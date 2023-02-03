@file:OptIn(ExperimentalLifecycleComposeApi::class)

package cucerdariancatalin.sdui.presentation.ui.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cucerdariancatalin.sdui.presentation.ui.components.headers.DynamicListHeaderComponentView
import cucerdariancatalin.sdui.presentation.ui.components.showCase.ShowCase
import cucerdariancatalin.sdui.presentation.ui.components.showCase.rememberShowCaseState
import cucerdariancatalin.sdui.presentation.viewModels.ContextViewModel

@Composable
fun ContextView(
    title: String,
    widthSizeClass: WindowWidthSizeClass,
    viewModel: ContextViewModel
) {

    /**
     * 1. Change context should be a compose state.
     * 2. Reload should be a compose state.
     */

    val action by viewModel.contextViewAction.collectAsStateWithLifecycle()

    val showCaseState = rememberShowCaseState()

    val bodyLazyListState = rememberLazyListState()

    ShowCase(
        showIntroShowCase = true,
        state = showCaseState
    ) {
        Scaffold(
            topBar = {
                DynamicListHeaderComponentView(
                    title = title,
                    contextType = viewModel.context,
                    bodyLazyListState = bodyLazyListState
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier.padding(padding)
            ) {
                DynamicListCompose(viewModel.requestModel).DynamicListScreen(
                    headerAdapterController = viewModel.headerAdapterController,
                    bodyAdapterController = viewModel.bodyAdapterController,
                    action = action,
                    widthSizeClass = widthSizeClass,
                    showCaseState = showCaseState,
                    bodyListState = bodyLazyListState
                )
            }
        }
    }
}