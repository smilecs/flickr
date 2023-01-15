package com.smile.flickr.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import coil.request.ImageRequest
import com.smile.flickr.App
import com.smile.flickr.databinding.FragmentViewBinding
import kotlin.properties.Delegates


private const val ARG_PARAM = "param"
private const val ARG_AUTHOR = "author"
const val VIEW_MORE = "view_more"

class ViewFragment : Fragment() {

    private var param1: String? = null
    private var author: String? = null

    private var binding: FragmentViewBinding by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        arguments?.let {
            param1 = it.getString(ARG_PARAM)
            author = it.getString(ARG_AUTHOR)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageLoader = (requireActivity().application as App).imageLoader
        val request = ImageRequest.Builder(requireContext())
            .data(param1)
            .crossfade(true)
            .target(binding.flickrImage)
            .build()
        imageLoader.enqueue(request)

        binding.author.text = author
    }


    companion object {

        fun newInstance(url: String, author: String) =
            ViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM, url)
                    putString(ARG_AUTHOR, author)
                }
            }
    }
}