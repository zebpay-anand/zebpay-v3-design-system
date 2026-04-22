package com.zebpay.ui.v3.components.atoms.icon

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Abc
import androidx.compose.material.icons.rounded.Air
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.zebpay.ui.designsystem.v3.color.ZGradient
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.conditional
import com.zebpay.ui.designsystem.v3.utils.load
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalZGradientColor
import com.zebpay.ui.v3.components.utils.LocalZIconSize
import com.zebpay.ui.v3.components.utils.gradientIcon

/**
 * A composable function that displays a ZIcon.
 *
 * @param icon The ZIcon to display.
 * @param modifier The modifier to apply to the icon.
 * @param size The size of the icon. If set to -1.dp, the default size will be used.
 * @param contentDescription The content description for the icon, used for accessibility.
 * @param tint The tint color to apply to the icon.
 */
@Composable
fun ZIcon(
    icon: ZIcon,
    modifier: Modifier = Modifier,
    tagID:String="",
    size: Dp = LocalZIconSize.current,
    contentDescription: String? = null,
    tint: Color = LocalContentColor.current,
) {
    when (icon) {
        is ZIcon.Drawable -> {
            Icon(
                painter = painterResource(id = icon.id),
                contentDescription = contentDescription,
                tint = tint,
                modifier = modifier.conditional(size != (-1).dp) {
                    size(size)
                }.testTag(tagID).semantics{testTagsAsResourceId=true},
            )
        }

        is ZIcon.Vector -> {
            Icon(
                imageVector = icon.imageVector,
                contentDescription = contentDescription,
                tint = tint,
                modifier = modifier.conditional(size != (-1).dp) {
                    size(size)
                },
            )
        }

        is ZIcon.Url -> {
            val context = LocalContext.current
            AsyncImage(
                model = context load icon.uri.toString(),
                contentDescription = contentDescription,
                modifier = modifier.conditional(size != (-1).dp) {
                    size(size)
                },
                contentScale = ContentScale.Fit,
            )
        }
    }
}


@Preview
@Composable
private fun PreviewZIconsVariant() {
    Row(modifier = Modifier) {
        ZIcon(
            icon = Icons.Rounded.Air.asZIcon,
            modifier = Modifier,
            tint = ZTheme.color.icon.singleToneBlue,
        )

        ZIcon(
            icon = android.R.drawable.star_on.asZIcon,
            modifier = Modifier,
            tint = ZTheme.color.icon.singleToneBlue,
        )
    }
}


/**
 * A composable function that displays an icon with a gradient fill.
 *
 * @param imageVector The [ImageVector] to display.
 * @param brush The [Brush] to use for the gradient fill.
 * @param modifier The modifier to apply to the icon.
 * @param contentDescription The content description for the icon, used for accessibility.
 */
@Composable
fun ZGradientIcon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    gradient: ZGradient = LocalZGradientColor.current,
    contentDescription: String? = null,
) {
    Icon(
        modifier = modifier.gradientIcon(gradient),
        imageVector = imageVector,
        contentDescription = contentDescription,
    )
}

@Preview
@Composable
private fun PreviewZGradientIcon() {
    ZGradientIcon(
        imageVector = Icons.Rounded.Abc,
        gradient = ZTheme.color.icon.dualToneGradient,
    )
}

/**
 * A composable function that displays an icon with a gradient fill.
 *
 * @param drawable The [DrawableRes] to display.
 * @param brush The [Brush] to use for the gradient fill.
 * @param modifier The modifier to apply to the icon.
 * @param contentDescription The content description for the icon, used for accessibility.
 */
@Composable
fun ZGradientIcon(
    @DrawableRes drawable: Int,
    modifier: Modifier = Modifier,
    gradient: ZGradient = LocalZGradientColor.current,
    contentDescription: String? = null,
) {
    Icon(
        modifier = modifier
            .gradientIcon(gradient = gradient),
        painter = painterResource(id = drawable),
        contentDescription = contentDescription,
    )
}

@Preview
@Composable
private fun PreviewZGradientIconBitmap() {
    ZGradientIcon(
        icon = ZIcons.ic_star_filled.asZIcon,
        gradient = ZTheme.color.icon.dualToneGradient,
    )
}


@Composable
fun ZGradientIcon(
    icon: ZIcon,
    modifier: Modifier = Modifier,
    size: Dp = LocalZIconSize.current,
    gradient: ZGradient = LocalZGradientColor.current,
    contentDescription: String? = null,
) {
    when (icon) {
        is ZIcon.Drawable -> {
            ZGradientIcon(
                drawable = icon.id,
                contentDescription = contentDescription,
                modifier = modifier.conditional(size != (-1).dp) {
                    size(size)
                },
                gradient = gradient,
            )
        }

        is ZIcon.Vector -> {
            ZGradientIcon(
                imageVector = icon.imageVector,
                contentDescription = contentDescription,
                gradient = gradient,
                modifier = modifier.conditional(size != (-1).dp) {
                    size(size)
                },
            )
        }

        is ZIcon.Url -> {
            AsyncImage(
                model = icon.uri,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = modifier.conditional(size != (-1).dp) {
                    size(size)
                },
            )
        }
    }
}

@Composable
fun ZImage(
    icon: ZIcon,
    modifier: Modifier = Modifier,
    size: Dp = LocalZIconSize.current,
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String? = null,
) {
    when (icon) {
        is ZIcon.Drawable -> {
            Image(
                painter = painterResource(id = icon.id),
                contentDescription = contentDescription,
                modifier = modifier.conditional(size != (-1).dp) {
                    size(size)
                },
                contentScale = contentScale,
            )
        }

        is ZIcon.Vector -> {
            Image(
                imageVector = icon.imageVector,
                contentDescription = contentDescription,
                modifier = modifier.conditional(size != (-1).dp) {
                    size(size)
                },
                contentScale = contentScale,
            )
        }

        is ZIcon.Url -> {
            val context = LocalContext.current
            AsyncImage(
                model = context load icon.uri.toString(),
                contentDescription = contentDescription,
                modifier = modifier.conditional(size != (-1).dp) {
                    size(size)
                },
                contentScale = contentScale,
            )
        }
    }
}


/**
 * A sealed class to make dealing with [ImageVector], [DrawableRes] icons easier.
 */
sealed class ZIcon {
    data class Vector(val imageVector: ImageVector) : ZIcon()
    data class Drawable(@DrawableRes val id: Int) : ZIcon()
    data class Url(val uri: Uri) : ZIcon()
}

val @receiver:DrawableRes Int.asZIcon: ZIcon
    get() = ZIcon.Drawable(this)

val ImageVector.asZIcon: ZIcon
    get() = ZIcon.Vector(this)

val Uri.asZIcon: ZIcon
    get() = ZIcon.Url(this)
