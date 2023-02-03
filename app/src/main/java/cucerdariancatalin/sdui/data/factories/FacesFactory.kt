package cucerdariancatalin.sdui.data.factories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.javi.render.processor.annotations.factory.AdapterFactory
import com.javi.render.processor.data.enums.RenderType
import cucerdariancatalin.sdui.data.actions.ScrollAction
import cucerdariancatalin.sdui.data.factories.base.DynamicListFactory
import cucerdariancatalin.sdui.data.models.ComponentInfo
import cucerdariancatalin.sdui.data.models.ComponentItemModel
import cucerdariancatalin.sdui.presentation.components.faces.FacesComponentView
import cucerdariancatalin.sdui.presentation.components.faces.FacesModel
import javax.inject.Inject

@AdapterFactory
class FacesFactory @Inject constructor(

): DynamicListFactory {

    override val renders: List<RenderType>
        get() = listOf(
            RenderType.FACES
        )

    @Composable
    override fun CreateComponent(
        modifier: Modifier,
        component: ComponentItemModel,
        componentInfo: ComponentInfo,
    ) {
        val model = remember {
            derivedStateOf {
                (component.resource as FacesModel).items
            }
        }
        FacesComponentView(
            modifier = modifier.testTag("faces_component"),
            faces = model.value
        ) {
            componentInfo.scrollAction?.invoke(
                ScrollAction.ScrollIndex(index = it)
            )
        }
    }

    @Composable
    override fun CreateSkeleton() {
        Row(
            modifier = Modifier
                .testTag("skeleton")
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .wrapContentSize(unbounded = true),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            FacesSkeletonItem()
            FacesSkeletonItem()
            FacesSkeletonItem()
            FacesSkeletonItem()
            FacesSkeletonItem()
        }
    }
}

@Composable
fun FacesSkeletonItem() {

    val size = 67.dp
    val roundedText = 6.dp
    val heightText = 13.dp

    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(MaterialTheme.colors.onPrimary)
        )

        Box(
            modifier = Modifier
                .width(size)
                .height(heightText)
                .clip(RoundedCornerShape(roundedText))
                .background(MaterialTheme.colors.onPrimary)
        )
    }
}