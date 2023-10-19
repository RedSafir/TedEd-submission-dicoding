package com.miftah.mysubmissionintermediate.feature.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.miftah.mysubmissionintermediate.core.data.Result
import com.miftah.mysubmissionintermediate.core.ui.ViewModelFactory
import com.miftah.mysubmissionintermediate.databinding.FragmentSignupBinding
import com.miftah.mysubmissionintermediate.feature.auth.data.WelcomeViewModel

class SignupFragment : Fragment(), TextWatcher {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WelcomeViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        check()
        playAnimation()

        binding.edLoginEmail.addTextChangedListener(this)
        binding.edLoginPassword.addTextChangedListener(this)
        binding.edRegisterName.addTextChangedListener(this)

        binding.btnSignup.setOnClickListener {
            val username = binding.edRegisterName.text.toString()
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()
            viewModel.userRegis(email, username, password).observe(viewLifecycleOwner) {
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
                        Toast.makeText(requireContext(), "akun berhasil dibuat", Toast.LENGTH_SHORT)
                            .show()
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    private fun check() {
        val edEmail = binding.edLoginEmail.text.toString()
        val edPass = binding.edLoginPassword.text.toString()
        val edName = binding.edRegisterName.text.toString()
        val check = (edEmail.isNotEmpty() && edPass.isNotEmpty() && edName.isNotEmpty())
        binding.btnSignup.isActivated = check
        binding.btnSignup.isEnabled = check
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        check()
    }

    override fun afterTextChanged(s: Editable?) {
    }

    private fun playAnimation() {
        val titleImg = ObjectAnimator.ofFloat(binding.titleImgTedd, View.ALPHA, 1F).setDuration(500)
        val titleSignUp =
            ObjectAnimator.ofFloat(binding.tvTitleRegistration, View.ALPHA, 1F).setDuration(500)
        val imageSignUp = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1F).setDuration(500)
        val titleName =
            ObjectAnimator.ofFloat(binding.tvTitleUsername, View.ALPHA, 1F).setDuration(500)
        val edLayoutName =
            ObjectAnimator.ofFloat(binding.usernameEditTextLayout, View.ALPHA, 1F).setDuration(500)
        val edTextName =
            ObjectAnimator.ofFloat(binding.edRegisterName, View.ALPHA, 1F).setDuration(500)
        val titleMail =
            ObjectAnimator.ofFloat(binding.tvTitleEmail, View.ALPHA, 1F).setDuration(500)
        val edLayoutMail =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1F).setDuration(500)
        val edTextMail =
            ObjectAnimator.ofFloat(binding.edLoginEmail, View.ALPHA, 1F).setDuration(500)
        val titlePass =
            ObjectAnimator.ofFloat(binding.tvTitlePassword, View.ALPHA, 1F).setDuration(500)
        val edLayoutPass =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1F).setDuration(500)
        val edTextPass =
            ObjectAnimator.ofFloat(binding.edLoginPassword, View.ALPHA, 1F).setDuration(500)
        val btnSignup = ObjectAnimator.ofFloat(binding.btnSignup, View.ALPHA, 1F).setDuration(500)

        val header = AnimatorSet().apply {
            playTogether(titleImg, titleSignUp, imageSignUp)
        }

        val name = AnimatorSet().apply {
            playTogether(titleName, edLayoutName, edTextName)
        }

        val mail = AnimatorSet().apply {
            playTogether(titleMail, edLayoutMail, edTextMail)
        }

        val pass = AnimatorSet().apply {
            playTogether(titlePass, edLayoutPass, edTextPass)
        }

        AnimatorSet().apply {
            playSequentially(header, name, mail, pass, btnSignup)
            start()
        }
    }

}