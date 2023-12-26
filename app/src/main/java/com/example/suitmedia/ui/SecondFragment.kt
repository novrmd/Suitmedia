package com.example.suitmedia.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.suitmedia.MainViewModel
import com.example.suitmedia.R
import com.example.suitmedia.databinding.FragmentSecondBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSecondBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupAction()
        getUser()
    }

    private fun setupAction() = with(binding) {
        appbar.apply {
            tvFragmentTitle.text = getString(R.string.second_screen)
            btnBack.setOnClickListener { navigateToFirstFragment() }
        }

        btnChooseUser.setOnClickListener { navigateToThirdFragment() }
    }

    private fun navigateToFirstFragment() {
        findNavController().navigate(SecondFragmentDirections.actionSecondFragmentToFirstFragment())
    }

    private fun navigateToThirdFragment() {
        findNavController().navigate(SecondFragmentDirections.actionSecondFragmentToThirdFragment())
    }

    private fun setupView() {
        viewModel.getValueNameLogin().observe(viewLifecycleOwner) {
            binding.tvUserName.text = it
            Log.d("SecondFragment", "setupView: $it")
        }
    }

    private fun getUser() {
        viewModel.getSelectedUser().observe(viewLifecycleOwner) {
            val userName = it.firstName?.let { firstName -> it.lastName?.let { lastName -> "$firstName $lastName" } }
            binding.tvSelectedUser.text = userName ?: "No User Selected"
        }
    }
}