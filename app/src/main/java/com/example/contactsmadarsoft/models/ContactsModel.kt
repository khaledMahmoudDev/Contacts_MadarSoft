package com.example.contactsmadarsoft.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "contacts_table")
data class ContactsModel(
    @PrimaryKey(autoGenerate = true)
    var contactId: Long = 0L,

    @ColumnInfo(name = "contact_name")
    var contactName: String = "",

    @ColumnInfo(name = "contact_age")
    var contactAge: Int = 0,

    @ColumnInfo(name = "contact_job_title")
    var contactJobTitle: String = "",

    @ColumnInfo(name = "contact_gender")
    var contactGender: String = ""
) : Parcelable
