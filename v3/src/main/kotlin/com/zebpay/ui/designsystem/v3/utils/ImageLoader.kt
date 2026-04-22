package com.zebpay.ui.designsystem.v3.utils

import android.content.Context
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import com.zebpay.ui.v3.R

infix fun Context.load(imageUrl: String) =
    ImageRequest.Builder(this)
        .data(imageUrl)
        .crossfade(true)
        .placeholder(R.drawable.ic_image_place_holder)
        .error(R.drawable.ic_image_place_holder)
        .build()
