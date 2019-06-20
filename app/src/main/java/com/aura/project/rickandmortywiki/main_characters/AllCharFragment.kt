package com.aura.project.rickandmortywiki.main_characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.aura.project.rickandmortywiki.OnCharClickListener
import com.aura.project.rickandmortywiki.R
import com.aura.project.rickandmortywiki.data.Character
import kotlinx.coroutines.ExperimentalCoroutinesApi


class AllCharFragment : Fragment(), OnCharClickListener {

    companion object {
        fun newInstance() = AllCharFragment()
    }

    private lateinit var _viewModel: AllCharViewModel
    private lateinit var _recyclerView: RecyclerView
    private lateinit var _adapter: CharacterAdapter

    private val pagedListObserver =
        Observer<List<Character>> { newList -> _adapter.charList = newList }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.all_char_fragment, container, false)
        _adapter = CharacterAdapter(this)
        _recyclerView = view.findViewById(R.id.char_recycler)
        _recyclerView.adapter = _adapter
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        _viewModel = ViewModelProviders.of(this).get(AllCharViewModel::class.java)
        _viewModel.charList.observe(this, pagedListObserver)
    }

    override fun OnCharClicked(character: Character) {

    }

    @ExperimentalCoroutinesApi
    override fun onDestroy() {
        _adapter.onDestroy()
        super.onDestroy()
    }

}
