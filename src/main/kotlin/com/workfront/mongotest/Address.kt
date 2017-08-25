package com.workfront.mongotest

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "addresses")
@JsonIgnoreProperties(ignoreUnknown = true)
open class Address (
    @Id
    var id: String? = null,
    var value: String? = null
)