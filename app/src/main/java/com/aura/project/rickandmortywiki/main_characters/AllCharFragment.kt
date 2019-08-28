package com.aura.project.rickandmortywiki.main_characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aura.project.rickandmortywiki.R
import com.aura.project.rickandmortywiki.Router
import kotlinx.coroutines.ExperimentalCoroutinesApi


class AllCharFragment : Fragment(), CharacterAdapter.OnItemClickListener,
    CharacterAdapter.CharacterLoader {

    companion object {
        fun newInstance() = AllCharFragment()
    }

    private lateinit var viewModel: AllCharViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CharacterAdapter
    private var currentListItem: List<ListItem> = listOf()
    private var showError = false

    private val _pagedListObserver = Observer<List<ListItem>> { newList ->
        currentListItem = newList
        if (showError) {
            currentListItem.toMutableList() + ErrorItem()
        }
        adapter.itemList = currentListItem
    }
    private val _progressBarObserver = Observer<Boolean> { inProgress ->
        //TODO
    }
    private val _showErrorObserver = Observer<Boolean> { shouldShowError ->
        if (shouldShowError && !showError) {
            currentListItem.toMutableList() + ErrorItem()
            adapter.itemList = currentListItem
        } else if (!shouldShowError && showError) {
            currentListItem.toMutableList().remove(ErrorItem())
            adapter.itemList = currentListItem
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.all_char_fragment, container, false)
        adapter = CharacterAdapter(this)
        recyclerView = view.findViewById(R.id.char_recycler)
        recyclerView.addOnScrollListener(object :
            EndlessRecyclerViewScrollListener(recyclerView.layoutManager as GridLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                viewModel.listEndReached()
            }
        })
        recyclerView.adapter = adapter
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AllCharViewModel::class.java)
        viewModel.apply {
            charList.observe(viewLifecycleOwner, _pagedListObserver)
            inProgress.observe(viewLifecycleOwner, _progressBarObserver)
            showingError.observe(viewLifecycleOwner, _showErrorObserver)
        }
    }

    override fun onCharClicked(characterId: Long) {
        (activity as Router).openCharacter(characterId)
    }

    override fun onErrorClick() {
        viewModel.tryButtonClicked()
    }

    override fun endReached() {
        viewModel.listEndReached()
    }

    @ExperimentalCoroutinesApi
    override fun onDestroy() {
        if (::adapter.isInitialized)
            adapter.onDestroy()
        super.onDestroy()
    }

}
