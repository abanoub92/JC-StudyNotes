package com.abanoub.studynotes.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
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
                    navController.navigate(NavRoutes.TaskRoute.createRoute(null, it))
                },
                onTaskCardClicked = { taskId, subjectId ->
                    navController.navigate(NavRoutes.TaskRoute.createRoute(taskId, subjectId))
                }
            )
        }

        composable(NavRoutes.TaskRoute.route) {
            TaskScreen(
                onBackButtonClicked = { navController.popBackStack() }
            )
        }

        composable(
            route = NavRoutes.SessionRoute.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "study_notes://landing/session"
                    action = Intent.ACTION_VIEW
                }
            )
        ) {
            SessionScreen(
                onBackButtonClicked = { navController.popBackStack() }
            )
        }
    }
}