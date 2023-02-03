package cucerdariancatalin.sdui.data.factories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.javi.render.processor.annotations.factory.AdapterFactory
import com.javi.render.processor.data.enums.RenderType
import cucerdariancatalin.sdui.data.factories.base.DynamicListFactory
import cucerdariancatalin.sdui.data.models.ComponentInfo
import cucerdariancatalin.sdui.data.models.ComponentItemModel
import cucerdariancatalin.sdui.presentation.components.poster.PosterComponentScreenView
import cucerdariancatalin.sdui.presentation.components.poster.PosterModel
import javax.inject.Inject

@AdapterFactory
class PosterFactory @Inject constructor(): DynamicListFactory {

    override val renders: List<RenderType> = listOf(RenderType.POSTER)

    @Composable
    override fun CreateComponent(
        modifier: Modifier,
        component: ComponentItemModel,
        componentInfo: ComponentInfo
    ) {
        val model = remember {
            derivedStateOf { component.resource as PosterModel }
        }

        PosterComponentScreenView(
            model = model.value,
            isExpandedScreen = componentInfo.windowWidthSizeClass == WindowWidthSizeClass.Expanded
        )
    }

    @Composable
    override fun CreateSkeleton() {
        Box(
            modifier = Modifier
                .height(450.dp)
                .fillMaxWidth()
                .testTag("skeleton")
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.onPrimary)
        )
    }
}