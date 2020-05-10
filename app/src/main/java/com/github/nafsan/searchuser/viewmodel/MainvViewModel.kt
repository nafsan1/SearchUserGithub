package com.github.nafsan.searchuser.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.nafsan.searchuser.model.User
import com.github.nafsan.searchuser.repository.UserSetRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class MainvViewModel(private val userSet: UserSetRepository) : ViewModel() {


    val error = MutableLiveData<String>()
    val data = MutableLiveData<List<User>>()
    val loading = MutableLiveData<Boolean>()

    fun getSets(q: String, page: Int, perPage: Int) = viewModelScope.launch {
        loading.postValue(true)
        try {

            val dataUser = userSet.getUser(q, page, perPage)
            loading.postValue(false)
            data.postValue(dataUser)

        } catch (ex: Exception) {

            loading.postValue(false)
            error.postValue(ex.message.toString())

        }
    }
}