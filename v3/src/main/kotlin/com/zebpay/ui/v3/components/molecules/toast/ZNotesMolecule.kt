package com.zebpay.ui.v3.components.molecules.toast

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.safeClickable
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.icon.ZGradientIcon
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.resource.ZIcons


private data class ZNoteColor(
    val containerColor: Color,
    val strokeColor: Color,
    val titleColor: Color,
    val subTitleColor: Color,
    val iconColor: Color,
)

enum class ZNoteType {
    Success, Info, Error;
}

@Composable
private fun ZNoteType.getColor(): ZNoteColor {
    val noteColor = ZTheme.color.note
    val textColor = ZTheme.color.text
    val iconColor = ZTheme.color.icon

    return when (this) {
        ZNoteType.Success -> ZNoteColor(
            containerColor = noteColor.backgroundDefault,
            strokeColor = noteColor.borderDefault,
            titleColor = noteColor.textGreen,
            subTitleColor = textColor.secondary,
            iconColor = iconColor.singleToneGreen,
        )

        ZNoteType.Info -> ZNoteColor(
            containerColor = noteColor.backgroundDefault,
            strokeColor = noteColor.borderDefault,
            titleColor = noteColor.textYellow,
            subTitleColor = textColor.secondary,
            iconColor = iconColor.singleToneYellow,
        )

        ZNoteType.Error -> ZNoteColor(
            containerColor = noteColor.backgroundDefault,
            strokeColor = noteColor.borderDefault,
            titleColor = noteColor.textRed,
            subTitleColor = textColor.secondary,
            iconColor = iconColor.singleToneRed,
        )
    }
}

@Composable
fun ZNote(
    title: String,
    modifier: Modifier = Modifier,
    message: String? = null,
    type: ZNoteType,
    showShadow: Boolean = false,
    maxMessageLine: Int = 5,
    tagId:String="",
    onClick: (() -> Unit)?=null,
) {
    val noteColor = type.getColor()

    Surface(
        shadowElevation = if (showShadow) 8.dp else 0.dp,
        shape = ZTheme.shapes.large,
        color = noteColor.containerColor,
        border = BorderStroke(1.dp, noteColor.strokeColor),
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.safeClickable(onClick!=null, tagId = tagId) {
                onClick?.invoke()
            }.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ZIcon(
                        icon = ZIcons.ic_info.asZIcon,
                        contentDescription = null,
                        tint = noteColor.iconColor,
                    )
                    Text(
                        text = title,
                        color = noteColor.titleColor,
                        style = ZTheme.typography.bodyRegularB2.semiBold(),
                        modifier = Modifier.weight(1f),
                    )
                    if(onClick!=null) {
                        ZGradientIcon(
                            icon = ZIcons.ic_arrow_right.asZIcon,
                            contentDescription = null,
                            gradient = ZTheme.color.icon.dualToneGradient,
                        )
                    }
                }
                if (!message.isNullOrEmpty()) {
                    Text(
                        text = message,
                        color = noteColor.subTitleColor,
                        style = ZTheme.typography.bodyRegularB3,
                        maxLines = maxMessageLine,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

@ThemePreviews
@Composable
fun PreviewZNote() {
    ZBackgroundPreviewContainer {
        ZNote(
            title = "Note",
            message = "Text",
            type = ZNoteType.Success,
            onClick = {}
        )

        ZNote(
            title = "Note",
            message = "Text",
            type = ZNoteType.Info,
            onClick = {},
        )

        ZNote(
            title = "Note",
            message = "Text",
            type = ZNoteType.Error,
            onClick = {},
        )
    }
}
