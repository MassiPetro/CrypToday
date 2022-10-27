package com.petrogallimassimo.cryptoday.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.petrogallimassimo.cryptoday.R
import com.petrogallimassimo.cryptoday.data.ApiFactory
import com.petrogallimassimo.cryptoday.data.ApiService
import com.petrogallimassimo.cryptoday.data.RepositoryImpl
import com.petrogallimassimo.cryptoday.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val repository = RepositoryImpl(ApiFactory.buildService(ApiService::class.java))

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.ViewModelFactory(repository)
    }
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: CryptosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel.getCryptos()

        setViews()
        setObservers()

        return binding.root
    }

    private fun setViews() {
        adapter = CryptosAdapter(requireContext()) {
            val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(it)
            findNavController().navigate(action)
        }
        binding.rvCryptos.adapter = adapter
        val itemDecoration = MaterialDividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        itemDecoration.dividerColor =
            resources.getColor(R.color.md_theme_light_tertiary, activity?.theme)
        itemDecoration.dividerInsetStart = 15
        itemDecoration.dividerInsetEnd = 15
        binding.rvCryptos.addItemDecoration(itemDecoration)
    }

    private fun setObservers() {
        viewModel.cryptosListLiveData.observe(viewLifecycleOwner) {
            adapter.replaceItems(it)
        }
    }

}