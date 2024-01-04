package com.raka.suitmediatest.ui.thirdscreen

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.raka.suitmediatest.R
import com.raka.suitmediatest.data.local.DataFlashSale
import com.raka.suitmediatest.data.remote.response.DataItem
import com.raka.suitmediatest.databinding.ActivityThirdBinding
import com.raka.suitmediatest.ui.AppViewModelFactory
import com.raka.suitmediatest.ui.secondscreen.FlashSaleAdapter
import com.raka.suitmediatest.ui.thirdscreen.adapter.LoadingAdapter
import com.raka.suitmediatest.ui.thirdscreen.adapter.UserAdapter

class ThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var loading: Dialog
    private val viewModel by viewModels<ThirdViewModel> {
        AppViewModelFactory.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.statusBarColor = resources.getColor(R.color.white, theme)
            }
        }

        loading = Dialog(this)
        binding.swipeRefresh.setOnRefreshListener {
            setupDataUser()
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        setupAdapter()
        setupDataUser()
    }

    private fun setupDataUser() {
        viewModel.user.observe(this){user->
            updateDataUser(user)
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun updateDataUser(user: PagingData<DataItem>) {
        userAdapter.submitData(lifecycle, user)
    }

    private fun setupAdapter() {
        userAdapter = UserAdapter()
        val layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.adapter = userAdapter
        binding.rvUser.adapter = userAdapter.withLoadStateFooter(
            footer = LoadingAdapter{
                userAdapter.retry()
            }
        )

        userAdapter.setOnItemClickCallback(object : UserAdapter.OnClickCallback{
            override fun onItemClicked(data: DataItem) {
                setResultToSecondScreen(data)
            }
        })

        binding.rvUser.layoutManager = layoutManager
        binding.rvUser.addItemDecoration(itemDecoration)

        userAdapter.addLoadStateListener { state->
            binding.swipeRefresh.isRefreshing = state.source.refresh is LoadState.Loading
        }
    }

    private fun setResultToSecondScreen(data: DataItem) {
        val resultData = "${data.firstName}${data.lastName}"
        val resultIntent = Intent()
        resultIntent.putExtra("resultData", resultData)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}