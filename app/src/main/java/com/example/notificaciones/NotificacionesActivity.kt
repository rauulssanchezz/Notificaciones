package com.example.notificaciones

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificacionesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notificaciones)

        val botonBigPicture = findViewById<Button>(R.id.botonbigpicture)
        val botonBigText = findViewById<Button>(R.id.botonbigtext)

        createNotificationChannel()

        botonBigPicture.setOnClickListener {
            showBigPictureNotification()
        }

        botonBigText.setOnClickListener {
            showBigTextNotification()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "canal_notificaciones"
            val descriptionText = "Canal para notificaciones de prueba"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(name, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showBigPictureNotification() {
        val builder = NotificationCompat.Builder(this, "canal_notificaciones")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Notificación BigPictureStyle")
            .setContentText("Esta es una notificación BigPictureStyle")
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_background))
                .bigLargeIcon(null))
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
            }
            notify(1, builder.build())
        }
    }

    private fun showBigTextNotification() {
        val builder = NotificationCompat.Builder(this, "canal_notificaciones")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Notificación BigTextStyle")
            .setContentText("Esta es una notificación BigTextStyle")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Este es el texto largo de la notificación BigTextStyle. En el principio solo habia dios y polvo y dios decidió crear la tierra, tardo 7 dias y 7 noches, primero creo la tierra y vio que era bueno, en el segundo dia creo el mar y vio que era bueno..."))
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
            }
            notify(2, builder.build())
        }
    }
}