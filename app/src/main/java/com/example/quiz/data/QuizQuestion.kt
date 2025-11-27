package com.example.quiz.data

data class QuizQuestion(

    val question: String,
    val choices: List<String>,
    val answerIndex: Int
)
