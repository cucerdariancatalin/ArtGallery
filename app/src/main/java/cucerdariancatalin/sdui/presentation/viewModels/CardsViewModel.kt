package cucerdariancatalin.sdui.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.javier.api.NavigationController
import com.ramcosta.composedestinations.spec.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val navigationController: NavigationController
): ViewModel() {

    fun navigateToCardsDetail(
        direction: Direction
    ) {
        navigationController.navigateTo(direction)
    }
}