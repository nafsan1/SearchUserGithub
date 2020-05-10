package com.github.nafsan.searchuser


import android.app.Application

import com.github.nafsan.searchuser.datastore.UserSetRemoteDataStore
import com.github.nafsan.searchuser.repository.UserSetRepository

class BaseApplication :Application() {
    override fun onCreate() {
        super.onCreate()

        val userTcgService = RetrofitApp.GITHUB_TCG_SERVICE

        UserSetRepository.instance.apply {
            init(
                UserSetRemoteDataStore(
                    userTcgService
                )

            )
        }

    }
}