package com.github.nafsan.searchuser.repository

import android.util.Log
import com.github.nafsan.searchuser.datastore.UserSetDataStore
import com.github.nafsan.searchuser.model.User


class UserSetRepository private constructor() : BaseRepository<UserSetDataStore>() {

    suspend fun getUser(
        q: String,
        page: Int,
        perPage: Int
    ): MutableList<User>? {

        val response = remoteDataStore?.getUser(q, page, perPage)
        return response

    }

    companion object {
        val instance by lazy { UserSetRepository() }
    }
}