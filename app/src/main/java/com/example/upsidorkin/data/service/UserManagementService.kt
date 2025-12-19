package com.example.upsidorkin.data.service

import com.example.upsidorkin.data.model.*
import retrofit2.Response
import retrofit2.http.*

const val API_KEY =
    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inl1cmZmendpa2N3dGNwaWt2a2t1Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTkyNjA3NTIsImV4cCI6MjA3NDgzNjc1Mn0.nfUxFx21J5MYGjNJdujY_2wU_BFsqeUQr6-qfTBKzAI"

// DTO для таблицы profiles
data class ProfileDto(
    val id: String?,
    val user_id: String?,
    val photo: String?,
    val firstname: String?,
    val lastname: String?,
    val address: String?,
    val phone: String?
)

interface UserManagementService {

    // ---------- AUTH ----------

    @Headers("apikey: $API_KEY", "Content-Type: application/json")
    @POST("auth/v1/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>

    @Headers("apikey: $API_KEY", "Content-Type: application/json")
    @POST("auth/v1/token?grant_type=password")
    suspend fun signIn(@Body signInRequest: SignInRequest): Response<SignInResponse>

    @Headers("apikey: $API_KEY", "Content-Type: application/json")
    @POST("auth/v1/verify")
    suspend fun verifyOTP(@Body verifyOtpRequest: VerifyOtpRequest): Response<Any>

    @Headers("apikey: $API_KEY", "Content-Type: application/json")
    @POST("auth/v1/recover")
    suspend fun recoverPassword(@Body body: Map<String, String>): Response<Any>

    @Headers("apikey: $API_KEY", "Content-Type: application/json")
    @POST("change-password")
    suspend fun changePassword(@Body body: ChangePasswordRequest): Response<Any>

    // ---------- PROFILES (REST) ----------

    @Headers("apikey: $API_KEY")
    @GET("rest/v1/profiles")
    suspend fun getProfile(
        @Header("Authorization") authHeader: String,   // Bearer <token>
        @Query("user_id") userIdFilter: String,        // eq.<uuid>
        @Query("select") select: String = "*"
    ): List<ProfileDto>

    @Headers("apikey: $API_KEY", "Content-Type: application/json")
    @PUT("rest/v1/profiles")
    suspend fun updateProfile(
        @Header("Authorization") authHeader: String,
        @Query("user_id") userIdFilter: String,
        @Body body: Map<String, Any?>
    ): Response<Unit>
}
