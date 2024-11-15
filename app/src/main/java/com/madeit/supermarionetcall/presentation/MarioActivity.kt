package com.madeit.supermarionetcall.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.madeit.supermarionetcall.data.retrofit.RetrofitInstance
import com.madeit.supermarionetcall.databinding.ActivityMarioBinding
import com.madeit.supermarionetcall.presentation.adapter.MarioAdapter
import com.madeit.supermarionetcall.presentation.adapter.MarioSearchedItemAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MarioActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMarioBinding.inflate(layoutInflater) }
    private val marioAdapter = MarioAdapter()
    private val searchedItemAdapter = MarioSearchedItemAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        prepareRecyclerView()
        prepareSearchRcView()
        getDataFromInternet()
        btnSearch()
    }

    private fun prepareRecyclerView() {
        binding.recyclerviewAllMarios.apply {
            adapter = marioAdapter
            layoutManager =
                LinearLayoutManager(this@MarioActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun getDataFromInternet() {
        progressBarVisibility(false)
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.createMarioApi().getAllMarios()
                if (response.isSuccessful) {
                    progressBarVisibility(false)
                    response.body()?.marios?.let { marioList ->
                        marioAdapter.updateMariosList(marioList)
                    }
                } else {
                    Toast.makeText(this@MarioActivity, "Failed to load data", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@MarioActivity, "${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                progressBarVisibility(true)
            }
        }
    }

    private fun btnSearch() {
        binding.btnSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (it.isNotEmpty()) {
                        searchMarios(it)
                    } else {
                        showMainViews()
                        clearSearchResults()
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.isNotEmpty()) {
                        searchMarios(it)
                    } else {
                        showMainViews()
                        clearSearchResults()
                    }
                }
                return true
            }
        })
    }

    private fun clearSearchResults() {
        searchedItemAdapter.clear()
    }

    private fun searchMarios(query: String) {
        lifecycleScope.launch {
            binding.recyclerviewAllMarios.visibility = View.GONE
            binding.searchedItemRecyclerView.visibility = View.GONE
            binding.progressBarView.visibility = View.VISIBLE
            try {
                val response = RetrofitInstance.createMarioApi().getItemBySearch(query)
                if (response.isSuccessful) {
                    response.body()?.marios?.let { filteredList ->
                        searchedItemAdapter.updateMariosList(filteredList)
                        showSearchedItemRecView()
                    }
                } else {
                    Toast.makeText(
                        this@MarioActivity,
                        "Failed to load search results",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@MarioActivity, "${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBarView.visibility = View.GONE
            }
        }
    }

    private fun prepareSearchRcView() {
        binding.searchedItemRecyclerView.apply {
            adapter = searchedItemAdapter
            layoutManager = GridLayoutManager(this@MarioActivity, 2)
        }
    }

    private fun showSearchedItemRecView() {
        with(binding) {
            tvAllMarios.visibility = View.GONE
            tvBlackLine.visibility = View.GONE
            recyclerviewAllMarios.visibility = View.GONE
            searchedItemRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun showMainViews() {
        with(binding) {
            tvAllMarios.visibility = View.VISIBLE
            tvBlackLine.visibility = View.VISIBLE
            recyclerviewAllMarios.visibility = View.VISIBLE
            searchedItemRecyclerView.visibility = View.GONE
        }
    }

    private fun progressBarVisibility(getData: Boolean) {
        if (getData) {
            binding.progressBarView.visibility = View.INVISIBLE
        } else
            binding.progressBarView.visibility = View.VISIBLE
    }

}