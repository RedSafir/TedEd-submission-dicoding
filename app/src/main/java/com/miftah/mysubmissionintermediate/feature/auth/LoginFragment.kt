package com.miftah.mysubmissionintermediate.feature.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.miftah.mysubmissionintermediate.R
import com.miftah.mysubmissionintermediate.core.data.Result
import com.miftah.mysubmissionintermediate.core.ui.ViewModelFactory
import com.miftah.mysubmissionintermediate.databinding.FragmentLoginBinding
import com.miftah.mysubmissionintermediate.feature.main.MainActivity

class LoginFragment : Fragment(), TextWatcher {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WelcomeViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        check()
        playAnimation()

        binding.edLoginEmail.addTextChangedListener(this)
        binding.edLoginPassword.addTextChangedListener(this)

        binding.btnLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            viewModel.userLogin(email, password).observe(viewLifecycleOwner) {
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
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.inf_intro), Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
                }
            }
        }
    }

    private fun check() {
        val edEmail = binding.edLoginEmail.text.toString()
        val edPassword = binding.edLoginPassword.text.toString()
        val check = (edEmail.isNotEmpty() && edPassword.isNotEmpty())
        binding.btnLogin.isActivated = check
        binding.btnLogin.isEnabled = check
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
        val tvTitleLogin =
            ObjectAnimator.ofFloat(binding.tvTitleLogin, View.ALPHA, 1F).setDuration(500)
        val tvImageTitle =
            ObjectAnimator.ofFloat(binding.tvImgTitle, View.ALPHA, 1F).setDuration(500)
        val imageView = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1F).setDuration(500)
        val tvTitleEmail =
            ObjectAnimator.ofFloat(binding.tvTitleEmail, View.ALPHA, 1F).setDuration(500)
        val edEmailLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1F).setDuration(500)
        val edLoginEmail =
            ObjectAnimator.ofFloat(binding.edLoginEmail, View.ALPHA, 1F).setDuration(500)
        val tvTitlePass =
            ObjectAnimator.ofFloat(binding.tvTitlePassword, View.ALPHA, 1F).setDuration(500)
        val edPassLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1F).setDuration(500)
        val edLoginPass =
            ObjectAnimator.ofFloat(binding.edLoginPassword, View.ALPHA, 1F).setDuration(500)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1F).setDuration(500)

        val emailAct = AnimatorSet().apply {
            playTogether(tvTitleEmail, edEmailLayout, edLoginEmail)
        }

        val passAct = AnimatorSet().apply {
            playTogether(tvTitlePass, edPassLayout, edLoginPass)
        }

        val titleAct = AnimatorSet().apply {
            playTogether(tvTitleLogin, tvImageTitle, imageView)
        }

        AnimatorSet().apply {
            playSequentially(titleAct, emailAct, passAct, btnLogin)
            start()
        }
    }
}