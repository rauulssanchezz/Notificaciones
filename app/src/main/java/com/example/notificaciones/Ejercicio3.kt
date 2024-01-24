package com.example.notificaciones

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.notificaciones.databinding.ActivityEjercicio3Binding

class Ejercicio3 : AppCompatActivity() {
    private lateinit var binding : ActivityEjercicio3Binding
    var titulo: String? = null
    var texto: String? = null
    var icono: Drawable? = null
    var foto: Drawable? = null
    var botones: Int = 0
    var cont = 0

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "nombre del canal"
            val descriptionText = "Texto del canal"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("channel_id", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }

    private fun mostrarNotificacion() {
        var bitmap = foto?.toBitmap()
        var bitmapic = icono?.toBitmap()
        var builder = NotificationCompat.Builder(this, "channel_id")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(titulo)
            .setContentText(texto)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            .setLargeIcon(bitmapic)

        if (botones != 0) {
            for (i in 1..botones) {
                builder.addAction(
                    R.drawable.ic_launcher_foreground,
                    "Boton $i",
                    PendingIntent.getActivity(this, 0, Intent(), PendingIntent.FLAG_IMMUTABLE)
                )
            }
        }

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this@Ejercicio3,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

            }
            cont++
            notify(cont, builder.build())
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEjercicio3Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        createNotificationChannel()

        ArrayAdapter.createFromResource(
            this,
            R.array.iconos,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.iconos.adapter = adapter

        }
            binding.iconos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    p1: View?,
                    p2: Int,
                    p3: Long
                ) {
                    when (p2) {
                        0 -> icono = null
                        1 -> icono = getDrawable(R.drawable.mensaje)!!
                        2 -> icono = getDrawable(R.drawable.ic_launcher_background)!!
                        3 -> icono = getDrawable(R.drawable.baseline_stop_24)!!
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>) {
                    icono = null
                }
            }
            ArrayAdapter.createFromResource(
                this,
                R.array.fotos,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.fotos.adapter = adapter

            }

            binding.fotos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    p1: View?,
                    p2: Int,
                    p3: Long
                ) {
                    when (p2) {
                        0 -> foto = null
                        1 -> foto = getDrawable(R.drawable.enfadau)!!
                        2 -> foto = getDrawable(R.drawable.enrallao)!!
                        3 -> foto = getDrawable(R.drawable.the_boys)!!
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>) {
                    foto = null
                }
            }
                binding.btnMas.setOnClickListener {
                    if (botones < 3) {
                        botones++
                        binding.txtContador.setText(botones.toString())
                    }
                }

                binding.btnMenos.setOnClickListener {
                    if (botones > 0) {
                        botones--
                        binding.txtContador.setText(botones.toString())
                    }
                }

        binding.btnNotificacion.setOnClickListener {
            if (validar()) {
                titulo = binding.txtInputTitulo.text.toString()
                texto = binding.txtInputTexto.text.toString()
                mostrarNotificacion()
                Toast.makeText(this, "Notificacion enviada", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun validar(): Boolean {
        var fot = true
        var ic = true
        var titulo = true
        var texto = true

        if (binding.txtInputTitulo.text.toString().isEmpty()) {
            binding.txtInputTitulo.error = "Ingrese un titulo"
            titulo = false
        }
        if (binding.txtInputTexto.text.toString().isEmpty()) {
            binding.txtInputTexto.error = "Ingrese una descripcion"
            texto = false
        }
        if (icono == null) {
            ic = false
            Toast.makeText(this, "Debes elegir un icono", Toast.LENGTH_SHORT).show()
        }
        if (foto == null) {
            fot = false
            Toast.makeText(this, "Debes elegir una foto", Toast.LENGTH_SHORT).show()
        }

        return titulo && texto && ic && fot
    }
}

