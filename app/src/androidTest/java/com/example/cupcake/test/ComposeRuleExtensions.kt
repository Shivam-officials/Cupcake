package com.example.cupcake

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule


/**
 *
 *  generic funtion take generic parameter first before the funtion name so that those generic parameters(received data type as input)
 *  * can be used also in the normal parameters
 *  *
 *  * ****************    [A:ComponentActivity]    *******************
 *  *  in this above parameter type
 *  * A -> This is the name of the generic type parameter. It acts as a placeholder for the actual activity type.
 *  * : ComponentActivity -> This specifies a constraint on the type parameter A. It enforces that A must be a subclass of ComponentActivity.
 *
 * ActivityScenarioRule is the implementation testRule interface
 * It Manages the lifecycle of an Activity for testing.
 * And Provides methods to launch, pause, resume, and destroy the Activity.
 *
 *
 */


fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithStringId(
    @StringRes id: Int
): SemanticsNodeInteraction = onNodeWithText(activity.getString(id))

/**
 * fun <R1 : TestRule, A : ComponentActivity> AndroidComposeTestRule<R1, A>.onNodeWithStringId(
 *     @StringRes id: Int
 * ): SemanticsNodeInteraction = onNodeWithText(activity.getString(id))
 **/
