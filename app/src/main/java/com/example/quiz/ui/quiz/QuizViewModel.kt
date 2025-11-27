package com.example.quiz.ui.quiz

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quiz.data.QuizQuestion
import com.example.quiz.data.QuizRepository

class QuizViewModel(category: String): ViewModel() {

    private val questions = QuizRepository.getQuestions(category)

    val totalQuestions: Int
        get() = questions.size

    var currentIndex by mutableStateOf(0)
        private set

    var score by mutableStateOf(0)
        private set

    var selectedAnswerIndex by mutableStateOf(-1)
        private set

    var isAnswerChecked by mutableStateOf(false)
        private set

    var isCorrect by mutableStateOf<Boolean?>(null)
        private set

    val currentQuestion: QuizQuestion
        get() = questions[currentIndex]

    // 선택 단순 처리 (선택/취소)
    fun selectAnswer(index: Int) {
        selectedAnswerIndex =
            if (selectedAnswerIndex == index) -1 // 같은 걸 누르면 취소
            else index
    }

    // 제출 버튼 눌렀을 때 실행됨
    fun submitAnswer(): Boolean {
        if (selectedAnswerIndex == -1) return false // 선택 없으면 제출 불가

        isCorrect = (selectedAnswerIndex == currentQuestion.answerIndex)
        isAnswerChecked = true

        if (isCorrect == true) score++

        return true
    }

    fun goToNext() {
        // 다음 문제로 넘어가기 전에 state초기화
        selectedAnswerIndex = -1
        isAnswerChecked = false
        isCorrect = null

        currentIndex++
    }
    fun isLastQuestion(): Boolean = currentIndex == questions.size - 1

    fun isFinished(): Boolean = currentIndex >= questions.size

    companion object {
        fun provideFactory(category: String): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return QuizViewModel(category) as T
                }
            }
    }
}