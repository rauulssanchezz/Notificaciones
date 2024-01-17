package com.example.notificaciones

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.concurrent.atomic.AtomicInteger

class MainActivity : AppCompatActivity() {

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = channel_name
            val descriptionText = ""
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channel_id, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()

        var id= create_notification_id()
        var context=this
        var botonNotificacion=findViewById<Button>(R.id.notificacionBoton)

        println(channel_id)

        val builder = NotificationCompat.Builder(this, channel_id)
            .setSmallIcon(R.drawable.baseline_stop_24)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


            botonNotificacion.setOnClickListener {

            println("Existo")
            with(NotificationManagerCompat.from(this))  {

                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                   return@with
                }

                println(builder.build())
                notify(id, builder.build())
            }
        }
    }
    companion object{
        val app_id="com.example.notificaciones"
        val channel_id="${app_id}_c1"
        var id = AtomicInteger(0)
        var channel_name="Cacota"

        fun create_notification_id():Int{
           return id.incrementAndGet()
        }
    }

}