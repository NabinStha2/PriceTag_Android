package com.example.price_tag.utils

class Validations {

    fun validateEmail(input: String): String? {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)\$".toRegex()
        return if (!emailRegex.matches(input)) {
            "Invalid email format"
        } else {
            null
        }
    }

    fun validatePhoneNumber(input: String): String? {
        val phoneNumberRegex = "^(98|97)\\d{8}\$".toRegex() // For mobile numbers
        return if (!phoneNumberRegex.matches(input)) {
            "Invalid phone number format"
        } else {
            null
        }
    }

    fun validatePasswords(password: String): String? {
        val passwordRegex = "^.{8,}\$".toRegex()
        return if (!passwordRegex.matches(password)) {
            "Password must be at least 8 characters long"
        } else {
            null
        }
    }

    fun validateConfirmPasswords(password: String, confirmPassword: String): String? {
        return if (password != confirmPassword) {
            "Passwords do not match"
        } else {
            null
        }
    }

    fun validateFullName(input: String): String? {
        return when {
            input.isBlank() -> "Name cannot be empty"
            input.split(" ").size < 2 -> "Please enter both first and last names"
            !input.all { it.isLetter() || it.isWhitespace() } -> "Name can only contain letters and spaces"
            else -> null
        }
    }

}