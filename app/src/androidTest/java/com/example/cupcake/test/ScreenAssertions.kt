package com.example.cupcake

import androidx.navigation.NavController
import junit.framework.TestCase.assertEquals


fun NavController.assertCurrentScreenRouteName(expectedRouteName: String) {
    assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
}

