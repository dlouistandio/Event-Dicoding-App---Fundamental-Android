package com.example.eventdicoding.ui.upcomingEvent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventdicoding.databinding.FragmentUpcomingBinding
import com.example.eventdicoding.ui.ViewModelFactory
import com.example.eventdicoding.ui.adapter.ListEventAdapter
import com.example.eventdicoding.data.local.entity.EventEntity
import com.example.eventdicoding.data.Result
import com.example.eventdicoding.data.remote.response.ListEventsItem
import com.example.eventdicoding.ui.adapter.FavoriteEventAdapter

class UpcomingEventFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!

    private val factory: ViewModelFactory by lazy { ViewModelFactory.getInstance(requireActivity()) }
    private val viewModel: UpcomingEventViewModel by viewModels { factory }

    private lateinit var upcomingEventAdapter: ListEventAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        upcomingEventAdapter = ListEventAdapter()

        binding.rvUpcoming.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = upcomingEventAdapter
        }

        viewModel.listEvent.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    setUpcomingEventData(result.data)
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        context,
                        "Terjadi kesalahan: ${result.error}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                null -> binding.progressBar.visibility = View.GONE
            }
        }
        viewModel.fetchUpcomingEvents()
    }

    private fun setUpcomingEventData(eventList: List<ListEventsItem>) {
        upcomingEventAdapter.submitList(eventList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}