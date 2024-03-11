package com.example.cupcake

import android.icu.text.SimpleDateFormat
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Calendar
import java.util.Locale


class CupcakeScreenNavigationTest {

    val TAG = "CupcakeScreenNavigationTest"

    /**
     * declaring TestNavHostController object so that it can be accessible it in all tests for
     * testing the navigation
     */
    lateinit var navController: NavHostController

    @get: Rule
// /** todo */    val composeTestRule = createComposeRule()
//todo    val composeTestRule = createAndroidComposeRule<MainActivity>()
            /** OR */
            /**
             * Note: To access to an empty activity, the code uses ComponentActivity instead of MainActivity.
             */

    val composeTestRule = createAndroidComposeRule<ComponentActivity>()


    /**
     * before Rule function which set content on an activity with real android context
     * and TestNavController object for testing the navigation
     */
    @Before
    fun SetupScreenWithNavController() {

        composeTestRule.setContent {
            //todo     navController = rememberNavController()  // this also

            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }

            CupcakeApp(navController = navController)
        }
    }

    /**
     * * verifies the start to flavour screen navigation
     */

    /**
     * verify the startDestination matches the Start route
     */
    @Test
    fun cupCakeNavHost_verifyStartDestination() {

        //using self made extension function to just check the current screen route
        navController.assertCurrentScreenRouteName(CupcakeScreen.start.name)
        composeTestRule.onNodeWithStringId(R.string.one_cupcake).assertExists("nhi hai bhai")

    }

    /**
     * verifies the Back arrow does not shown on the start screen
     */
    @Test
    fun cupCakeNavHost_verifyBackNavigationNotShownOnStartOrderScreen() {
        composeTestRule.onNodeWithStringId(R.string.back_button).assertDoesNotExist()
    }


    @Test
    fun cupcakeNavHost_clickOneCupCake_NavigateToSelectFlavourScreen() {

        composeTestRule.onNodeWithStringId(R.string.one_cupcake)
            .performClick()

        //asserting the screen by the self made extension function
        navController.assertCurrentScreenRouteName(CupcakeScreen.flavor.name)
    }


    /**
     * verifies the flavour screen navigation
     */
    @Test
    fun cupcakeNavHost_clickNextButtonOnFlavourScreen_navigateToPickupScreen() {

        //navigating to the falvour screen
        navigateToFlavourScreen()

        //finding the next-button with string id and then performing click on it
        composeTestRule.onNodeWithStringId(R.string.next)
            .performClick()

        //asserting that the current screen matches with pickup route
        navController.assertCurrentScreenRouteName(CupcakeScreen.pickup.name)
    }


    @Test
    fun cupcakeNavHost_clickCancelButtonOnFlavourScreen_navigateToStartScreen() {
        navigateToFlavourScreen()
        composeTestRule.onNodeWithStringId(R.string.cancel).performClick()

        navController.assertCurrentScreenRouteName(CupcakeScreen.start.name)
    }

    @Test
    fun cupcakeNavHost_clickBackArrowOnFlavourScreen_navigateToStartScreen() {
        navigateToFlavourScreen()
//        composeTestRule.onNodeWithStringId(R.string.back_button).performClick()
        performNavigationUp()
        navController.assertCurrentScreenRouteName(CupcakeScreen.start.name)
    }


    /** pickup Screen */
    @Test
    fun cupcakeNavHost_clickNextButtonOnPickupScreen_navigateSummaryScreen() {
        //navigate to the pickupScreen
        navigateToPickupScreen()

        //click on the next screen
        composeTestRule.onNodeWithStringId(R.string.next).performClick()

        //asserting that the current screen is summary screen
        navController.assertCurrentScreenRouteName(CupcakeScreen.summary.name)
    }

    @Test
    fun cupcakeNavHost_clickBackArrowOnPickupScreen_navigateToFlavourScreen() {
        navigateToPickupScreen()
        performNavigationUp()
        navController.assertCurrentScreenRouteName(CupcakeScreen.flavor.name)
    }

    @Test
    fun cupcakeNavHost_clickCancelButtonOnPickupScreen_navigateStartScreen() {
        navigateToPickupScreen()
        composeTestRule.onNodeWithStringId(R.string.cancel).performClick()
        navController.assertCurrentScreenRouteName(CupcakeScreen.start.name)
    }

    /** summary screen */
    @Test
    fun cupcakeNavHost_clickCancelButtonOnSummaryScreen_navigateToStartScreen() {
        navigateToSummaryScreen()
        composeTestRule.onNodeWithStringId(R.string.cancel).performClick()
        navController.assertCurrentScreenRouteName(CupcakeScreen.start.name)
    }

    @Test
    fun cupcakeNavHost_clickBackArrowOnSummaryScreen_navigateToPickupScreen() {
        navigateToSummaryScreen()
        performNavigationUp()
        navController.assertCurrentScreenRouteName(CupcakeScreen.pickup.name)
    }

    /** helper functions */

    private fun performNavigationUp() {

        // getting the back text so that we can find that semantic with its contentDescreption
        // perfrom action on it
        val backText = composeTestRule.activity.getString(R.string.back_button)

        /** if we use find it directly with text then we wont be able to perform input/click on it */
        composeTestRule.onNodeWithContentDescription(backText).performClick()
    }

    private fun navigateToFlavourScreen() {
        composeTestRule.onNodeWithStringId(R.string.one_cupcake)
            .performClick()

        // enabling the next as it will be enable selecting on of the action
        composeTestRule.onNodeWithStringId(R.string.chocolate)
            .performClick()
    }


    private fun navigateToPickupScreen() {
        // navigate to the flavour screen
        navigateToFlavourScreen()

        //click next button
        composeTestRule.onNodeWithStringId(R.string.next).performClick()

        //click on the date string obtained from getFormattedDate() to enable the next button
        composeTestRule.onNodeWithText(getFormattedDate()).performClick()
    }

    fun navigateToSummaryScreen() {
        navigateToPickupScreen()
        composeTestRule.onNodeWithStringId(R.string.next).performClick()
    }

    //get the formatted date string for picking up the next following day of current day
    private fun getFormattedDate(): String {
        val calender = Calendar.getInstance()
        calender.add(Calendar.DATE, 1)

        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        return formatter.format(calender.time)
    }


}



