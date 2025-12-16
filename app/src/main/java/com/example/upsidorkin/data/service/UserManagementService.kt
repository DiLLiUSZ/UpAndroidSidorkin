package com.example.upsidorkin.data.service

import com.example.upsidorkin.data.model.SignUpRequest
import com.example.upsidorkin.data.model.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

const val API_KEY =
    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inl1cmZmendpa2N3dGNwaWt2a2t1Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTkyNjA3NTIsImV4cCI6MjA3NDgzNjc1Mn0.nfUxFx21J5MYGjNJdujY_2wU_BFsqeUQr6-qfTBKzAI"

interface UserManagementService {

    @Headers("apikey: $API_KEY", "Content-Type: application/json")
    @POST("auth/v1/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>
}
