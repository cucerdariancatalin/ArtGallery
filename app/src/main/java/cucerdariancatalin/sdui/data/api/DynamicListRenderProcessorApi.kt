package cucerdariancatalin.sdui.data.api

interface DynamicListRenderProcessorApi {

    suspend fun <T> processResource(render: String, resource: T? = null): Any?
}