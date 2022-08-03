package com.stebitto.agify.impl.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.stebitto.agify.R
import com.stebitto.agify.databinding.FragmentAgifyBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class AgifyFragment : Fragment() {

    private val agifyViewModel: AgifyViewModel by viewModels()

    private var _binding: FragmentAgifyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgifyBinding.inflate(inflater, container, false)

        subscribeUI()
        setupListeners()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { agifyViewModel.state.collect { renderUI(it) } }
            }
        }
    }

    private fun setupListeners() {
        binding.buttonSubmit.setOnClickListener {
            agifyViewModel.getPredictionForName(binding.edittextName.text.toString())
        }
    }

    private fun renderUI(state: AgifyViewModel.State) {
        when(state) {
            is AgifyViewModel.State.Init -> Unit
            is AgifyViewModel.State.Loading -> {  }
            is AgifyViewModel.State.Success -> {
                binding.textviewResult.text =
                    String.format(getString(R.string.agify_predict_result), state.name, state.age)
            }
            is AgifyViewModel.State.Error -> {  }
        }
    }
}