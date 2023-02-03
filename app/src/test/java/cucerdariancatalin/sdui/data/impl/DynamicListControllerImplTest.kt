package cucerdariancatalin.sdui.data.impl

import app.cash.turbine.test
import cucerdariancatalin.sdui.data.actions.DynamicListAction
import cucerdariancatalin.sdui.data.api.DynamicListMockResponseApi
import cucerdariancatalin.sdui.data.api.DynamicListRenderProcessorApi
import cucerdariancatalin.sdui.data.models.*
import cucerdariancatalin.sdui.domain.impl.DynamicListControllerImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class DynamicListControllerImplTest {

    @Mock
    lateinit var dynamicListMockResponseApi: DynamicListMockResponseApi

    @Mock
    lateinit var dynamicListRenderProcessorApi: DynamicListRenderProcessorApi

    private lateinit var dynamicListControllerImpl: DynamicListControllerImpl

    private val dynamicListRequestModel by lazy {
        DynamicListRequestModel(ContextType.HOME)
    }

    private val defaultDataContentModel by lazy {
        DataContentModel(
            header = emptyList(),
            body = emptyList()
        )
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        dynamicListControllerImpl = DynamicListControllerImpl(
            dynamicListRenderProcessorApi = dynamicListRenderProcessorApi,
            dynamicListMockResponseApi = dynamicListMockResponseApi
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `SuccessAction should return`() = runTest {
        whenever(
            dynamicListMockResponseApi.getJsonDataFromAsset()
        ).thenReturn(
            defaultDataContentModel
        )

        dynamicListControllerImpl.get(0, dynamicListRequestModel).test {
            assert(awaitItem() is DynamicListAction.SuccessAction)
            cancelAndConsumeRemainingEvents()
        }
    }
}