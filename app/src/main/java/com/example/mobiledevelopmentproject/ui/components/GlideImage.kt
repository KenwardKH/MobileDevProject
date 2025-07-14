package com.example.mobiledevelopmentproject.ui.components

import android.widget.ImageView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide


@Composable
fun GlideImage(url: String, modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            ImageView(context).apply {
                scaleType = ImageView.ScaleType.FIT_CENTER
            }
        },
        update = { imageView ->
            Glide.with(imageView.context)
                .load(url)
                .into(imageView)
        },
        modifier = modifier,
    )
}