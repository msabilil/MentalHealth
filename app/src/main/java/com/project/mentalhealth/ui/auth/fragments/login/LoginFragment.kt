package com.project.mentalhealth.ui.auth.fragments.login

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.project.mentalhealth.utils.Result
import androidx.fragment.app.Fragment
import com.project.mentalhealth.R
import com.project.mentalhealth.databinding.FragmentLoginBinding
import com.project.mentalhealth.ui.auth.fragments.register.RegisterFragment
import com.project.mentalhealth.ui.main.MainActivity
import com.project.mentalhealth.utils.Event
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModel()

    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        observeToken()
        observeViewModel()

        setListeners()

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.apply {
            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

            toastMessage.observe(viewLifecycleOwner) { event ->
                event.getContentIfNotHandled()?.let { message ->
                    showToast(message)
                }
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            btnLogin.setOnClickListener {
                if (isValid()) {
                    viewModel.login(edEmail.text.toString(), edPassword.text.toString())
                        .observe(viewLifecycleOwner) { result ->
                            when (result) {
                                is Result.Loading -> {
                                    viewModel.isLoading.postValue(true)
                                }

                                is Result.Success -> {
                                    viewModel.isLoading.postValue(false)
                                    observeToken()
                                }

                                is Result.Error -> {
                                    viewModel.isLoading.postValue(false)
                                    viewModel.toastMessage.postValue(Event(result.error))
                                }
                            }
                        }
                }
            }

            btnRegister.setOnClickListener {
                parentFragmentManager.beginTransaction().apply {
                    replace(
                        R.id.authentication_container,
                        RegisterFragment(),
                        RegisterFragment::class.java.simpleName
                    )
                    commit()
                }
            }
        }
    }

    private fun observeToken() {
        viewModel.getIsLoggedIn().observe(viewLifecycleOwner) {
            if (it) {
                requireActivity().startActivity(Intent(requireActivity(), MainActivity::class.java))
                requireActivity().finishAffinity()
            }
        }
    }

    private fun isValid() = if (binding.edEmail.text.isNullOrEmpty()) {
        showToast("Invalid Username!")
        false
    } else if (!binding.edPassword.error.isNullOrEmpty() || binding.edPassword.text.isNullOrEmpty()) {
        showToast("Invalid Password!")
        false
    } else {
        true
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            btnLogin.isEnabled = !isLoading
            edEmail.isEnabled = !isLoading
            edPassword.isEnabled = !isLoading
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}