package com.aura.project.rickandmortywiki

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedList
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.all_char_fragment.*

class AllCharFragment : Fragment() {

    companion object {
        fun newInstance() = AllCharFragment()
    }

    private lateinit var viewModel: AllCharViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.all_char_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AllCharViewModel::class.java)
        val config = PagedList.Config.Builder().setPageSize(20).setEnablePlaceholders(false).build()
        PagedList.Builder<>(,config)
        TODO("create datasource")
    }

}
