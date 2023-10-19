package com.miftah.mysubmissionintermediate.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.miftah.mysubmissionintermediate.core.ui.ViewModelFactory
import com.miftah.mysubmissionintermediate.databinding.FragmentDetailStoryBinding
import com.miftah.mysubmissionintermediate.feature.main.data.MainActivityViewModel

class DetailStoryFragment : Fragment() {

    private var _binding: FragmentDetailStoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainActivityViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = DetailStoryFragmentArgs.fromBundle(arguments as Bundle).name
        val photoUrl = DetailStoryFragmentArgs.fromBundle(arguments as Bundle).photoUrl
        val description = DetailStoryFragmentArgs.fromBundle(arguments as Bundle).desc

        binding.tvDetailName.text = name
        Glide.with(binding.root)
            .load(photoUrl)
            .into(binding.ivDetailPhoto)
        binding.tvDetailDescription.text = description
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}