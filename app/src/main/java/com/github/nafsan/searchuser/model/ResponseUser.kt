package com.github.nafsan.searchuser.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseUser (

    @SerializedName("total_count")
    @Expose
    var totalCount: Int?,
    @SerializedName("incomplete_results")
    @Expose
    var incompleteResults: Boolean?,
    var items: MutableList<User>
)