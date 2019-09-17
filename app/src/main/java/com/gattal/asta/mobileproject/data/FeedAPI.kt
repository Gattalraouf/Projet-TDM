package com.gattal.asta.mobileproject.data

import me.toptas.rssconverter.RssFeed
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface FeedAPI {
    @GET
    fun getRss(@Url url: String): Call<RssFeed>
}