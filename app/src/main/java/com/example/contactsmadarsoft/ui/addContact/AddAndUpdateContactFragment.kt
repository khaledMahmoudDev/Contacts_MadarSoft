package com.example.contactsmadarsoft.ui.addContact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.contactsmadarsoft.databinding.AddAndUpdateContactFragmentBinding

class AddAndUpdateContactFragment : Fragment() {

    companion object {
        fun newInstance() = AddAndUpdateContactFragment()
    }

    private val viewModel: AddAndUpdateContactViewModel by lazy {
        ViewModelProviders.of(this).get(AddAndUpdateContactViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = AddAndUpdateContactFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val safeArgs: AddAndUpdateContactFragmentArgs by navArgs()
        val receivedContact = safeArgs.selectedContact
        viewModel.setReceivedContact(receivedContact)

        viewModel.isNewContact.observe(viewLifecycleOwner, Observer {
            if (true == it) {
                binding.updateBtn.visibility = View.INVISIBLE
                binding.deleteBtn.visibility = View.INVISIBLE
            } else {
                binding.addBtnToDB.visibility = View.INVISIBLE
            }
        })




        viewModel.addContactMessage.observe(viewLifecycleOwner, Observer {
            if (it != AddContactValidation.Good) {
                var toastMessage = ""

                when (it) {
                    AddContactValidation.ContactNameError -> {
                        toastMessage = "please enter valid name"
                    }
                    AddContactValidation.ContactJobTitleError -> {
                        toastMessage = "please enter valid job title"
                    }
                    AddContactValidation.ContactAgeError -> {
                        toastMessage = "please pick contact age"
                    }
                    AddContactValidation.ContactGenderError -> {
                        toastMessage = "please select gender"
                    }
                    else -> {
                        toastMessage = "Unknown Error"
                    }
                }

                Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show()
            }
        })
        viewModel.addContactEvent.observe(viewLifecycleOwner, Observer {
            if (false != it) {
                val action =
                    AddAndUpdateContactFragmentDirections.actionAddAndUpdateContactFragmentToContactsListFragment()
                findNavController().navigate(action)
                viewModel.completeAddContact()
            }
        })

        return binding.root

    }


}