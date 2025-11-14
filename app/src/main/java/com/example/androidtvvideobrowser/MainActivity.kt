package com.example.androidtvvideobrowser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import com.example.androidtvvideobrowser.ui.theme.AndroidTVVideoBrowserTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.tv.foundation.lazy.grid.TvGridCells
import androidx.tv.foundation.lazy.grid.TvLazyVerticalGrid
import androidx.tv.foundation.lazy.grid.items
import coil.compose.AsyncImage
import com.example.androidtvvideobrowser.model.Video
import com.example.androidtvvideobrowser.model.sampleVideos
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.Text


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTVVideoBrowserTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RectangleShape
                ) {
                    VideoBrowserApp()
                }
            }
        }
    }
}

@Composable
fun VideoBrowserApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(onVideoClick = { video ->
                navController.navigate("playback/${video.id}")
            })
        }
        composable("playback/{videoId}") { backStackEntry ->
            val videoId = backStackEntry.arguments?.getString("videoId")
            val video = sampleVideos.find { it.id == videoId }
            if (video != null) {
                PlaybackScreen(video = video)
            } else {
                // Handle error: video not found
            }
        }
    }
}

@Composable
fun HomeScreen(onVideoClick: (Video) -> Unit) {
    TvLazyVerticalGrid(
        columns = TvGridCells.Fixed(4),
        modifier = Modifier.padding(16.dp)
    ) {
        items(sampleVideos) { video ->
            VideoCard(video = video, onClick = { onVideoClick(video) })
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun VideoCard(video: Video, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.padding(8.dp),
        scale = CardDefaults.scale(focusedScale = 1.1f),
        shape = CardDefaults.shape(shape = RectangleShape)
    ) {
        Column {
            AsyncImage(
                model = video.thumbnailUrl,
                contentDescription = video.title,
                modifier = Modifier.size(200.dp, 120.dp),
                contentScale = ContentScale.Crop
            )
            Text(text = video.title, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun PlaybackScreen(video: Video) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(video.videoUrl))
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(
        AndroidView(factory = {
            PlayerView(context).apply {
                player = exoPlayer
            }
        })
    ) {
        onDispose {
            exoPlayer.release()
        }
    }
}
