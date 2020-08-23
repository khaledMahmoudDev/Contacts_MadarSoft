package com.example.contactsmadarsoft.models

data class ContactsModel(
    var contactId: Long = 0L,
    var contactName: String = "",
    var contactAge: Int = 0,
    var contactJobTitle: String = "",
    var contactGender: Gender = Gender.NOTSPECIFIED
)

enum class Gender {
    NOTSPECIFIED,
    MALE,
    FEMALE
}