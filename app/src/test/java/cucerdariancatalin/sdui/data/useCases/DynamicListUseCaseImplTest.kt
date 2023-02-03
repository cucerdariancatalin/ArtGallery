package cucerdariancatalin.sdui.data.useCases

import app.cash.turbine.test
import cucerdariancatalin.sdui.data.actions.DynamicListAction
import cucerdariancatalin.sdui.data.api.DynamicListControllerApi
import cucerdariancatalin.sdui.data.models.ContextType
import cucerdariancatalin.sdui.data.models.DynamicListContainer
import cucerdariancatalin.sdui.data.models.DynamicListRequestModel
import cucerdariancatalin.sdui.domain.database.AppDatabase
import cucerdariancatalin.sdui.domain.database.skeletons.SkeletonsDao
import cucerdariancatalin.sdui.domain.database.skeletons.SkeletonsEntity
import cucerdariancatalin.sdui.domain.useCases.DynamicListUseCaseImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
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
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class DynamicListUseCaseImplTest {

    @Mock
    lateinit var controller: DynamicListControllerApi

    @Mock
    lateinit var database: AppDatabase

    @Mock
    lateinit var skeletonsDao: SkeletonsDao

    @Mock
    lateinit var errorAction: DynamicListAction.ErrorAction

    private lateinit var dynamicListUseCaseImpl: DynamicListUseCaseImpl

    private val dispatcher = StandardTestDispatcher()

    private val dynamicListRequestModel by lazy {
        DynamicListRequestModel(ContextType.HOME)
    }

    private val successAction by lazy {
        DynamicListAction.SuccessAction(
            container = DynamicListContainer(
                headers = emptyList(),
                bodies = emptyList()
            )
        )
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        dynamicListUseCaseImpl = DynamicListUseCaseImpl(
            dispatcher,
            controller,
            database
        )

        whenever(database.skeletonsDao()).thenReturn(skeletonsDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `LoadingAction should return first`() = runTest {
        whenever(
            controller.get(0, dynamicListRequestModel)
        ).thenReturn(flow { emit(errorAction) })

        assert(
            dynamicListUseCaseImpl.get(0, dynamicListRequestModel).first() is DynamicListAction.LoadingAction
        )
    }

    @Test
    fun `SkeletonAction should return when skeleton database have data`() = runTest {
        whenever(
            controller.get(0, dynamicListRequestModel)
        ).thenReturn(flow { emit(successAction) })

        whenever(
            database.skeletonsDao().provideSkeletonsByContext(ContextType.HOME.source)
        ).thenReturn(SkeletonsEntity(ContextType.HOME.source, emptyList()))

        dynamicListUseCaseImpl.get(0, dynamicListRequestModel).first() is DynamicListAction.SkeletonAction
    }

    @Test
    fun `saveSkeletonsByContext should be called when response is success`() = runTest {
        whenever(
            controller.get(0, dynamicListRequestModel)
        ).thenReturn(flow { emit(successAction) })

        dynamicListUseCaseImpl.get(0, dynamicListRequestModel).collect()

        verify(database.skeletonsDao()).saveSkeletonsByContext(
            SkeletonsEntity(
                ContextType.HOME.source,
                emptyList()
            )
        )
    }

    @Suppress("TooGenericExceptionThrown")
    @Test
    fun `ErrorAction should be return when an exception happens in the repository`() = runTest {
        whenever(controller.get(0, dynamicListRequestModel)).thenReturn(
            flow { throw RuntimeException("Crash!") }
        )

        whenever(
            database.skeletonsDao().provideSkeletonsByContext(ContextType.HOME.source)
        ).thenReturn(SkeletonsEntity(ContextType.HOME.source, emptyList()))

        dynamicListUseCaseImpl.get(0, dynamicListRequestModel).test {
            awaitItem()
            assert(awaitItem() is DynamicListAction.ErrorAction)
            cancelAndIgnoreRemainingEvents()
        }
    }
}