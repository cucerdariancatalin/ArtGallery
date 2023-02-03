package cucerdariancatalin.sdui.presentation.components.filters

import com.javi.render.processor.annotations.render.RenderClass
import com.javi.render.processor.data.enums.RenderType
import com.squareup.moshi.Json

@RenderClass(type = RenderType.FILTERS)
data class Filters(
    @Json(name = "items") val items: List<FilterItemModel>
)

data class FilterItemModel(
    @Json(name = "text") val text: String,
    @Json(name = "go_to") val goTo: String,
    @Json(name = "color") val color: String
)