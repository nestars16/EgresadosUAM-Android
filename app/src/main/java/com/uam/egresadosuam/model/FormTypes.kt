package com.uam.egresadosuam.model

import android.util.ArraySet


data class Question(
    private val id: String,
    private val question: String,
    private val type: QuestionType,
    private val possibleAnswers: List<String>,
    private val answers: List<String>,
)

enum class QuestionType {
    TEXT,
    MULTIPLE_CHOICE,
}

data class Form(
    private val id: String = "",
    private val name: String = "",
    private val questions: List<Question> = ArrayList(),
    private val answersCollectedFrom: Set<String> = ArraySet(),
    private val description: String? = null,
    private val published: Boolean = false,
)