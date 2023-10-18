package com.miftah.mysubmissionintermediate.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.miftah.mysubmissionintermediate.core.data.Result
import com.miftah.mysubmissionintermediate.core.data.source.remote.response.ListStoryItem
import com.miftah.mysubmissionintermediate.core.ui.AdapterCardStories
import com.miftah.mysubmissionintermediate.core.ui.ViewModelFactory
import com.miftah.mysubmissionintermediate.databinding.FragmentListStoryBinding
import com.miftah.mysubmissionintermediate.feature.main.data.MainActivityViewModel

class ListStoryFragment : Fragment() {

    private var _binding: FragmentListStoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AdapterCardStories
    private val viewModel: MainActivityViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRV()

        viewModel.token.observe(viewLifecycleOwner) { token ->
            viewModel.getAllStories(token).observe(viewLifecycleOwner) {
                when (it) {
                    is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            it.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        adapter.submitList(it.data)
                    }
                }
            }

            binding.fabAdd.setOnClickListener {
                val toAddStoryActivity =
                    ListStoryFragmentDirections.actionNavigationListStoryToAddStoryActivity()
                toAddStoryActivity.token = token
                findNavController().navigate(toAddStoryActivity)
            }
        }
    }

    private fun setupRV() {
        val layoutManager = LinearLayoutManager(view?.context)
        adapter = AdapterCardStories()
        binding.rvStories.adapter = adapter
        binding.rvStories.layoutManager = layoutManager
        binding.rvStories.addItemDecoration(
            DividerItemDecoration(view?.context, layoutManager.orientation)
        )
        adapter.setOnClickCallback(object : AdapterCardStories.OnClickListener {
            override fun onClickCard(friendItem: ListStoryItem) {
                val toDetailStoryFragment =
                    ListStoryFragmentDirections.actionNavigationListStoryToDetailStoryFragment()
                toDetailStoryFragment.name = friendItem.name
                toDetailStoryFragment.photoUrl = friendItem.photoUrl
                toDetailStoryFragment.desc = friendItem.description
                findNavController().navigate(toDetailStoryFragment)
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}