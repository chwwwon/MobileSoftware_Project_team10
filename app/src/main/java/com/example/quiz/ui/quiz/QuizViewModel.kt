package com.example.quiz.ui.quiz

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quiz.data.QuizDataManager
import com.example.quiz.data.QuizQuestion
import com.example.quiz.data.QuizRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class QuizViewModel(private val category: String): ViewModel() {

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

    init {
        if(currentIndex == 0) {
            QuizDataManager.clearCurrentWrongAnswers()
        }
    }

    fun selectAnswer(index: Int) {
        selectedAnswerIndex =
            if (selectedAnswerIndex == index) -1
            else index
    }

    fun submitAnswer(): Boolean {
        if (selectedAnswerIndex == -1) return false

        isCorrect = (selectedAnswerIndex == currentQuestion.answerIndex)
        isAnswerChecked = true

        if (isCorrect == true) {
            score += 10
        } else {
            QuizDataManager.addWrongAnswer(currentQuestion, selectedAnswerIndex)
        }

        return true
    }

    fun goToNext() {
        selectedAnswerIndex = -1
        isAnswerChecked = false
        isCorrect = null

        currentIndex++
    }
    fun isLastQuestion(): Boolean = currentIndex == questions.size - 1

    fun saveRank() {
        val sdf = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
        val date = sdf.format(Date())
        val categoryName = when(category) {
            "common" -> "상식"
            "math" -> "암산"
            "capital" -> "수도"
            else -> "기타"
        }
        QuizDataManager.addRanking(score, categoryName, date)
    }

    companion object {
        fun provideFactory(category: String): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return QuizViewModel(category) as T
                }
            }
    }
}