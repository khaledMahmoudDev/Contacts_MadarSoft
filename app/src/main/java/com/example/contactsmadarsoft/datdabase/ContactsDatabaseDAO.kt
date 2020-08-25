package com.example.contactsmadarsoft.datdabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.contactsmadarsoft.models.ContactsModel

@Dao
interface ContactsDatabaseDAO {

    @Insert
    fun insertContact(contact: ContactsModel)

    @Update
    fun updateContact(contact: ContactsModel)

    @Query(value = "SELECT * from contacts_table WHERE contactId = :key")
    fun getContact(key: Long): ContactsModel

    @Delete
    fun deleteContact(contact: ContactsModel)

    @Query(value = "SELECT * from contacts_table ORDER BY contactId DESC")
    fun getAllContacts(): LiveData<List<ContactsModel>>



}