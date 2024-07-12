package com.uam.egresadosuam.model

import android.util.ArraySet


data class Question(
    val id: String,
    val question: String,
    val type: QuestionType,
    val possibleAnswers: List<String>,
    val answers: MutableList<String>,
)

enum class QuestionType {
    TEXT,
    MULTIPLE_CHOICE,
}

data class Form(
    val id: String = "",
    val name: String = "",
    val questions: List<Question> = ArrayList(),
    val answersCollectedFrom: MutableSet<String> = ArraySet(),
    val description: String = "",
    val published: Boolean = false,
)