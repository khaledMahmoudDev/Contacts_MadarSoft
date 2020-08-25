package com.example.contactsmadarsoft.ui.addContact

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.NumberPicker
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.contactsmadarsoft.R
import com.example.contactsmadarsoft.Utils.Validation
import com.example.contactsmadarsoft.Utils.ValidationMSG
import com.example.contactsmadarsoft.datdabase.ContactsDatabase
import com.example.contactsmadarsoft.models.ContactsModel
import com.example.contactsmadarsoft.repository.ContactsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class AddAndUpdateContactViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var contact: ContactsModel


    // setup coroutine to do backgroundTask
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    var contactName = MutableLiveData<String>()
    private val isContactNAmeValid: LiveData<ValidationMSG> = Transformations.map(contactName)
    { Validation.validateInput(it) }
    var contactNameErrorMessage: LiveData<String> = Transformations.map(isContactNAmeValid)
    { Validation.validationResult(it) }


    var contactJobTitle = MutableLiveData<String>()
    private val isJobTitleValid: LiveData<ValidationMSG> = Transformations.map(contactJobTitle)
    { Validation.validateInput(it) }
    var contactJobTitleErrorMessage: LiveData<String> = Transformations.map(isJobTitleValid)
    { Validation.validationResult(it) }


    var contactAge = MutableLiveData<Int>(0)
    var contactAgeTxt = MutableLiveData<String>("Pick Age")

    fun pickAge(context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.pick_age_dialog)

        val okButton = dialog.findViewById<Button>(R.id.ok_btn)
        val cancelButton = dialog.findViewById<Button>(R.id.cancel_btn)
        val agePicker = dialog.findViewById<NumberPicker>(R.id.numberPicker)
        agePicker.maxValue = 100
        agePicker.minValue = 12
        okButton.setOnClickListener {
            contactAge.value = agePicker.value
            contactAgeTxt.value = agePicker.value.toString()

            dialog.dismiss()
        }
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    var selectedGender = MutableLiveData<ContactGender>(ContactGender.NOTSELECTED)


    fun selectGender(gender: Int) {
        when (gender) {
            1 -> {
                selectedGender.value = ContactGender.Male

            }
            2 -> {
                selectedGender.value = ContactGender.Female

            }
            else -> {
                selectedGender.value = ContactGender.NOTSELECTED

            }
        }

    }

    val isNewContact = MutableLiveData<Boolean>(false)

    fun setReceivedContact(receivedContact: ContactsModel) {
        contact = receivedContact
        if (contact.contactId == 0L) {
            isNewContact.value = true
        } else {
            contactJobTitle.value = contact.contactJobTitle
            contactName.value = contact.contactName
            contactAge.value = contact.contactAge
            contactAgeTxt.value = contact.contactAge.toString()
        }
    }

    var addContactMessage = MutableLiveData<AddContactValidation>()

    fun addContact() {
        if (checkFields() != AddContactValidation.Good) {
            addContactMessage.value = checkFields()
        } else {

            val contactGender = when (selectedGender.value) {
                ContactGender.Male -> "Male"
                ContactGender.Female -> "Female"
                else -> "NOTSELECTED"
            }

            contact = ContactsModel(
                contactName = contactName.value!!,
                contactJobTitle = contactJobTitle.value!!,
                contactAge = contactAge.value!!,
                contactGender = contactGender
            )
            addContactToDB(contact)
        }


    }

    private fun checkFields(): AddContactValidation {
        when {
            isContactNAmeValid.value != ValidationMSG.Good -> {
                return AddContactValidation.ContactNameError
            }
            isJobTitleValid.value != ValidationMSG.Good -> {
                return AddContactValidation.ContactJobTitleError
            }
            contactAge.value == 0 -> {
                return AddContactValidation.ContactAgeError
            }
            selectedGender.value == ContactGender.NOTSELECTED -> {
                return AddContactValidation.ContactGenderError
            }
            else -> {
                return AddContactValidation.Good
            }
        }
    }

    //database and repo
    private val database = ContactsDatabase.getInstance(application)
    private val contactRepo = ContactsRepository(database = database)

    var addContactEvent = MutableLiveData<Boolean>(false)


    private fun addContactToDB(contact: ContactsModel) {
        viewModelScope.launch {
            contactRepo.insertContact(contact)
        }
        addContactEvent.value = true
    }

    fun deleteContact() {
        viewModelScope.launch {
            contactRepo.deleteContact(contact)
        }
        addContactEvent.value = true
    }

    fun update() {
        if (checkFields() != AddContactValidation.Good) {
            addContactMessage.value = checkFields()
        } else {

            val contactGender = when (selectedGender.value) {
                ContactGender.Male -> "Male"
                ContactGender.Female -> "Female"
                else -> "NOTSELECTED"
            }

            contact = ContactsModel(
                contactId = contact.contactId,
                contactName = contactName.value!!,
                contactJobTitle = contactJobTitle.value!!,
                contactAge = contactAge.value!!,
                contactGender = contactGender
            )
            updateContact(contact)
        }

    }

    private fun updateContact(contact: ContactsModel) {
        viewModelScope.launch {
            contactRepo.updateContact(contact)
        }
        addContactEvent.value = true

    }

    fun completeAddContact() {
        addContactEvent.value = false
    }


}

enum class AddContactValidation {
    ContactNameError,
    ContactJobTitleError,
    ContactAgeError,
    ContactGenderError,
    Good
}


enum class ContactGender {
    Male,
    Female,
    NOTSELECTED
}