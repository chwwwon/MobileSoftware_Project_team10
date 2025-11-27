package com.example.quiz.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.quiz.ui.quiz.QuizScreen
import com.example.quiz.ui.result.ResultScreen
import com.example.quiz.MainActivity
import com.example.quiz.ui.main.MainScreen

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        // 메인 화면
        composable("main") {
            MainScreen { category ->
                navController.navigate("quiz/$category")
            }
        }

        // 퀴즈 화면 (카테고리 전달)
        composable("quiz/{category}") { backStack ->
            val category = backStack.arguments?.getString("category") ?: "common"

            QuizScreen(
                category = category,
                onFinish = { score ->
                    navController.navigate("result/$score")
                }
            )
        }

        // 결과 화면
        composable("result/{score}") { backStack ->
            val score = backStack.arguments?.getString("score")!!.toInt()
            ResultScreen(
                score = score,
                onGoHome = { navController.navigate("main") }
            )
        }
    }
}