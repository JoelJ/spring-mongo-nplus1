package com.workfront.mongotest

import org.springframework.data.mongodb.repository.MongoRepository

interface AddressRepository : MongoRepository<Address, String>