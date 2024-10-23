package com.example.eventdicoding.ui.pastEvent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventdicoding.databinding.FragmentPastBinding
import com.example.eventdicoding.ui.ViewModelFactory
import com.example.eventdicoding.ui.adapter.ListEventAdapter
import com.example.eventdicoding.data.Result
import com.example.eventdicoding.data.remote.response.ListEventsItem

class PastEventFragment : Fragment() {

    private var _binding: FragmentPastBinding? = null
    private val binding get() = _binding!!

    private val factory: ViewModelFactory by lazy { ViewModelFactory.getInstance(requireActivity()) }
    private val viewModel: PastEventViewModel by viewModels { factory }

    private lateinit var pastEventAdapter: ListEventAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pastEventAdapter = ListEventAdapter()

        binding.rvPast.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = pastEventAdapter
        }

        viewModel.listEvent.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    setPastEventData(result.data)
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
        viewModel.fetchPastEvents()
    }

    private fun setPastEventData(eventList: List<ListEventsItem>) {
        pastEventAdapter.submitList(eventList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}