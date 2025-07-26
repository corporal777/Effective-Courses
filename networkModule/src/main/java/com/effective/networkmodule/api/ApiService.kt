package com.effective.networkmodule.api

import com.effective.networkmodule.model.CoursesResponse
import io.reactivex.Maybe
import retrofit2.http.GET

interface ApiService {

    @GET("u/0/uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download")
    fun getCourses(): Maybe<CoursesResponse>
}