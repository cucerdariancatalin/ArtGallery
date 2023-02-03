package cucerdariancatalin.sdui.presentation.ui.components.showCase

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cucerdariancatalin.sdui.presentation.ui.theme.Typography
import cucerdariancatalin.sdui.presentation.ui.theme.gradientColor1
import cucerdariancatalin.sdui.presentation.ui.theme.gradientColor2

@Composable
fun TooltipView(
    text: String,
    alignment: Alignment = Alignment.Center
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .align(alignment)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            gradientColor1,
                            gradientColor2
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(5.dp) ,
                textAlign = TextAlign.Center,
                style = Typography.h6,
                color = Color.White
            )
        }
    }
}

@Composable
@Preview
fun PreviewTooltip() {
    TooltipView(
        "Esto es un tooltip"
    )
}