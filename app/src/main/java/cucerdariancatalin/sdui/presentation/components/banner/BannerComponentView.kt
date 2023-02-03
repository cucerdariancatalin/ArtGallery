package cucerdariancatalin.sdui.presentation.components.banner

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cucerdariancatalin.sdui.R
import cucerdariancatalin.sdui.data.models.showCase.ShapeType
import cucerdariancatalin.sdui.data.models.showCase.ShowCaseStrategy
import com.javi.render.processor.data.enums.RenderType
import cucerdariancatalin.sdui.destinations.BannerScreenDestination
import cucerdariancatalin.sdui.presentation.components.common.BannerImageView
import cucerdariancatalin.sdui.presentation.ui.components.showCase.ShowCaseState
import cucerdariancatalin.sdui.presentation.ui.components.showCase.ShowCaseStyle
import cucerdariancatalin.sdui.presentation.ui.components.showCase.TooltipView
import cucerdariancatalin.sdui.presentation.ui.components.showCase.asShowCaseTarget
import cucerdariancatalin.sdui.presentation.ui.components.showCase.rememberShowCaseState
import cucerdariancatalin.sdui.presentation.viewModels.BannerViewModel

const val BANNER_IMAGE_TEST_TAG = "banner-image"
const val BANNER_IMAGE_SCREEN_TEST_TAG = "banner-image-screen"

@Composable
fun BannerComponentViewScreen(
    modifier: Modifier,
    model: BannerModel,
    componentIndex: Int,
    showCaseState: ShowCaseState,
    viewModel: BannerViewModel = hiltViewModel()
) {
    BannerComponentView(
        modifier = modifier.testTag(BANNER_IMAGE_SCREEN_TEST_TAG),
        model = model,
        componentIndex = componentIndex,
        showCaseState = showCaseState
    ) {
        viewModel.loadBanner(
            BannerScreenDestination(it)
        )
    }
}

@Composable
fun BannerComponentView(
    modifier: Modifier,
    model: BannerModel,
    componentIndex: Int,
    showCaseState: ShowCaseState,
    onClickAction: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .testTag(BANNER_IMAGE_TEST_TAG)
            .padding(start = 16.dp, end = 16.dp)
            .clickable {
                onClickAction.invoke(model.imageURL)
            }
    ) {

        BannerImageView(
            modifier = modifier
                .height(150.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .asShowCaseTarget(
                    index = componentIndex,
                    style = ShowCaseStyle.Default.copy(
                        shapeType = ShapeType.RECTANGLE,
                        cornerRadius = 16.dp,
                        withAnimation = false
                    ),
                    content = {
                        TooltipView(text = stringResource(R.string.tooltip_banner))
                    },
                    strategy = ShowCaseStrategy(firstToHappen = true),
                    key = RenderType.BANNER.value,
                    state = showCaseState
                ),
            imageURL = model.imageURL,
            onClickAction = {
                onClickAction(model.imageURL)
            },
            bannerInfo = model.bannerInfo
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewCompactBannerComponentView() {
    BannerComponentView(
        modifier = Modifier,
        model = BannerModel(""),
        componentIndex = 0,
        showCaseState = rememberShowCaseState()
    ) { }
}
