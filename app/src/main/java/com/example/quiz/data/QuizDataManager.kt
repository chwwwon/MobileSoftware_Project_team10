package com.example.quiz.data

data class RankingEntry(val score: Int, val topic: String, val date: String)
data class WrongAnswer(val question: QuizQuestion, val selectedIndex: Int)

object QuizDataManager {
    val rankingList = mutableListOf<RankingEntry>()
    val currentWrongAnswers = mutableListOf<WrongAnswer>()

    fun addRanking(score: Int, topic: String, date: String) {
        rankingList.add(RankingEntry(score, topic, date))
        rankingList.sortByDescending { it.score }
    }

    fun clearCurrentWrongAnswers() {
        currentWrongAnswers.clear()
    }

    fun addWrongAnswer(question: QuizQuestion, selectedIndex: Int) {
        currentWrongAnswers.add(WrongAnswer(question, selectedIndex))
    }
}