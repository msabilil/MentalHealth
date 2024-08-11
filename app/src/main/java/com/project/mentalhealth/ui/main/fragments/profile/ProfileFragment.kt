package com.project.mentalhealth.ui.main.fragments.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.project.mentalhealth.databinding.FragmentProfileBinding
import com.project.mentalhealth.ui.auth.AuthenticationActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModel()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setListeners()

        return root
    }

    private fun setListeners() {
        binding.apply {
            tvUsername.text = viewModel.getUsername()
            btnLogout.setOnClickListener {
                viewModel.logout()
                requireActivity().apply {
                    startActivity(Intent(this, AuthenticationActivity::class.java))
                    finishAffinity()
                }
            }
        }
    }
}