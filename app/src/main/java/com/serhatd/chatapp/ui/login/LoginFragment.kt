package com.serhatd.chatapp.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.serhatd.chatapp.MainActivity
import com.serhatd.chatapp.R
import com.serhatd.chatapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: LoginViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        (requireActivity() as MainActivity).binding.titleSuffix = getString(R.string.title_login)

        binding.viewModel = viewModel
        initObservers()

        return binding.root
    }

    private fun initObservers() {
        viewModel.loginObserver.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    // navigate to chat fragment
                }
            }
        }
    }
}