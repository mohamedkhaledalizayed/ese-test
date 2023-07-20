package com.neqabty.healthcare.onboarding.contact.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SubmitClientRequest(
    @SerializedName("nationalId")
    var nationalId: String = "",
    @SerializedName("userInputNationalId")
    var userInputNationalId: String = "",
    @SerializedName("clientInfo")
    var clientInfo: ClientInfo = ClientInfo(),
    @SerializedName("employmentDetails")
    var employmentDetails: EmploymentDetails = EmploymentDetails(),
    @SerializedName("boosters")
    var boosters: Boosters = Boosters(),
    @SerializedName("bulkFile")
    var bulkFile: Boolean = false
)

data class ClientInfo(
    @SerializedName("homeAddress")
    var homeAddress: String = "",
    @SerializedName("homeGov")
    var homeGov: String = "",
    @SerializedName("homeArea")
    var homeArea: String = "",
    @SerializedName("maritalStatus")
    var maritalStatus: String = "",
    @SerializedName("dependentsNum")
    var dependentsNum: String = "",
    @SerializedName("refContactName")
    var refContactName: String = "",
    @SerializedName("refContactNum")
    var refContactNum: String = "",
    @SerializedName("userName")
    var userName: String = "",
    @SerializedName("phone")
    var phone: String = "",
    @SerializedName("email")
    var email: String = "",
    @SerializedName("residentStatus")
    var residentStatus: String = ""
)

data class EmploymentDetails(
    @SerializedName("employmentStatus")
    var employmentStatus: String = "",
    @SerializedName("employmentSector")
    var employmentSector: String = "",
    @SerializedName("jobTitle")
    var jobTitle: String = "",
    @SerializedName("currentWorkYears")
    var currentWorkYears: String = "",
    @SerializedName("appMonthlyIncome")
    var appMonthlyIncome: String = "0",
    @SerializedName("requestedLimit")
    var requestedLimit: String = "10000",
    @SerializedName("companyName")
    var companyName: String = "",
    @SerializedName("workAddress")
    var workAddress: String = "",
    @SerializedName("workGov")
    var workGov: String = "",
    @SerializedName("workArea")
    var workArea: String = ""
)

data class Boosters(
    @SerializedName("location")
    var location: Location = Location(),
    @SerializedName("deviceType")
    var deviceType: String = "string"
)

data class Location(
    @SerializedName("lat")
    var lat: String = "string",
    @SerializedName("long")
    var long: String = "string"
)