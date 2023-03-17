package com.example.quizapp


data class Question(val resource: Int, val answer: Boolean, var cheated: Boolean = false) {
}