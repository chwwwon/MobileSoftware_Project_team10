package com.example.quiz.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.quiz.ui.main.MainScreen
import com.example.quiz.ui.quiz.QuizScreen
import com.example.quiz.ui.result.RankingScreen
import com.example.quiz.ui.result.ResultScreen
import com.example.quiz.ui.result.WrongNoteScreen

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        // 메인 화면
        composable("main") {
            MainScreen(
                onSelectCategory = { category ->
                    navController.navigate("quiz/$category")
                },
                onShowRanking = {
                    navController.navigate("ranking")
                }
            )
        }

        // 퀴즈 화면 (카테고리 전달)
        composable("quiz/{category}") { backStack ->
            val category = backStack.arguments?.getString("category") ?: "common"

            QuizScreen(
                category = category,
                onFinish = { score ->
                    navController.navigate("result/$score") {
                        popUpTo("main") { inclusive = false }
                    }
                }
            )
        }

        // 결과 화면
        composable("result/{score}") { backStack ->
            val score = backStack.arguments?.getString("score")!!.toInt()
            ResultScreen(
                score = score,
                onGoHome = { navController.navigate("main") { popUpTo("main") { inclusive = true } } },
                onShowWrongNote = { navController.navigate("wrong_note") },
                onShowRanking = { navController.navigate("ranking") }
            )
        }
        // 오답 노트 화면
        composable("wrong_note") {
            WrongNoteScreen(onBack = { navController.popBackStack() })
        }

        // 랭킹 화면
        composable("ranking") {
            RankingScreen(onBack = { navController.popBackStack() })
        }
    }
}