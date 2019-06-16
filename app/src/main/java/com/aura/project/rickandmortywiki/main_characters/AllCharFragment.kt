package com.aura.project.rickandmortywiki.main_characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.aura.project.rickandmortywiki.CharDiffCallback
import com.aura.project.rickandmortywiki.R
import com.aura.project.rickandmortywiki.data.Character

class AllCharFragment : Fragment() {

    companion object {
        fun newInstance() = AllCharFragment()
    }

    private lateinit var viewModel: AllCharViewModel

    private val pagedListObserver =
        Observer<List<Character>> { newList -> _adapter.charList = newList }

    private lateinit var _adapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _adapter = CharacterAdapter(this.context!!)
        return inflater.inflate(R.layout.all_char_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AllCharViewModel::class.java)
        viewModel.charList.observe(this, pagedListObserver)
    }

}
