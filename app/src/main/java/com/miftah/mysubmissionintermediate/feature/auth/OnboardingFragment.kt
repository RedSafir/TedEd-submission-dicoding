package com.miftah.mysubmissionintermediate.feature.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.miftah.mysubmissionintermediate.R
import com.miftah.mysubmissionintermediate.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playAnimation()

        binding.btnLogin.setOnClickListener {
            it.findNavController().navigate(R.id.action_onboardingFragment_to_loginFragment)
        }

        binding.btnSignup.setOnClickListener {
            it.findNavController().navigate(R.id.action_onboardingFragment_to_signupFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun playAnimation() {
        val imageAnim = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1F).setDuration(500)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1F).setDuration(500)
        val btnSign = ObjectAnimator.ofFloat(binding.btnSignup, View.ALPHA, 1F).setDuration(500)
        val quote = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1F).setDuration(500)

        val btnAct = AnimatorSet().apply {
            playTogether(btnSign, btnLogin)
        }

        AnimatorSet().apply {
            playSequentially(imageAnim, quote, btnAct)
            start()
        }
    }
}