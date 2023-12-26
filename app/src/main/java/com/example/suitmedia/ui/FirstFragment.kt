package com.example.suitmedia.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.suitmedia.MainViewModel
import com.example.suitmedia.R
import com.example.suitmedia.databinding.FragmentFirstBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstFragment : Fragment(R.layout.fragment_first) {

    private val viewModel by viewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFirstBinding.bind(view)
        setupAction(binding)
    }

    private fun setupAction(binding: FragmentFirstBinding) {
        binding.btnCheck.setOnClickListener {
            val palindromeInput = binding.etPalindrome.text.toString()
            when {
                palindromeInput.isEmpty() -> showAlertDialog(palindromeInput, "Please fill all the fields")
                palindromeInput.toIntOrNull() != null -> binding.etPalindrome.error = "Please don't input a number"
                else -> {
                    val isPalindrome = checkPalindrome(palindromeInput)
                    showAlertDialog(palindromeInput, if (isPalindrome) "This is a palindrome!" else "This is not a palindrome!")
                    clearInput(binding)
                }
            }
        }

        binding.btnNext.setOnClickListener {
            val nameInput = binding.etName.text.toString()
            if (nameInput.isEmpty()) {
                showAlertDialog(nameInput, "Please fill all the fields")
            } else {
                viewModel.setNameUserLogin(nameInput)
                val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun showAlertDialog(input: String, message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Palindrome Checker '$input'")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun checkPalindrome(inputPalindrome: String): Boolean {
        val input = inputPalindrome.replace("\\s".toRegex(), "")
        val len = input.length
        val mid = len / 2
        for (i in 0 until mid) {
            if (input[i] != input[len - 1 - i]) {
                return false
            }
        }
        return true
    }

    private fun clearInput(binding: FragmentFirstBinding) {
        binding.etPalindrome.text?.clear()
    }
}