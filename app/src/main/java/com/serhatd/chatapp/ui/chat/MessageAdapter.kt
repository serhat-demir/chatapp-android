package com.serhatd.chatapp.ui.chat

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.serhatd.chatapp.R
import com.serhatd.chatapp.data.model.Message
import com.serhatd.chatapp.databinding.CardMessageBinding

class MessageAdapter(private val messages: List<Message>, private val currentUserName: String): RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    class MessageViewHolder(val binding: CardMessageBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding: CardMessageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.card_message, parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.binding.message = messages[position]

        if (messages[position].message_sender == currentUserName) {
            holder.binding.cardMessage.setHorizontalGravity(Gravity.END)
        } else {
            holder.binding.cardMessage.setHorizontalGravity(Gravity.START)
        }

        if (position > 0 && messages[position - 1].message_sender == messages[position].message_sender) {
            holder.binding.txtSender.visibility = View.GONE
        } else {
            holder.binding.txtSender.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }
}