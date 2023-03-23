package com.serhatd.chatapp.ui.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
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
        (requireActivity() as MainActivity).binding.subTitle = getString(R.string.subtitle_chat, viewModel.getSession()[SharedPrefs.COL_USER_NAME])

        binding.viewModel = viewModel
        initObservers()

        return binding.root
    }

    private fun initObservers() {
        viewModel.messages.observe(viewLifecycleOwner) {
            it?.let {
                // init rv adapter
            }
        }
    }
}