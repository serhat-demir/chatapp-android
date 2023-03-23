package com.serhatd.chatapp.ui.chat

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.serhatd.chatapp.MainActivity
import com.serhatd.chatapp.R
import com.serhatd.chatapp.data.prefs.SharedPrefs
import com.serhatd.chatapp.databinding.FragmentChatBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private lateinit var viewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: ChatViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
        (requireActivity() as MainActivity).binding.titleSuffix = null
        (requireActivity() as MainActivity).binding.subTitle = getString(R.string.subtitle_chat, viewModel.getSession()[SharedPrefs.COL_USER_NAME])

        binding.viewModel = viewModel
        initObservers()

        createMenu()
        return binding.root
    }

    private fun initObservers() {
        viewModel.terminateSessionObserver.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    viewModel.endSession()
                    findNavController().popBackStack()
                }
            }
        }

        viewModel.messageObserver.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    binding.txtMessage.setText("")
                }
            }
        }

        viewModel.messages.observe(viewLifecycleOwner) {
            it?.let {
                val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                val adapter = MessageAdapter(it, viewModel.getSession()[SharedPrefs.COL_USER_NAME]!!)

                binding.recyclerView.layoutManager = layoutManager
                binding.recyclerView.adapter = adapter

                layoutManager.scrollToPosition(adapter.itemCount - 1)
            }
        }

        viewModel.getMessages()
    }

    private fun createMenu() {
        requireActivity().addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_chat, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                viewModel.endSession()
                findNavController().popBackStack()
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}