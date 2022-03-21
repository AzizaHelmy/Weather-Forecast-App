package com.example.weatherforecastapp.ui.alerts

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.data.source.Repository
import com.example.weatherforecastapp.data.source.local.ConcretLocalSource
import com.example.weatherforecastapp.ui.home.viewmodel.WeatherViewModel
import com.example.weatherforecastapp.utils.Constant


class AlertWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    private lateinit var manager: NotificationManager
    private val concretLocalSource: ConcretLocalSource = ConcretLocalSource(context)//bad developer
    private val homeViewModel: WeatherViewModel = WeatherViewModel()//bad developer
    private val repo = Repository(homeViewModel, concretLocalSource)//bad developer

    override fun doWork(): Result {
        val type = inputData.getString("type")
        val lat = inputData.getDouble("lat", 0.0)
        val lng = inputData.getDouble("lng", 0.0)

        val alertResult = repo.isWeatherAlert(lat, lng)

        //if (alertResult) {
            createNotificationChannal()
            sendNotification("Be Careful !")
        //} else {
            Log.i("TAG", "doWork: Fail !!")
       // }
        return Result.success()
    }


    //===================================================
    private fun createNotificationChannal() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                Constant.CHANNEL_ID,
                Constant.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
            //notificationChannel.setSound(R.raw.alert, audioAttributes)
            manager.createNotificationChannel(notificationChannel)
        }
    }

    //=====================================================
    private fun sendNotification(message: String) {
        val notificationManager = NotificationManagerCompat.from(applicationContext);
        val notification: Notification = NotificationCompat.Builder(applicationContext, "1")
            .setSmallIcon(R.drawable.clouds)
            .setContentTitle("Weather Status Today is $message ")
            .setContentText("BE CAREFUL")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
        notificationManager.notify(2, notification)
        if (Constant.notification) {
            val player =
                MediaPlayer.create(applicationContext, com.example.weatherforecastapp.R.raw.alert)
            player.start()
        }

    }


}