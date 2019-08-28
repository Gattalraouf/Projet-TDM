package com.gattal.asta.mobileproject.data

import java.io.Serializable

class Product(
    var id: String,
    var category: String,
    var type: String,
    var localisation: String,
    var surface: String,
    var owner: Owner,
    var imgs: List<String>,
    var videos: List<String>,
    var price: String,
    var descr: String
) : Serializable