package com.example.quizapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {
    private val questions: List<Question> = listOf(
        Question(R.string.question1, true,  false),
        Question(R.string.question2, false, false),
        Question(R.string.question3, true,  false),
        Question(R.string.question4, true, false),
        Question(R.string.question5, false, false)
    )

    private val _gameWon = MutableLiveData(false)
        val gameWon: Boolean
            get() = gameWon

    val currentQuestionNumber: LiveData<Int>
        get() = currentQuestionNumber

    private val _questionsCorrect = MutableLiveData(0)
    val questionsCorrect: Int
        get() = questionsCorrect

    private val _questionsWrong = MutableLiveData(0)
    val questionsWrong: Int
        get() = questionsWrong

    val currentQuestionAnswer: Boolean
        get() = currentQuestionAnswer

    val currentQuestionText: String
        get() = currentQuestionText

    val currentQuestionCheatStatus : Boolean
        get() = currentQuestionCheatStatus

    fun setCheatedStatusForCurrentQuestion(cheater: Boolean) {
        val currentQuestion = questions[currentQuestionNumber.toString().toInt()]
        currentQuestion.cheated = cheater
    }

    fun checkIfGameWon() : Boolean {
        return if (questionsCorrect.toString().toInt() == 3) {
            _gameWon.value = true
            return gameWon.toString().toBoolean()
        } else
            return gameWon.toString().toBoolean()
    }


}