import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dam2jms.loginregistrefirebase.navigation.AppScreens
import com.dam2jms.loginregistrefirebase.screens.HomeScreen
import com.dam2jms.loginregistrefirebase.screens.LoginScreen
import com.dam2jms.loginregistrefirebase.screens.RegisterScreen

@Composable
fun appNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route) {
        composable(route = AppScreens.LoginScreen.route) { LoginScreen(navController) }
        composable(route = AppScreens.HomeScreen.route) { HomeScreen(navController) }
        composable(route = AppScreens.RegisterScreen.route) { RegisterScreen(navController) }
    }
}

