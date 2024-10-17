package com.example.listadetareas.fragments.web.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.listadetareas.R
import com.example.listadetareas.databinding.FragmentMovieDetailsBinding
import com.example.listadetareas.utils.Contants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private val args by navArgs<MovieDetailsFragmentArgs>()

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = args.movieItem.title
        (requireActivity() as AppCompatActivity).supportActionBar?.subtitle = args.movieItem.release_date
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(requireContext())
            .load("${Contants.BASE_URL_IMAGES}${args.movieItem.poster_path}")
            .dontAnimate()
            .error(R.drawable.baseline_image_24)
            .centerCrop()
            .into(binding.imageViewSrc)

        binding.ratingBarRatingVoteAverage.rating = (args.movieItem.vote_average?.toFloat() ?: 0.0) as Float

        binding.materialTextViewOriginalTitle.text = "${args.movieItem.original_title}"

        binding.materialTextViewDate.text = "${args.movieItem.release_date}"

        binding.materialTextViewOverView.text = "${args.movieItem.overview}"

    }

}