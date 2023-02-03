package cucerdariancatalin.sdui.presentation.ui.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cucerdariancatalin.sdui.data.actions.ContextViewAction
import cucerdariancatalin.sdui.data.actions.DynamicListAction
import cucerdariancatalin.sdui.data.actions.ScrollAction
import cucerdariancatalin.sdui.data.actions.TargetAction
import cucerdariancatalin.sdui.data.controllers.DynamicListComposeController
import cucerdariancatalin.sdui.data.controllers.DynamicListComposeLoader
import cucerdariancatalin.sdui.data.models.DynamicListRequestModel
import cucerdariancatalin.sdui.presentation.ui.components.ErrorView
import cucerdariancatalin.sdui.presentation.ui.components.LoaderView
import cucerdariancatalin.sdui.presentation.ui.components.showCase.ShowCaseState
import cucerdariancatalin.sdui.presentation.viewModels.DynamicListViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalLifecycleComposeApi::class)
class DynamicListCompose(
    requestModel: DynamicListRequestModel
) : DynamicListComposeLoader() {

    private var bodyComposeController: DynamicListComposeController? = null
    private var headerComposeController: DynamicListComposeController? = null

    private val dynamicListRequestModel = mutableStateOf<DynamicListRequestModel?>(requestModel)

    @Composable
    override fun <T: DynamicListComposeController> DynamicListScreen(
        bodyAdapterController: T,
        headerAdapterController: T,
        action: ContextViewAction?,
        widthSizeClass: WindowWidthSizeClass,
        showCaseState: ShowCaseState,
        bodyListState: LazyListState
    ) {
        this.bodyComposeController = bodyAdapterController
        this.headerComposeController = headerAdapterController

        DynamicListContent(
            action = action,
            widthSizeClass = widthSizeClass,
            showCaseState = showCaseState,
            bodyListState = bodyListState
        )
    }

    @Composable
    private fun DynamicListContent(
        widthSizeClass: WindowWidthSizeClass,
        action: ContextViewAction?,
        showCaseState: ShowCaseState,
        bodyListState: LazyListState,
        dynamicListViewModel: DynamicListViewModel = hiltViewModel()
    ) {
        val dynamicListState by dynamicListViewModel.dynamicListAction.collectAsStateWithLifecycle()

        action?.let {
            when (it) {
                is ContextViewAction.Reload -> dynamicListViewModel.load(dynamicListRequestModel.value!!)
            }
        }

        when (dynamicListState) {

            is DynamicListAction.SkeletonAction -> {
                val skeletons = (dynamicListState as DynamicListAction.SkeletonAction).renderTypes
                bodyComposeController?.dispatchSkeletons(skeletons)
                bodyComposeController?.DynamicListSkeletons()
            }

            is DynamicListAction.LoadingAction -> {
                LoaderView()
                dynamicListViewModel.load(dynamicListRequestModel.value!!)
            }

            is DynamicListAction.ErrorAction ->
                ErrorView((dynamicListState as DynamicListAction.ErrorAction).exception) {
                dynamicListViewModel.load(dynamicListRequestModel.value!!)
            }

            is DynamicListAction.SuccessAction -> {
                val container = (dynamicListState as DynamicListAction.SuccessAction).container
                val coroutineScope = rememberCoroutineScope()

                LaunchedEffect(container, dynamicListViewModel) {
                    coroutineScope.launch {
                        headerComposeController?.dispatch(container.headers)
                        bodyComposeController?.dispatch(container.bodies)
                    }
                }

                val actionBody = remember {
                    mutableStateOf<ScrollAction?>(null)
                }

                DynamicListView(
                    widthSizeClass = widthSizeClass,
                    contentHeader = {
                        headerComposeController?.ComposeHeader(
                            widthSizeClass = widthSizeClass,
                            showCaseState = showCaseState
                        ) {
                            if (it.target == TargetAction.BODY) {
                                actionBody.value = it
                            }
                        }
                    },
                    contentBody = {
                        bodyComposeController?.ComposeBody(
                            widthSizeClass = widthSizeClass,
                            sharedAction = actionBody.value,
                            showCaseState = showCaseState,
                            bodyListState = bodyListState
                        ) {
                            if (it.target == TargetAction.BODY) {
                                actionBody.value = it
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun DynamicListView(
    widthSizeClass: WindowWidthSizeClass,
    contentHeader: @Composable () -> Unit,
    contentBody: @Composable () -> Unit
) {
    when(widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            Column {
                contentHeader()
                contentBody()
            }
        }

        WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded -> {
            Row {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    contentHeader()
                }

                Box(
                    modifier = Modifier.weight(1.5f)
                ) {
                    contentBody()
                }
            }
        }
    }
}
