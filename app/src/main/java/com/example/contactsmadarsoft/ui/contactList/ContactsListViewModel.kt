package com.example.contactsmadarsoft.ui.contactList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.contactsmadarsoft.datdabase.ContactsDatabase
import com.example.contactsmadarsoft.models.ContactsModel
import com.example.contactsmadarsoft.repository.ContactsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ContactsListViewModel(application: Application) : AndroidViewModel(application) {

//
//    // setup coroutine to do backgroundTask
//    private val viewModelJob = SupervisorJob()
//    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
//
//
//    override fun onCleared() {
//        super.onCleared()
//        viewModelJob.cancel()
//    }

    // setup database and data repository
    private val database = ContactsDatabase.getInstance(application)
    private val contactRepo = ContactsRepository(database = database)


    val contacts = contactRepo.allContacts




}