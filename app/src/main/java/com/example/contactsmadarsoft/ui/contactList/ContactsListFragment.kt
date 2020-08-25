package com.example.contactsmadarsoft.ui.contactList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.contactsmadarsoft.adapter.ContactListAdapter
import com.example.contactsmadarsoft.adapter.ContactListItemListener
import com.example.contactsmadarsoft.databinding.ContactsListFragmentBinding
import com.example.contactsmadarsoft.models.ContactsModel
import kotlinx.android.synthetic.main.contacts_list_fragment.*

class ContactsListFragment : Fragment() {

    companion object {
        fun newInstance() =
            ContactsListFragment()
    }

    private val viewModel: ContactsListViewModel by lazy {
        ViewModelProviders.of(this).get(ContactsListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ContactsListFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        val adapter = ContactListAdapter(ContactListItemListener {
            val action =
                ContactsListFragmentDirections.actionContactsListFragmentToAddAndUpdateContactFragment(
                    it
                )
            this.findNavController().navigate(action)
        })
        binding.contactsRecyclerView.adapter = adapter

        viewModel.contacts.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.contacts.observe(viewLifecycleOwner, Observer {
            Log.d("numberrrr", it.size.toString())
        })
        AddContact_btn.setOnClickListener {
            val contact = ContactsModel()
            val action =
                ContactsListFragmentDirections.actionContactsListFragmentToAddAndUpdateContactFragment(contact)
            this.findNavController().navigate(action)
        }
    }

}