package com.example.quizapp

import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.quizapp.databinding.FragmentCheatBinding
import com.example.quizapp.databinding.FragmentGameWonBinding
import kotlinx.coroutines.NonCancellable.start
import androidx.fragment.app.viewModels
import androidx.fragment.app.activityViewModels

class GameWonFragment : Fragment() {

    private var _binding: FragmentGameWonBinding? = null
    private val binding get() = _binding!!

    lateinit var music: MediaPlayer

    private val viewModel: QuizViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameWonBinding.inflate(inflater, container, false)
        val rootView = binding.root
        val args = GameWonFragmentArgs.fromBundle(requireArguments())
        binding.howManyIncorrect.text = "You had " + args.wrongArg + " wrong answers!"
        setHasOptionsMenu(true)
        music = MediaPlayer.create(context, R.raw.win)
        music.isLooping = true
        music.start()

        binding.pause.setOnClickListener {
            music.pause()
            binding.pause.isVisible = false
            binding.play.isVisible = true
        }

        binding.play.setOnClickListener {
            music.start()
            binding.pause.isVisible = true
            binding.play.isVisible = false
        }

        binding.forward.setOnClickListener {
            val curr = music.currentPosition
            music.seekTo(curr + 10000)
        }

        binding.rewind.setOnClickListener {
            val curr = music.currentPosition
            music.seekTo(curr - 10000)
        }
        return rootView
    }

    override fun onStop() {
        super.onStop()
        music.release()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.
        onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}