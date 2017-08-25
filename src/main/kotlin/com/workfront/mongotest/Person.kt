package com.workfront.mongotest

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.IndexDirection
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "people")
@JsonIgnoreProperties(ignoreUnknown = true)
open class Person(
    @Id
    var id: String? = null,

    @Indexed(direction = IndexDirection.ASCENDING)
    var name: String? = null,
    var age: Int = 0,

    @DBRef(lazy = true)
//    @DBRef(lazy = false)
    var address: Address? = null,

    @DBRef(lazy = true)
//    @DBRef(lazy = false)
    var job: Job? = null
)