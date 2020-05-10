package com.github.nafsan.searchuser.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView



import com.github.nafsan.searchuser.R
import com.github.nafsan.searchuser.databinding.UserListBinding
import com.github.nafsan.searchuser.model.User


class SearchAdapter(
    private val userList: ArrayList<User>
) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<UserListBinding>(inflater, R.layout.user_list, parent, false)
        return SearchViewHolder(view)
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

        holder.view.user = userList[position]

    }

    class SearchViewHolder(var view: UserListBinding) : RecyclerView.ViewHolder(view.root)

    fun addData(list: List<User>) {
        userList.clear()
        userList.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        userList.clear()
        notifyDataSetChanged()
    }

    fun addAll(list: List<User>) {
        userList.addAll(list)
        notifyDataSetChanged()
    }

}