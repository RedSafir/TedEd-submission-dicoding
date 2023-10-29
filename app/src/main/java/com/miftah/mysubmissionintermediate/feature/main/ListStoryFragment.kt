package com.miftah.mysubmissionintermediate.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.miftah.mysubmissionintermediate.core.data.source.local.entity.Stories
import com.miftah.mysubmissionintermediate.core.ui.AdapterCardStories
import com.miftah.mysubmissionintermediate.core.ui.LoadingStateAdapter
import com.miftah.mysubmissionintermediate.core.ui.ViewModelFactory
import com.miftah.mysubmissionintermediate.databinding.FragmentListStoryBinding

class ListStoryFragment : Fragment() {

    private var _binding: FragmentListStoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AdapterCardStories
    private val viewModel: MainViewModel by activityViewModels {
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
                adapter.submitData(lifecycle, it)
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
        binding.rvStories.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        binding.rvStories.layoutManager = layoutManager
        binding.rvStories.addItemDecoration(
            DividerItemDecoration(view?.context, layoutManager.orientation)
        )
        adapter.setOnClickCallback(object : AdapterCardStories.OnClickListener {
            override fun onClickCard(friendItem: Stories) {
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