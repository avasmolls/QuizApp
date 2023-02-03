package com.example.quizapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.quizapp.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    // DATA CLASS -> used to store questions

    data class Question(val resource: Int, val answer: Boolean) {
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    // PROPERTIES

    var currentQuestionNumber = 0
    var questionsCorrect = 0
    var questionsWrong = 0
    val questions: List<Question> = listOf(
        Question(R.string.question1, true),
        Question(R.string.question2, false),
        Question(R.string.question3, true),
        Question(R.string.question4, true),
        Question(R.string.question5, false)
    )


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

        binding.cheatButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToCheatFragment(questions[currentQuestionNumber].answer)
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
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

// FUNCTION(S)

    // checkAnswer() -> called when user answers
    fun checkAnswer(usersAnswer: Boolean) {
        /* TODO: Uses the Question data class to determine if the user's answer is correct
        TODO: Displays a toast letting the user know if their answer was correct or incorrect every time they answer */
        val current = questions[this.currentQuestionNumber]
        val correctAnswer = current.answer
        if (correctAnswer == usersAnswer) {
            Toast.makeText(activity, R.string.toast_right, Toast.LENGTH_SHORT).show()
            questionsCorrect++
        } else {
            Toast.makeText(activity, R.string.toast_wrong, Toast.LENGTH_SHORT).show()
            questionsWrong++
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


