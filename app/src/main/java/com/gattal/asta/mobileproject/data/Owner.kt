package com.gattal.asta.mobileproject.data

import java.io.Serializable

class Owner(
    var name: String,
    var lastName: String,
    var address: String,
    var email: String,
    var phone1: String,
    var phone2: String
) : Serializable