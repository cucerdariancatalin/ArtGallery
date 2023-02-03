package cucerdariancatalin.sdui.presentation.components.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import cucerdariancatalin.sdui.presentation.components.banner.BannerInfo
import cucerdariancatalin.sdui.presentation.components.bannerCarousel.BANNER_CAROUSEL_IMAGE_TEST_TAG

const val FADE_IMAGE_DURATION = 1000

@Composable
fun BannerImageView(
    modifier: Modifier = Modifier,
    imageURL: String,
    bannerInfo: BannerInfo? = null,
    onClickAction: () -> Unit
) {

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .testTag(BANNER_CAROUSEL_IMAGE_TEST_TAG)
    ) {

        ImageComponentView(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onClickAction()
                },
            imageURL = imageURL
        )

        bannerInfo?.let {
            BannerInfoView(
                modifier = Modifier.fillMaxSize(),
                bannerInfo = it
            )
        }
    }
}