package com.mmrbd.cmedtask.ui.detais

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.ui.text.capitalize
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import com.mmrbd.cmedtask.R
import com.mmrbd.cmedtask.data.model.CharacterModelItem
import com.mmrbd.cmedtask.databinding.FragmentCharacterDetailsBinding
import java.util.Locale

class CharacterDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCharacterDetailsBinding

    //
//    private val args: CharacterDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val detailsData =
            this.arguments?.getParcelable(DETAILS_DATA, CharacterModelItem::class.java)


        with(binding) {
            imgCharacter.load(
                detailsData?.image
            ) {
                transformations(CircleCropTransformation())
                crossfade(true)
                placeholder(R.drawable.no_image)
                error(R.drawable.no_image)
            }

            tvName.text = detailsData?.name
            tvActorName.text = detailsData?.actor
            tvHouse.text = detailsData?.house
            tvStatus.text =
                if (detailsData?.alive == true) getString(R.string.alive) else getString(R.string.died)
            tvSpecies.text = detailsData?.species?.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.ROOT
                ) else it.toString()
            }
            tvGender.text = detailsData?.gender?.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.ROOT
                ) else it.toString()
            }
        }

    }

    companion object {
        const val DETAILS_DATA = "details_data"
    }
}