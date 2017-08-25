package com.workfront.mongotest

import org.springframework.data.mongodb.repository.MongoRepository

interface PersonRepository : MongoRepository<Person, String>