package com.github.nafsan.searchuser.datastore

import android.util.Log
import com.github.nafsan.searchuser.GithubTcgService
import com.github.nafsan.searchuser.model.User



class UserSetRemoteDataStore(private val userServices: GithubTcgService) :
    UserSetDataStore {

    override suspend fun getUser(
        q: String,
        page: Int,
        perPage: Int
    ): MutableList<User>? {
        val response = userServices.search(q, page, perPage)

        if (response.isSuccessful) {
            return response.body()?.items
        } else {
            throw Exception(response.message())
        }


    }


}