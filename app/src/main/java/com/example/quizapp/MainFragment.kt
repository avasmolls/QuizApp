package com.example.quizapp

import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.quizapp.databinding.FragmentMainBinding
import kotlinx.coroutines.NonCancellable.start
import androidx.fragment.app.viewModels

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

        // SAVE INSTANCE STATE

        if (savedInstanceState != null) {
            currentQuestionNumber = savedInstanceState.getInt(KEY_QUESTION_NUM)
        }

        nextQuestion()

        viewModel.currentQuestionNumber.observe(viewLifecycleOwner) {
            binding.questionView.text = viewModel.currentQuestionText
        }

        binding.cheatButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToCheatFragment(questions[currentQuestionNumber].answer)
            rootView.findNavController().navigate(action)
        }

        setFragmentResultListener("REQUESTING_CHEAT_KEY") { requestKey, bundle: Bundle ->
            questions[currentQuestionNumber].cheated = bundle.getBoolean("CHEAT_KEY")
        }

        // when true button is clicked
        binding.trueButton.setOnClickListener() {
            checkAnswer(true)
            if(questionsCorrect == 3) {
                val action = MainFragmentDirections.actionMainFragmentToGameWonFragment(questionsWrong)
                rootView.findNavController().navigate(action)
            }
        }


        // when false button is clicked
        binding.falseButton.setOnClickListener() {
            checkAnswer(false)
            if(questionsCorrect == 3) {
                val action = MainFragmentDirections.actionMainFragmentToGameWonFragment(questionsWrong)
                rootView.findNavController().navigate(action)
            }
        }

        // when next button is clicked
        binding.nextQuestion.setOnClickListener() {
            if (currentQuestionNumber + 1 > questions.size - 1) {
                currentQuestionNumber = 0
                nextQuestion()
            } else {
                currentQuestionNumber++
                nextQuestion()
            }
        }


        // TODO: EXTRA CREDIT!!

        // when question is clicked, acts as next button
        binding.questionView.setOnClickListener()
        {
            if (currentQuestionNumber + 1 > questions.size - 1) {
                currentQuestionNumber = 0
                nextQuestion()
            } else {
                currentQuestionNumber++
                nextQuestion()
            }
        }


        // when last button is clicked
        binding.backButton.setOnClickListener()
        {
            if (currentQuestionNumber - 1 < 0) {
                currentQuestionNumber = questions.size - 1
                nextQuestion()
            } else {
                currentQuestionNumber--
                nextQuestion()
            }
        }
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
        val current = questions[this.currentQuestionNumber]
        val correctAnswer = current.answer
        if (correctAnswer == usersAnswer) {
            music = MediaPlayer.create(context, R.raw.right)
            music.start()
            if(questions[currentQuestionNumber].cheated) {
                Toast.makeText(activity, R.string.toast_cheat, Toast.LENGTH_SHORT).show()
            }
            else {
                questionsCorrect+=1
                Toast.makeText(activity, R.string.toast_right, Toast.LENGTH_SHORT).show()

            }
        } else {
            music = MediaPlayer.create(context, R.raw.wrong)
            music.start()
            questionsWrong+=1
            Toast.makeText(activity, R.string.toast_wrong, Toast.LENGTH_SHORT).show()
        }
    }

    // nextQuestion() -> called to go onto next question
    fun nextQuestion() {
        val next = questions[this.currentQuestionNumber]
        binding.questionView.text = getString(next.resource)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt(KEY_QUESTION_NUM, currentQuestionNumber)
    }


}


