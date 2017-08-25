package com.workfront.mongotest

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "jobs")
@JsonIgnoreProperties(ignoreUnknown = true)
open class Job(
    @Id
    var id: String? = null,
    var companyName: String? = null,

//    @DBRef(lazy = true)
    @DBRef(lazy = false)
    var boss: Person? = null,
    var salary: Int = 0
)