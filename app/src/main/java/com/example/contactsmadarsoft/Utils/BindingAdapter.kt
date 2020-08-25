package com.example.contactsmadarsoft.Utils

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.example.contactsmadarsoft.R
import com.example.contactsmadarsoft.models.ContactsModel

@BindingAdapter("layoutBackGround")
fun layoutBackGround(view: ConstraintLayout, data: ContactsModel)
{
    if (data.contactGender == "Male")
    {
        view.setBackgroundColor(view.resources.getColor(R.color.maleColor))
    }else
    {
        view.setBackgroundColor(view.resources.getColor(R.color.femaleColor))
    }

}