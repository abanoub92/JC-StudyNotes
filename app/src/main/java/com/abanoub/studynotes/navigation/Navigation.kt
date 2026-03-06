package com.abanoub.studynotes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abanoub.studynotes.screens.landing.LandingScreen
import com.abanoub.studynotes.screens.session.SessionScreen
import com.abanoub.studynotes.screens.subject.SubjectScreen
import com.abanoub.studynotes.screens.task.TaskScreen

@Composable
fun NavHost(){

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.LandingRoute.route
    ) {

        composable(NavRoutes.LandingRoute.route) {
            LandingScreen(
                onSubjectCardClicked = {
                    navController.navigate(NavRoutes.SubjectRoute.createRoute(it))
                },
                onTaskCardClicked = {
                    navController.navigate(NavRoutes.TaskRoute.createRoute(it, null))
                },
                onStartSessionButtonClicked = { navController.navigate(NavRoutes.SessionRoute.route) }
            )
        }

        composable(NavRoutes.SubjectRoute.route) {
            SubjectScreen(
                onBackButtonClicked = { navController.popBackStack() },
                onAddTaskButtonClicked = {
                    navController.navigate(NavRoutes.TaskRoute.createRoute(null, -1))
                },
                onTaskCardClicked = {
                    navController.navigate(NavRoutes.TaskRoute.createRoute(it, null))
                }
            )
        }

        composable(NavRoutes.TaskRoute.route) {
            TaskScreen(
                onBackButtonClicked = { navController.popBackStack() }
            )
        }

        composable(NavRoutes.SessionRoute.route) {
            SessionScreen(
                onBackButtonClicked = { navController.popBackStack() }
            )
        }
    }
}