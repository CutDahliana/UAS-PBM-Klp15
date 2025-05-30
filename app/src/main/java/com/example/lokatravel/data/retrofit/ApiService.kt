package com.example.lokatravel.data.retrofit
import com.example.lokatravel.BuildConfig
import com.example.lokatravel.data.response.LoginResponse
import com.example.lokatravel.data.response.NewsResponse
import com.example.lokatravel.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    //@POST("/signup")
   // fun register(@Body requestBody: RegisterRequest): Call<RegisterResponse>

   // @POST("login")
   // fun login(@Body requestBody: LoginRequest): Call<LoginResponse>

    @GET("top-headlines?country=us&category=business")
    suspend fun getNews(
        @Query("apiKey") apiKey: String
    ): NewsResponse
}

