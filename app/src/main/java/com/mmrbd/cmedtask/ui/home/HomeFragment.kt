package com.mmrbd.cmedtask.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmrbd.cmedtask.R
import com.mmrbd.cmedtask.databinding.FragmentHomeBinding
import com.mmrbd.cmedtask.ui.detais.CharacterDetailsFragment.Companion.DETAILS_DATA
import com.mmrbd.cmedtask.ui.home.adapter.CharacterAdapter
import com.mmrbd.cmedtask.utils.network.NetworkFailureMessage
import com.mmrbd.cmedtask.utils.network.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var networkFailureMessage: NetworkFailureMessage

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var adapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        adapter = CharacterAdapter {

            val  bundle = Bundle()
            bundle.putParcelable(DETAILS_DATA, it)

            findNavController().navigate(R.id.characterDetailsFragment, bundle)
        }
        binding.rcvCharacter.layoutManager = LinearLayoutManager(context)
        binding.rcvCharacter.adapter = adapter

        lifecycleScope.launch {
            viewModel.characterDataShareFlow.collect {
                when (it) {
                    is Result.Error -> {
                        binding.progressLoading.isVisible = false
                        binding.rcvCharacter.isVisible = false

                        Toast.makeText(
                            context,
                            networkFailureMessage.handleFailure(it.throwable),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is Result.Loading -> {
                        binding.progressLoading.isVisible = true
                        binding.rcvCharacter.isVisible = false
                    }

                    is Result.Success -> {
                        binding.progressLoading.isVisible = false
                        binding.rcvCharacter.isVisible = true

                        it.data?.let { it1 -> adapter.submitCharacterData(it1) }
                    }
                }
            }
        }
    }

}