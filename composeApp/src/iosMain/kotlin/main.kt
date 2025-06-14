import androidx.compose.ui.window.ComposeUIViewController
import com.ansar.autoPartsApp.App
import com.ansar.autoPartsApp.di.KoinInjector
import com.ansar.autoPartsApp.di.platformViewModel
import org.koin.dsl.module
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController {
    KoinInjector.init(
        listOf(platformViewModel)
    )
    App()
}
