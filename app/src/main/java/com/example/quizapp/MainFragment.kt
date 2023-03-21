package com.example.quizapp

import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.quizapp.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    lateinit var music: MediaPlayer

    private val viewModel: QuizViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val rootView = binding.root

        viewModel.currentQuestionNumber.observe(viewLifecycleOwner) {
            binding.questionView.text = getString(viewModel.currentQuestionText)
        }

        viewModel.gameWon.observe(viewLifecycleOwner) {
            if(viewModel.checkIfGameWon()) {
                val action = MainFragmentDirections.actionMainFragmentToGameWonFragment()
                rootView.findNavController().navigate(action)
            }
        }

        binding.cheatButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToCheatFragment()
            rootView.findNavController().navigate(action)
        }

        // when true button is clicked
        binding.trueButton.setOnClickListener() {
            checkAnswer(true)
        }

        // when false button is clicked
        binding.falseButton.setOnClickListener() {
            checkAnswer(false)
        }

        binding.nextQuestion.setOnClickListener() {
            viewModel.nextQuestion()
        }


        // TODO: EXTRA CREDIT!!

        // when question is clicked, acts as next button
       /* binding.questionView.setOnClickListener()
        {
            if (currentQuestionNumber + 1 > questions.size - 1) {
                currentQuestionNumber = 0
                nextQuestion()
            } else {
                currentQuestionNumber++
                nextQuestion()
            }
        } */


        // when last button is clicked
        /*binding.backButton.setOnClickListener()
        {
            if (currentQuestionNumber - 1 < 0) {
                currentQuestionNumber = questions.size - 1
                nextQuestion()
            } else {
                currentQuestionNumber--
                nextQuestion()
            }
        } */
        setHasOptionsMenu(true)
        return rootView
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

// FUNCTION(S)

    // checkAnswer() -> called when user answers
    fun checkAnswer(usersAnswer: Boolean) {
        if (viewModel.checkAnswer(usersAnswer)) {
            music = MediaPlayer.create(context, R.raw.right)
            music.start()
            if(viewModel.currentQuestionCheatStatus) {
                Toast.makeText(activity, R.string.toast_cheat, Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(activity, R.string.toast_right, Toast.LENGTH_SHORT).show()

            }
        } else {
            music = MediaPlayer.create(context, R.raw.wrong)
            music.start()
            Toast.makeText(activity, R.string.toast_wrong, Toast.LENGTH_SHORT).show()
        }
    }



}


