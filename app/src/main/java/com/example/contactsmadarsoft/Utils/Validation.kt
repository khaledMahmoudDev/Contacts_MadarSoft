package com.example.contactsmadarsoft.Utils

object Validation {
    fun validateInput(input: String): ValidationMSG {
        return if (input == "") {
            ValidationMSG.Empty
        } else if (!input.matches("^[\\u0621-\\u064Aa-zA-Z\\d\\-_\\s]+\$".toRegex())) {
            ValidationMSG.NotMatchingNames
        } else if (input.length < 3) {
            ValidationMSG.TooSmall
        } else ValidationMSG.Good
    }

    fun validationResult(it: ValidationMSG?): String? {
        return when (it) {
            ValidationMSG.Empty -> "Can not be empty"
            ValidationMSG.TooSmall -> "Too Small"
            ValidationMSG.NotMatchingNames -> "Must contain only letters numbers and underscores"
            else -> null
        }
    }

}

enum class ValidationMSG {
    Empty,
    TooSmall,
    NotMatchingNames,
    Good
}