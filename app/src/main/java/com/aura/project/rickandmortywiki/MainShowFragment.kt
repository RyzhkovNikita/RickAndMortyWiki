package com.aura.project.rickandmortywiki

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.aura.project.rickandmortywiki.main_characters.AllCharFragment


class MainShowFragment : Fragment() {

    companion object {
        fun newInstance() = MainShowFragment()
    }

    private lateinit var viewModel: MainShowViewModel
    private val fragmentObserver = Observer<Int> { fragmentId ->
        when (fragmentId) {
            MainShowViewModel.ALL_CHAR_FRAGMENT -> replace(AllCharFragment.newInstance())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.main_show_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainShowViewModel::class.java)
        viewModel.fragment.observe(this, fragmentObserver)
    }

    private fun replace(fragment: Fragment) = fragmentManager
        ?.beginTransaction()
        ?.replace(R.id.fragment_container, fragment)
        ?.commit()

}
