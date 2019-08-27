package com.aura.project.rickandmortywiki.details_location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.aura.project.rickandmortywiki.R
import com.aura.project.rickandmortywiki.Router
import com.aura.project.rickandmortywiki.main_characters.CharacterAdapter

class LocationFragment : Fragment(), CharacterAdapter.CharacterLoader, CharacterAdapter.OnCharClickListener {

    companion object {
        const val LOCATION_ID_KEY = "locationIdKey"
        fun newInstance(id: Long) = LocationFragment().apply {
            arguments = bundleOf(LOCATION_ID_KEY to id)
        }
    }

    private lateinit var _viewModel: LocationViewModel
    private lateinit var _name: TextView
    private lateinit var _type: TextView
    private lateinit var _dimension: TextView
    private lateinit var _adapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.location_fragment, container, false)
        view.apply {
            _name = findViewById(R.id.name)
            _type = findViewById(R.id.type)
            _dimension = findViewById(R.id.dimension)
        }
        _adapter = CharacterAdapter(this)
        view.findViewById<RecyclerView>(R.id.location_recycler).adapter = _adapter
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val id = arguments!!.getLong(LOCATION_ID_KEY)
        val factory = LocationViewModel.Factory(id, activity!!.application)
        _viewModel = ViewModelProviders.of(this, factory).get(LocationViewModel::class.java)
        _viewModel.apply {

            location.observe(viewLifecycleOwner, Observer { location ->
                _name.text = location.name
                _type.text = location.type
                _dimension.text = location.dimension
            })

            chars.observe(viewLifecycleOwner, Observer { _adapter.itemList = it })
        }
    }

    override fun onCharClicked(characterId: Long) {
        (activity as Router).openCharacter(characterId)
    }

    override fun onErrorClick() {

    }

    override fun endReached() {
        //ignored
    }
}
