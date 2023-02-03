package cucerdariancatalin.sdui.data.factories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import cucerdariancatalin.sdui.presentation.components.filters.Filters
import cucerdariancatalin.sdui.presentation.components.filters.FiltersComponentViewScreen
import javax.inject.Inject

@AdapterFactory
class FiltersFactory @Inject constructor(): DynamicListFactory {

    override val renders: List<RenderType>
        get() = listOf(
            RenderType.FILTERS
        )

    @Composable
    override fun CreateComponent(
        modifier: Modifier,
        component: ComponentItemModel,
        componentInfo: ComponentInfo
    ) {

        val model = remember {
            derivedStateOf {
                (component.resource as Filters).items
            }
        }
        FiltersComponentViewScreen(
            modifier = modifier.testTag("filters_component"),
            data = model.value,
            windowWidthSizeClass = componentInfo.windowWidthSizeClass
        ) {
            componentInfo.scrollAction?.invoke(
                ScrollAction.ScrollRender(it)
            )
        }
    }

    @Composable
    override fun CreateSkeleton() {
        Row(
            modifier = Modifier
                .testTag("skeleton")
                .fillMaxWidth()
                .wrapContentSize(unbounded = true),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            val width = 80.dp
            val height = 35.dp

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(MaterialTheme.colors.onPrimary)
                    .width(width)
                    .height(height)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(MaterialTheme.colors.onPrimary)
                    .width(width)
                    .height(height)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(MaterialTheme.colors.onPrimary)
                    .width(width)
                    .height(height)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(MaterialTheme.colors.onPrimary)
                    .width(width)
                    .height(height)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(MaterialTheme.colors.onPrimary)
                    .width(width)
                    .height(height)
            )
        }
    }
}