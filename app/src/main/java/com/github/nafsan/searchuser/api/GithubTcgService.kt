
package com.github.nafsan.searchuser

import com.github.nafsan.searchuser.model.ResponseUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GithubTcgService {

    @GET("search/users")
    @Headers("Content-Type: application/json")
    suspend fun search(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<ResponseUser>
}