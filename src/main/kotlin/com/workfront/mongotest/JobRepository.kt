package com.workfront.mongotest

import org.springframework.data.mongodb.repository.MongoRepository

interface JobRepository : MongoRepository<Job, String>