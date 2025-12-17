package com.example.upsidorkin.data.model

data class ChangePasswordRequest(
    val email: String,
    val newPassword: String
)
