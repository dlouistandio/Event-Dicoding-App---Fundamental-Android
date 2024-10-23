package com.example.eventdicoding.ui.favoriteEvent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventdicoding.databinding.FragmentFavoriteBinding
import com.example.eventdicoding.ui.ViewModelFactory
import com.example.eventdicoding.ui.adapter.FavoriteEventAdapter
class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val factory: ViewModelFactory by lazy { ViewModelFactory.getInstance(requireActivity()) }
    private val viewModel: FavoriteEventViewModel by viewModels { factory }

    private lateinit var favoriteEventAdapter: FavoriteEventAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteEventAdapter = FavoriteEventAdapter()

        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = favoriteEventAdapter
        }

        viewModel.favoriteEvent.observe(viewLifecycleOwner) { favoriteEvent ->
            binding.progressBar.visibility = View.GONE
            favoriteEventAdapter.submitList(favoriteEvent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}