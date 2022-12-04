package com.smile.flickr.ui.main

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.smile.flickr.*
import com.smile.flickr.databinding.FragmentMainBinding
import kotlinx.coroutines.launch
import kotlin.properties.Delegates


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var binding: FragmentMainBinding by Delegates.notNull()

    private var viewModel: MainViewModel by Delegates.notNull()
    private var gridLayoutManager: GridLayoutManager by Delegates.notNull()
    private var flickrAdapter: FlickrAdapter by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    (requireActivity() as MainActivity).setUpButtonAction()
                }
            }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = viewModels<MainViewModel> { MainViewModel.Factory }.value
        gridLayoutManager = GridLayoutManager(requireContext(), LINEAR_VIEW)
        flickrAdapter = FlickrAdapter(
            (requireActivity().application as App).imageLoader,
            ::showFullView
        )
        with(binding.list) {
            layoutManager = gridLayoutManager
            adapter = flickrAdapter
        }
        observeFlickrFlow()
        setListeners()
        setMenu()
    }

    private fun showFullView(url: String) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, ViewFragment.newInstance(url), VIEW_MORE)
            .addToBackStack(null)
            .commit()
    }

    private fun setMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.adapter_control, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.switch_orientation) {
                    switchOrientation()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun switchOrientation() {
        gridLayoutManager.spanCount = if (gridLayoutManager.spanCount == LINEAR_VIEW) GRID_VIEW
        else LINEAR_VIEW
        flickrAdapter.calibrate()
    }

    private fun setListeners() {
        binding.searchTerm.setOnEditorActionListener { textView, action, _ ->
            if (action == EditorInfo.IME_ACTION_GO) {
                val searchTerm = textView.text
                if (searchTerm.isNotBlank()) {
                    val queryParam = searchTerm.replace(Regex("/s"), ",")
                    viewModel.getUIFeed(queryParam)
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun observeFlickrFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flickrUIFlow.collect {
                when (it) {
                    is FlickrUI.Init -> {
                        Snackbar.make(binding.main, "Try a search term", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                    is FlickrUI.UIData -> {
                        if (it.uiList.isEmpty()) {
                            Snackbar.make(
                                binding.main,
                                "Couldn't find results",
                                Snackbar.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            flickrAdapter.update(it.uiList)
                        }
                    }
                    is FlickrUI.Error -> {
                        Snackbar.make(binding.main, "Error getting feed", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}