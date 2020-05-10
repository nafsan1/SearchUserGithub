package com.github.nafsan.searchuser.datastore

import com.github.nafsan.searchuser.model.User


interface UserSetDataStore {
    suspend fun getUser( q: String,
                         page: Int,
                         perPage: Int):MutableList<User>?

}