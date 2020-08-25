package com.example.contactsmadarsoft.ui.contactList

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.contactsmadarsoft.R
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
        setHasOptionsMenu(true)


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

        viewModel.filterContact.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        viewModel.isEmpty.observe(viewLifecycleOwner, Observer {
            if (it == false) {
                binding.contactsNotFound.visibility = View.GONE
            } else {
                binding.contactsNotFound.visibility = View.VISIBLE
            }
        })

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AddContact_btn.setOnClickListener {
            val contact = ContactsModel()
            val action =
                ContactsListFragmentDirections.actionContactsListFragmentToAddAndUpdateContactFragment(
                    contact
                )
            this.findNavController().navigate(action)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val search = menu.findItem(R.id.searchContacts)
        val searchView = search.actionView as SearchView
        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.setQuery(it) }
                return true
            }

        })

        super.onCreateOptionsMenu(menu, inflater)
    }


}