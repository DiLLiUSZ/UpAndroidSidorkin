package com.example.upsidorkin.data.service

import com.example.upsidorkin.data.model.ChangePasswordRequest
import com.example.upsidorkin.data.model.SignInRequest
import com.example.upsidorkin.data.model.SignInResponse
import com.example.upsidorkin.data.model.SignUpRequest
import com.example.upsidorkin.data.model.SignUpResponse
import com.example.upsidorkin.data.model.VerifyOtpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import retrofit2.http.Header

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

    // Получить профиль по user_id (uuid из auth.users)
    // baseUrl в RetrofitInstance: https://yurffzwikcwtcpikvkku.supabase.co/
    // => полный URL: https://.../rest/v1/profiles?user_id=eq.<uuid>&select=*
    @Headers("apikey: $API_KEY")
    @GET("rest/v1/profiles")
    suspend fun getProfile(
        @Header("Authorization") authHeader: String,      // "Bearer <access_token>"
        @Query("user_id") userIdFilter: String,           // "eq.<uuid>"
        @Query("select") select: String = "*"             // какие поля вернуть
    ): List<ProfileDto>

    // Обновить профиль по user_id
    // PUT /rest/v1/profiles?user_id=eq.<uuid>
    @Headers("apikey: $API_KEY", "Content-Type: application/json")
    @PUT("rest/v1/profiles")
    suspend fun updateProfile(
        @Header("Authorization") authHeader: String,
        @Query("user_id") userIdFilter: String,           // "eq.<uuid>"
        @Body body: Map<String, Any?>                    // firstname, lastname, address, phone, photo и т.п.
    ): Response<Unit>
}
