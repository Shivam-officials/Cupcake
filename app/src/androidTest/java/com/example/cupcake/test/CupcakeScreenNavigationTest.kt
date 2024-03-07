package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.cupcake.CupcakeApp
import org.junit.Before
import org.junit.Rule

class CupcakeScreenNavigationTest {

    lateinit var navController: TestNavHostController

    @get: Rule
//todo    val composeTestRule = createComposeRule()
//todo    val composeTestRule = createAndroidComposeRule<MainActivity>()
            /** OR */
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()


    /**
     * before function which set content on an activity with real android context
     * and TestNavController object
     */
    @Before
    fun SetupScreenWithNavController() {

        composeTestRule.setContent {
//todo           val navcontroller = rememberNavController()  // this also

            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            CupcakeApp(navController = navController)
        }

    }


}