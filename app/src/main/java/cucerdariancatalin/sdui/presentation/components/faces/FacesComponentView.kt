package cucerdariancatalin.sdui.presentation.components.faces

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.size.Size
import cucerdariancatalin.sdui.presentation.components.common.ImageComponentView
import cucerdariancatalin.sdui.presentation.components.common.toPx
import cucerdariancatalin.sdui.presentation.ui.theme.Typography

@Composable
fun FacesComponentView(
    modifier: Modifier,
    faces: List<FacesItemModel>,
    onClick: (Int) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
        modifier = modifier,
    ) {
        itemsIndexed(items = faces) { _, item ->
            FaceView(name = item.name, imageUrl = item.url) {
                onClick(item.goTo)
            }
        }
    }
}

@Composable
fun FaceView(
    name: String,
    imageUrl: String,
    onClick: () -> Unit
) {

    val size = 70.dp
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.width(70.dp)
    ) {

        ImageComponentView(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally)
                .clickable {
                    onClick()
                },
            imageURL = imageUrl,
            overrideSize = Size(
                size.toPx(context).toInt(),
                size.toPx(context).toInt()
            )
        )

        Text(
            text = name,
            style = Typography.caption,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            color = MaterialTheme.colors.secondary
        )
    }
}