package com.example.suitmedia.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suitmedia.MainViewModel
import com.example.suitmedia.R
import com.example.suitmedia.adapter.ItemListAdapter
import com.example.suitmedia.adapter.LoadingStateAdapter
import com.example.suitmedia.data.Result
import com.example.suitmedia.databinding.FragmentThirdBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ThirdFragment : Fragment() {
    private lateinit var binding: FragmentThirdBinding
    private val viewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var adapter: ItemListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentThirdBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupRv()
        lifecycleScope.launchWhenCreated {
            loadData()
        }
    }

    private fun loadData() {
        viewModel.users.observe(viewLifecycleOwner) { result ->
            binding.progressBar.visibility = when (result) {
                is Result.Loading -> View.VISIBLE
                is Result.Success -> {
                    adapter.submitData(lifecycle, result.data)
                    View.INVISIBLE
                }
                is Result.Error -> {
                    binding.tvIsEmpty.visibility = View.VISIBLE
                    View.INVISIBLE
                }
            }
        }

        adapter.onItemClick = {
            viewModel.setSelectedUser(it)
            val action = ThirdFragmentDirections.actionThirdFragmentToSecondFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupRv() {
        binding.rvUser.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter{
                adapter.retry()
            }
        )
        binding.rvUser.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun setupView() {
        with(binding.appbar) {
            tvFragmentTitle.text = getString(R.string.third_screen)
            btnBack.setOnClickListener { findNavController().navigateUp() }
        }
    }

}