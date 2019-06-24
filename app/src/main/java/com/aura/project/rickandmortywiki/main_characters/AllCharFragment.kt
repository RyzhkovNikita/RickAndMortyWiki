package com.aura.project.rickandmortywiki.main_characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.constraintlayout.solver.widgets.ConstraintHorizontalLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.aura.project.rickandmortywiki.R
import com.aura.project.rickandmortywiki.data.Character
import kotlinx.coroutines.ExperimentalCoroutinesApi


class AllCharFragment : Fragment(), CharacterAdapter.OnCharClickListener, CharacterAdapter.CharacterLoader {

    companion object {
        fun newInstance() = AllCharFragment()
    }

    private lateinit var _viewModel: AllCharViewModel
    private lateinit var _progressBar: ProgressBar
    private lateinit var _recyclerView: RecyclerView
    private lateinit var _adapter: CharacterAdapter
    private lateinit var _errorBlock: ConstraintLayout

    private val _pagedListObserver = Observer<List<Character>> { newList ->
        _adapter.charList = newList
    }
    private val _progressBarObserver = Observer<Boolean> { inProgress ->
        _progressBar.visibility = if (inProgress) View.VISIBLE else View.GONE
    }
    private val _showErrorObserver = Observer<Boolean> {shouldShowError ->
        _errorBlock.visibility = if (shouldShowError) View.VISIBLE else View.GONE
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.all_char_fragment, container, false)
        _progressBar = view.findViewById(R.id.all_char_progress)
        _errorBlock = view.findViewById(R.id.all_char_error)
        _errorBlock.findViewById<Button>(R.id.error_button).setOnClickListener { _viewModel.tryButtonClicked()}
        _adapter = CharacterAdapter(this)
        _recyclerView = view.findViewById(R.id.char_recycler)
        _recyclerView.adapter = _adapter
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        _viewModel = ViewModelProviders.of(this).get(AllCharViewModel::class.java)
        _viewModel.charList.observe(this, _pagedListObserver)
        _viewModel.inProgress.observe(this, _progressBarObserver)
        _viewModel.showingError.observe(this, _showErrorObserver)
    }

    override fun onCharClicked(character: Character) {
        TODO("navigation to charDetail fragment")
    }

    override fun endReached() {
        _viewModel.listEndReached()
    }


    @ExperimentalCoroutinesApi
    override fun onDestroy() {
        _adapter.onDestroy()
        super.onDestroy()
    }

}
