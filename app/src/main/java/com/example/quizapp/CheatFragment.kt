package com.example.quizapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.quizapp.databinding.FragmentCheatBinding
import androidx.fragment.app.viewModels
import androidx.fragment.app.activityViewModels

class CheatFragment : Fragment() {

    private var _binding: FragmentCheatBinding? = null
    private val binding get() = _binding!!

    private val viewModel: QuizViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheatBinding.inflate(inflater, container, false)
        val rootView = binding.root

        val args = CheatFragmentArgs.fromBundle(requireArguments())

        binding.cheatScreenButton.setOnClickListener {
            binding.answer.text = args.answerArg.toString()
            val cheat = true
            setFragmentResult("REQUESTING_CHEAT_KEY", bundleOf("CHEAT_KEY" to cheat))
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

}