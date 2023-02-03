package cucerdariancatalin.sdui.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.javier.api.NavigationController
import cucerdariancatalin.sdui.data.actions.ContextViewAction
import cucerdariancatalin.sdui.data.controllers.DefaultDynamicListController
import cucerdariancatalin.sdui.data.models.ContextType
import cucerdariancatalin.sdui.data.models.DynamicListRequestModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

abstract class ContextViewModel: ViewModel() {

    @Inject
    lateinit var navigationController: NavigationController

    @Inject
    lateinit var headerAdapterController: DefaultDynamicListController

    @Inject
    lateinit var bodyAdapterController: DefaultDynamicListController

    private val _contextViewAction = MutableStateFlow<ContextViewAction?>(null)
    val contextViewAction: StateFlow<ContextViewAction?> = _contextViewAction.asStateFlow()

    abstract val context: ContextType

    /**
     * Maybe this can be removed and create abstract properties
     * like state or other params for request model.
     * The idea is only change properties and not all the model
      */
    abstract val requestModel: DynamicListRequestModel

    fun onBackPressed() {
        navigationController.popBackStack()
    }
}