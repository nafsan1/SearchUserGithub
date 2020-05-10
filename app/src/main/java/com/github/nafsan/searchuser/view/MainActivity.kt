package com.github.nafsan.searchuser.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


import com.github.nafsan.searchuser.R
import com.github.nafsan.searchuser.adapter.SearchAdapter
import com.github.nafsan.searchuser.databinding.ActivityMainBinding
import com.github.nafsan.searchuser.helper.hideKeyboard
import com.github.nafsan.searchuser.helper.myToast
import com.github.nafsan.searchuser.repository.SetListFactory
import com.github.nafsan.searchuser.repository.UserSetRepository
import com.github.nafsan.searchuser.viewmodel.MainvViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    private lateinit var dataBinding: ActivityMainBinding
    private val perPage: Int = 10
    private var currentPage: Int = 1
    private val searchAdapter: SearchAdapter = SearchAdapter(arrayListOf())
    private var isLoading = false
    private var addDataToAdapter = false
    private var searchText = ""
    private lateinit var mainvViewModel: MainvViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val factory = SetListFactory(UserSetRepository.instance)
        mainvViewModel = ViewModelProvider(this, factory).get(MainvViewModel::class.java)
           /* .apply {
            viewState.observe(this@MainActivity, Observer(this@MainActivity::initData))

        }*/

        setRecycle()
        setEditText()
        initData()
    }

    private fun initData() {
        mainvViewModel.data.observe(this, Observer { data ->
            if (data.size > 0) {
                if (addDataToAdapter) {
                    data.let { searchAdapter.addAll(it) }
                } else {
                    data.let { searchAdapter.addData(it) }
                    dataBinding.recyclerView.smoothScrollToPosition(0)
                }
            } else {
                if (addDataToAdapter) {
                    myToast(this, "No data anymore")
                } else {
                    showError("Data not found")
                }

            }


        })

        mainvViewModel.error.observe(this, Observer {
            if (addDataToAdapter){
                if (it.contains("rate limit exceeded")){
                    showError(getString(R.string.message_api_limit))
                }else{
                    showError(it)
                }
            }else {
                if (it.contains("rate limit exceeded")){
                    showError(getString(R.string.message_api_limit))
                }else{
                    showError(it)
                }
            }


        })

        mainvViewModel.loading.observe(this, Observer { data ->
            run {

                if (data) {
                    if (addDataToAdapter) {
                        showProgressScrool()
                    } else {
                        showProgress()
                    }

                } else {
                    if (addDataToAdapter) {
                        hideProgressScrool()
                    } else {
                        hideProgress()
                    }

                }
            }
        })
    }

    private fun setEditText() {

        dataBinding.edtxSearch.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                hideKeyboard(this)
                mainvViewModel.getSets(edtxSearch.text.toString(), currentPage, perPage)
                searchText = edtxSearch.text.toString()
                addDataToAdapter = false
                currentPage = 1

                return@OnKeyListener true
            }
            false
        })

    }

    private fun setRecycle() {
        dataBinding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 1)
            adapter = searchAdapter

        }

        dataBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                val lastPosition = layoutManager!!
                    .findLastVisibleItemPosition()
                if (lastPosition == searchAdapter.itemCount - 1) {
                    if (lastPosition >= perPage - 1) {
                        if (!isLoading) {
                            isLoading = true
                            addDataToAdapter = true
                            dataBinding.progressBarRecycle.visibility = View.VISIBLE

                            Handler().postDelayed({

                                currentPage++
                                mainvViewModel.getSets(searchText, currentPage, perPage)
                                isLoading = false

                            }, 2000)
                        }
                    }
                }
            }
        })
    }

    private fun showProgress() {
        dataBinding.errorLayout.visibility = View.GONE
        dataBinding.progressBar.visibility = View.VISIBLE
        dataBinding.recyclerView.visibility = View.GONE
    }

    /* private fun showProgressScrool() {
         dataBinding.errorLayout.visibility = View.GONE
         dataBinding.progressBarRecycle.visibility = View.VISIBLE

     }*/

    private fun showProgressScrool() {
        dataBinding.errorLayout.visibility = View.GONE
        dataBinding.progressBarRecycle.visibility = View.VISIBLE

    }

    private fun hideProgress() {

        dataBinding.progressBarRecycle.visibility = View.GONE
        dataBinding.progressBar.visibility = View.GONE
        dataBinding.recyclerView.visibility = View.VISIBLE
    }

    private fun hideProgressScrool() {

        dataBinding.progressBarRecycle.visibility = View.GONE

    }

    private fun showError(message: String) {
        dataBinding.errorLayout.visibility = View.VISIBLE
        dataBinding.textDescription.setText(message)
        dataBinding.recyclerView.visibility = View.GONE
    }

}
