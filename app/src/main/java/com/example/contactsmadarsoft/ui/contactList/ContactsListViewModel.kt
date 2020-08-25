package com.example.contactsmadarsoft.ui.contactList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.contactsmadarsoft.datdabase.ContactsDatabase
import com.example.contactsmadarsoft.models.ContactsModel
import com.example.contactsmadarsoft.repository.ContactsRepository

class ContactsListViewModel(application: Application) : AndroidViewModel(application) {

    // setup database and data repository
    private val database = ContactsDatabase.getInstance(application)
    private val contactRepo = ContactsRepository(database = database)


    val contacts = contactRepo.allContacts
    val isEmpty = Transformations.map(contacts){
        it.isNullOrEmpty()
    }



    private val query = MutableLiveData<String>(" ")

    fun setQuery(text: String) {
        query.value = text
    }


    var filterContact: LiveData<List<ContactsModel>> = Transformations.map(query)
    {
        filter(it)
    }

    private fun filter(query: String?): List<ContactsModel>? {

        return if (query?.isEmpty()!!) {
            contacts.value
        } else {
            contacts.value?.filter {
                it.contactName.toLowerCase().contains(query.toLowerCase())
                        || it.contactJobTitle.toLowerCase().contains(query.toLowerCase())
            }
        }

    }


}