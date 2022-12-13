package com.example.memegeneratorusingretrofit

import retrofit2.Call
import retrofit2.http.GET




interface ApiInterface {
    @GET("gimme/20")//GET ANNOTATION TO SPECIFY WE ARE MAKING A GET REQUEST
    //ADD END POINT OF URL TO THE GET ANNOTATION
    fun Getdata():Call<MemeList>
}