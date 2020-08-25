package com.example.contactsmadarsoft.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.example.contactsmadarsoft.databinding.ContactRwoElementBinding
import com.example.contactsmadarsoft.models.ContactsModel


class ContactListAdapter(val contactClickListener: ContactListItemListener) :
    ListAdapter<ContactsModel, ContactListAdapter.ContactListViewHolder>(ContactListDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListViewHolder {
        return ContactListViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: ContactListViewHolder, position: Int) {
        val contact = getItem(position)
        holder.bind(contact, contactClickListener)
    }


    class ContactListViewHolder private constructor(val binding: ContactRwoElementBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var generator = ColorGenerator.MATERIAL

        fun bind(contact: ContactsModel, contactElementListener: ContactListItemListener) {
            binding.contactElement = contact
            binding.clickListener = contactElementListener
            binding.userNameRowElement.text = contact.contactName
            binding.userAgeRowElement.text = contact.contactAge.toString()


            val letter = contact.contactName[0]
            val textDrawable = TextDrawable.builder()
                .buildRound(letter.toString(), generator.randomColor)
            binding.userImgRowElement.setImageDrawable(textDrawable)

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ContactListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ContactRwoElementBinding.inflate(layoutInflater, parent, false)
                return ContactListViewHolder(binding)

            }
        }

    }

}

class ContactListItemListener(val contactClickListener: (contact: ContactsModel) -> Unit) {
    fun onContactItemClick(contact: ContactsModel) = contactClickListener(contact)
}

class ContactListDiffUtil : DiffUtil.ItemCallback<ContactsModel>() {
    override fun areItemsTheSame(oldItem: ContactsModel, newItem: ContactsModel): Boolean {
        return oldItem.contactId == newItem.contactId
    }

    override fun areContentsTheSame(oldItem: ContactsModel, newItem: ContactsModel): Boolean {
        return oldItem == newItem
    }

}