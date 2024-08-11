package com.project.mentalhealth.ui.auth.fragments.register

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.project.mentalhealth.utils.Result
import androidx.fragment.app.Fragment
import com.project.mentalhealth.R
import com.project.mentalhealth.databinding.FragmentRegisterBinding
import com.project.mentalhealth.ui.auth.fragments.login.LoginFragment
import com.project.mentalhealth.utils.Event
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {
    private val viewModel: RegisterViewModel by viewModel()

    private lateinit var binding: FragmentRegisterBinding

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
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

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
            btnRegister.setOnClickListener {
                if (isValid()) {
                    viewModel.register(
                        edEmail.text.toString(),
                        edPassword.text.toString()
                    )
                        .observe(viewLifecycleOwner) { result ->
                            when (result) {
                                is Result.Loading -> {
                                    viewModel.isLoading.postValue(true)
                                }

                                is Result.Success -> {
                                    viewModel.isLoading.postValue(false)
                                    parentFragmentManager.beginTransaction().apply {
                                        replace(
                                            R.id.authentication_container,
                                            LoginFragment(),
                                            LoginFragment::class.java.simpleName
                                        )
                                        commit()
                                    }
                                }

                                is Result.Error -> {
                                    viewModel.isLoading.postValue(false)
                                    viewModel.toastMessage.postValue(Event(result.error))
                                }
                            }
                        }
                }
            }

            btnLogin.setOnClickListener {
                parentFragmentManager.beginTransaction().apply {
                    replace(
                        R.id.authentication_container,
                        LoginFragment(),
                        LoginFragment::class.java.simpleName
                    )
                    commit()
                }
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
            btnRegister.isEnabled = !isLoading
            edEmail.isEnabled = !isLoading
            edPassword.isEnabled = !isLoading
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}