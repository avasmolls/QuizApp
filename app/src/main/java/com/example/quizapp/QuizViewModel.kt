package com.example.quizapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {
    private val questions: List<Question> = listOf(
        Question(R.string.question1, true, false),
        Question(R.string.question2, false, false),
        Question(R.string.question3, true, false),
        Question(R.string.question4, true, false),
        Question(R.string.question5, false, false)
    )

    private val _gameWon = MutableLiveData(false)
    val gameWon: LiveData<Boolean>
        get() = gameWon

    val _currentQuestionNumber = MutableLiveData(0)
    val currentQuestionNumber: LiveData<Int>
        get() = _currentQuestionNumber

    private var _questionsCorrect = 0
    val questionsCorrect: Int
        get() = questionsCorrect

    private var _questionsWrong = 0
    val questionsWrong: Int
        get() = questionsWrong

    val currentQuestionAnswer: Boolean
        get() = questions[_currentQuestionNumber.value ?: 0].answer

    val currentQuestionText: Int
        get() = questions[_currentQuestionNumber.value ?: 0].resource

    val currentQuestionCheatStatus: Boolean
        get() = questions[_currentQuestionNumber.value ?: 0].cheated

    fun setCheatedStatusForCurrentQuestion(cheater: Boolean) {
        questions[currentQuestionNumber.value ?: 0].cheated = cheater
    }

    fun checkIfGameWon() {
        _gameWon.value = (questionsCorrect == 3)
    }

    fun nextQuestion() {
        val current = currentQuestionNumber.value ?: 0
        if (current + 1 > questions.size - 1) {
            _currentQuestionNumber.value = 0
        } else {
            _currentQuestionNumber.value = current + 1
        }
    }

    fun checkAnswer(usersAnswer: Boolean): Boolean {
        if (currentQuestionAnswer == usersAnswer) {
            if (currentQuestionCheatStatus == false) {
                _questionsCorrect++
                checkIfGameWon()
            }
            return true
        } else {
            _questionsWrong++
            return false
        }
    }


}