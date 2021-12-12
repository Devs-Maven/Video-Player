package com.example.videoplayer

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSource


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen()
        }
    }
}

@Composable
fun HomeScreen() {

    val context = LocalContext.current
    val videoUrl =
        "https://cdn.videvo.net/videvo_files/video/free/" +
        "2020-03/large_watermarked/Tromeur_Knight2_preview.mp4"

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val dataSource = DefaultDataSource.Factory(context)

            val source = ProgressiveMediaSource.Factory(dataSource)
                .createMediaSource(MediaItem.fromUri(Uri.parse(videoUrl)))

            this.addMediaSource(source)
            this.prepare()
        }
    }

    Column(
        Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ){

        AndroidView(
            factory = {
                PlayerView(it).apply {
                    this.player = exoPlayer
                    this.useController = true
                    this.setShowBuffering(
                        PlayerView.SHOW_BUFFERING_ALWAYS
                    )
                }
            }
        )

    }

}