package com.example.contactsmadarsoft.repository

import com.example.contactsmadarsoft.datdabase.ContactsDatabase
import com.example.contactsmadarsoft.models.ContactsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactsRepository(private val database: ContactsDatabase) {

    val allContacts = database.contactsDatabaseDAO.getAllContacts()


    suspend fun insertContact(contact: ContactsModel) {
        withContext(Dispatchers.IO)
        {
            database.contactsDatabaseDAO.insertContact(contact)
        }
    }

    suspend fun updateContact(contact: ContactsModel) {
        withContext(Dispatchers.IO)
        {
            database.contactsDatabaseDAO.updateContact(contact)
        }
    }

    suspend fun deleteContact(contact: ContactsModel) {
        withContext(Dispatchers.IO)
        {
            database.contactsDatabaseDAO.deleteContact(contact)
        }
    }

}