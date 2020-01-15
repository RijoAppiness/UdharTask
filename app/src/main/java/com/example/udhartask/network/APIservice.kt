package com.example.udhartask.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor(){
    companion object {
        private var retroInstance: Retrofit? = null
        fun getInstance(): Retrofit {
            if(retroInstance==null){
                retroInstance = createRetrofit()
            }
            return retroInstance as Retrofit
        }
        private fun createRetrofit(): Retrofit {
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
                GsonConverterFactory.create()).
                build()
            return retrofit
        }
    }

}