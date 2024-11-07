package com.mohaberabi.androidtestingdeepdive.leads.presentation.controller

import com.mohaberabi.androidtestingdeepdive.leads.domain.model.LeadModel
import com.mohaberabi.androidtestingdeepdive.leads.presentation.LeadsController
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class LeadsControllerTest {

    private lateinit var controller: LeadsController

    @Before
    fun setup() {
        controller = LeadsController()
    }

    @Test
    fun `Given valid lead When call addLead then leads list has one items`() {
        val lead = LeadModel("Mohab", "Erabi", "01")
        controller.addLead(lead)
        assertEquals(1, controller.getLeads().size)
    }

    @Test
    fun `Given a lead with firstname less than 3 chars it is not added to list `() {
        val lead = LeadModel("s", "Erabi", "01")
        controller.addLead(lead)
        assertEquals(0, controller.getLeads().size)
    }

    @Test
    fun `Given a lead with firstname less grater or equal to  3 chars it is  added to list `() {
        val lead = LeadModel("slkasnlksanlsak", "Erabi", "01")
        controller.addLead(lead)
        assertEquals(1, controller.getLeads().size)
    }

    @Test
    fun `Given a lead with lastname less than 3 chars it is not added to list `() {
        val lead = LeadModel("sasdasd", "a", "01")
        controller.addLead(lead)
        assertEquals(0, controller.getLeads().size)
    }

    @Test
    fun `Given a lead with lastname greater or equal to  3 chars it is  added to list `() {
        val lead = LeadModel("sasdasd", "aasdasd", "01")
        controller.addLead(lead)
        assertEquals(1, controller.getLeads().size)
    }

    @Test
    fun `Given a lead with not valid phone  it is not  added to list `() {
        val lead = LeadModel("sasdasd", "aasdasd", "01")
        controller.addLead(lead)
        assertEquals(0, controller.getLeads().size)
    }

    @Test
    fun `Given a lead with  valid phone  it is  added to list `() {
        val lead = LeadModel("sasdasd", "aasdasd", "11111111111")
        controller.addLead(lead)
        assertEquals(1, controller.getLeads().size)
    }

    @Test
    fun `Given a lead with  valid phone length not numbers   it is not  added to list `() {
        val lead = LeadModel("sasdasd", "aasdasd", "aaaaaaaaaaa")
        controller.addLead(lead)
        assertEquals(0, controller.getLeads().size)
    }
}