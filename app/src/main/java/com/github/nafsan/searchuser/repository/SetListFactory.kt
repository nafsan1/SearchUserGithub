package com.github.nafsan.searchuser.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.nafsan.searchuser.viewmodel.MainvViewModel


class SetListFactory (private val setRepository: UserSetRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainvViewModel::class.java))
            return MainvViewModel(setRepository) as T
        throw IllegalArgumentException()
    }
}