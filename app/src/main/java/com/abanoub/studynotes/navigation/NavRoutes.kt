package com.abanoub.studynotes.navigation

sealed class NavRoutes(val route: String) {
    data object LandingRoute : NavRoutes("landing")
    data object SubjectRoute : NavRoutes("subject/{subjectId}"){
        fun createRoute(subjectId: Int?) = "subject/$subjectId"
    }
    data object TaskRoute : NavRoutes("task/{taskId}/{subjectId}"){
        fun createRoute(taskId: Int?, subjectId: Int?) = "task/$taskId/$subjectId"
    }
    data object SessionRoute : NavRoutes("session")
}