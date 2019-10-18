package com.aura.project.rickandmortywiki.main_characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aura.project.rickandmortywiki.*
import kotlinx.coroutines.ExperimentalCoroutinesApi


class AllCharFragment : Fragment(), CharacterAdapter.OnItemClickListener,
    CharacterAdapter.CharacterLoader {

    companion object {
        fun newInstance() = AllCharFragment()
    }

    private lateinit var viewModel: AllCharViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CharacterAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var toolbarTitle: TextView
    private lateinit var toolbarSearch: SearchView
    private var showError = false
    private val currentListItem
        get() = viewModel.charList.value

    private val _pagedListObserver = Observer<List<ListItem>> { newList ->
        if (showError) {
            adapter.itemList = ErrorDecorator.decorate(newList)
        }
    }

    private val _stateObserver = Observer<State> { state ->
        when (state) {
            is Error -> {
                if (!showError)
                    adapter.itemList = ErrorDecorator.decorate(currentListItem ?: emptyList())
                showError = true
            }
            is Ready -> {
                showError = false
                adapter.itemList = currentListItem ?: emptyList()
            }
            is Loading -> {
                showError = false
                adapter.itemList = LoadingDecorator.decorate(currentListItem ?: emptyList())
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.all_char_fragment, container, false)
        adapter = CharacterAdapter(this)
        recyclerView = view.findViewById(R.id.char_recycler)
        val layoutManager = recyclerView.layoutManager as GridLayoutManager
        val spanCount = layoutManager.spanCount
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (adapter.itemList[position] !is CharToShowItem) return spanCount
                return 1
            }
        }
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
        toolbar = (activity as MainActivity).toolbar
        toolbarTitle = (activity as MainActivity).toolbarTitle
        toolbarSearch = (activity as MainActivity).toolbarSearch
        viewModel = ViewModelProvider(this).get(AllCharViewModel::class.java)
        viewModel.apply {
            charList.observe(viewLifecycleOwner, _pagedListObserver)
            state.observe(viewLifecycleOwner, _stateObserver)
        }
    }

    override fun onResume() {
        super.onResume()
        toolbarSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.textChange(newText)
                return true
            }
        })
        toolbarSearch.setOnSearchClickListener {
            toolbarTitle.visibility = View.GONE
            viewModel.searchOpen()
        }
        toolbarSearch.setOnCloseListener {
            toolbarTitle.visibility = View.VISIBLE
            viewModel.searchClose()
            return@setOnCloseListener false
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
    override fun onDestroyView() {
        if (::adapter.isInitialized)
            adapter.onDestroy()
        (recyclerView.layoutManager as GridLayoutManager).spanSizeLookup = null
        recyclerView.clearOnScrollListeners()
        toolbarSearch.setOnQueryTextListener(null)
        toolbarSearch.setOnQueryTextListener(null)
        super.onDestroyView()
    }

}
