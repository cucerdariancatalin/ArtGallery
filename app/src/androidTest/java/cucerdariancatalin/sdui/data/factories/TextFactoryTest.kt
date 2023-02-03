package cucerdariancatalin.sdui.data.factories

import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import cucerdariancatalin.sdui.MainActivity
import cucerdariancatalin.sdui.data.models.ComponentInfo
import cucerdariancatalin.sdui.data.models.ComponentItemModel
import com.javi.render.processor.data.enums.RenderType
import cucerdariancatalin.sdui.presentation.components.text.TextModel
import cucerdariancatalin.sdui.presentation.ui.components.showCase.rememberShowCaseState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class TextFactoryTest {

    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var factory: TextFactory

    private val componentItemModel by lazy {
        ComponentItemModel(
            render = RenderType.TEXT.name,
            index =  0,
            resource = TextModel(
                text = String()
            )
        )
    }

    @Before
    fun setUp() {
        hiltTestRule.inject()
        factory = TextFactory()
    }

    @Test
    fun createComponentShouldHaveTextComponentView() {

        composeTestRule.activity.setContent {
            factory.CreateComponent(
                modifier = Modifier,
                component = componentItemModel,
                componentInfo = ComponentInfo(
                    windowWidthSizeClass = calculateWindowSizeClass(composeTestRule.activity).widthSizeClass,
                    showCaseState = rememberShowCaseState()
                )
            )
        }

        composeTestRule
            .onNodeWithTag("text_component")
            .assertExists()
    }

    @Test
    fun createSkeletonShouldHaveSkeleton() {

        composeTestRule.activity.setContent {
            factory.CreateSkeleton()
        }

        composeTestRule
            .onNodeWithTag("skeleton")
            .assertExists()
    }

    @Test
    fun renderNameShouldBe_TEXT() {
        assert(factory.renders.contains(RenderType.TEXT))
    }
}