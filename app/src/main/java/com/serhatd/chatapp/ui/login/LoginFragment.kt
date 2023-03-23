package com.serhatd.chatapp.ui.login

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
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
        (requireActivity() as MainActivity).binding.subTitle = null

        binding.viewModel = viewModel
        initObservers()

        hideMenu()
        return binding.root
    }

    private fun initObservers() {
        viewModel.loginObserver.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    findNavController().navigate(R.id.loginToChat)
                }
            }
        }
    }

    private fun hideMenu() {
        requireActivity().addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {}

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}