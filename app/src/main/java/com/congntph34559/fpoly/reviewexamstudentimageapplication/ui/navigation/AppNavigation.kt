package com.congntph34559.fpoly.reviewexamstudentimageapplication.ui.navigation

import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.congntph34559.fpoly.reviewexamstudentimageapplication.room.db.AppDatabase
import com.congntph34559.fpoly.reviewexamstudentimageapplication.ui.screens.GetLayoutCreateAndUpdate
import com.congntph34559.fpoly.reviewexamstudentimageapplication.ui.screens.GetLayoutList
import com.congntph34559.fpoly.reviewexamstudentimageapplication.ui.screens.GetLayoutWelcome
import com.congntph34559.fpoly.reviewexamstudentimageapplication.ui.screens.StudentViewModel

enum class ROUTE {
    WELCOME,
    LIST,
    CUD
}

@Composable
fun AppNavigation(db: AppDatabase) {
    val navController = rememberNavController()

    NavHost(
        modifier = Modifier.safeContentPadding(),
        navController = navController,
        startDestination = ROUTE.WELCOME.name
    ) {

        composable(ROUTE.WELCOME.name) { GetLayoutWelcome(navController) }
        composable(ROUTE.LIST.name) {
            GetLayoutList(
                navController,
                StudentViewModel(db)
            )
        }
        composable(
            "${ROUTE.CUD.name}/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) {
            GetLayoutCreateAndUpdate(
                navController,
                StudentViewModel(db),
                it.arguments?.getInt("id", 0)
            )
        }


    }

}