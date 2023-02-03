package cucerdariancatalin.sdui.data.renders

import com.javi.render.processor.annotations.render.RenderFactory
import com.javi.render.processor.data.enums.RenderType
import cucerdariancatalin.sdui.data.renders.base.DynamicListRender
import cucerdariancatalin.sdui.presentation.components.bannerCarousel.BannerCarouselModel
import javax.inject.Inject

@RenderFactory(model = BannerCarouselModel::class)
class BannerCarouselRender @Inject constructor() : DynamicListRender<BannerCarouselModel> {

    override val renders: List<RenderType>
        get() = listOf(
            RenderType.BANNER_CAROUSEL
        )

    override suspend fun <T> resolve(render: String, resource: T?): BannerCarouselModel {
        val model = (resource as BannerCarouselModel)
        return model.copy(banners = model.banners.filter { it.bannerInfo != null })
    }
}