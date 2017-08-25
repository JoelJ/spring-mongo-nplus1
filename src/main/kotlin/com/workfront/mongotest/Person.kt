package com.workfront.mongotest

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "people")
@JsonIgnoreProperties(value = *arrayOf("hibernateLazyInitializer", "handler"), ignoreUnknown = true)
open class Person(
    @Id
    var id: String? = null,
    var name: String? = null,
    var age: Int = 0,

    @DBRef(lazy = true)
//    @DBRef(lazy = false)
    var address: Address? = null,

    @DBRef(lazy = true)
//    @DBRef(lazy = false)
    var job: Job? = null
)