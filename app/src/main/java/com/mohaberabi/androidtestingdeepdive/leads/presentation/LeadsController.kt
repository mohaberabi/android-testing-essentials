package com.mohaberabi.androidtestingdeepdive.leads.presentation

import com.mohaberabi.androidtestingdeepdive.leads.domain.model.LeadModel


class LeadsController {


    private val leads = mutableListOf<LeadModel>()

    fun getLeads(): List<LeadModel> = leads.toList()


    fun addLead(lead: LeadModel) {
        if (isValidInput(lead.name)
            && isValidInput(lead.lastName)
            && phoneValid(lead.phone)
        ) {
            leads.add(lead)
        }
    }
 

    private fun phoneValid(
        phone: String,
    ) = phone.length == 11 && phone.all { it.isDigit() }

    private fun isValidInput(
        value: String,
        min: Int = 3
    ) = value.length >= min
}