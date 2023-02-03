package cucerdariancatalin.sdui.presentation.components.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import cucerdariancatalin.sdui.presentation.components.card.CardImage
import cucerdariancatalin.sdui.presentation.ui.theme.Typography

private const val MAX_ELEMENTS = 3

@Composable
fun CardItemVIew(
    modifier: Modifier = Modifier,
    title: String,
    images: List<CardImage>,
    onClick: () -> Unit
) {
    val pictures = remember {
        derivedStateOf {
            images.take(MAX_ELEMENTS)
        }
    }

    val number = remember {
        derivedStateOf { images.size - MAX_ELEMENTS }
    }

    Card(
        modifier = modifier
            .testTag("card-item")
            .wrapContentWidth()
            .height(100.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(12.dp),
        elevation = 5.dp
    ) {

        Column(
            modifier = Modifier
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = title,
                color = MaterialTheme.colors.secondary,
                style = Typography.button
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {

                pictures.value.forEach { cardImage ->
                    CardImageItem(imageURL = cardImage.imageURL)
                }

                if (images.size > MAX_ELEMENTS) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .clip(RoundedCornerShape(16.dp))
                    ) {
                        Text(
                            text = "+${number.value}",
                            style = Typography.button
                        )
                    }
                }
            }
        }
    }
}
