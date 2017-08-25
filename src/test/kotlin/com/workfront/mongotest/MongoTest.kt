package com.workfront.mongotest

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import java.util.Random
import java.io.IOException
import java.io.OutputStream


class MongoTest : BaseSpringIntegration() {
    companion object : Loggable

    @Autowired lateinit var jobRepository: JobRepository
    @Autowired lateinit var addressRepository: AddressRepository
    @Autowired lateinit var personRepository: PersonRepository
    @Autowired lateinit var mongoTemplate: MongoTemplate

    private val directions = listOf("east", "west", "north", "south")
    private val streets = listOf("street", "road", "court", "drive", "parkway")

    private val objectMapper = ObjectMapper().registerModule(KotlinModule())

    @Test
    fun search() {
        val query = Query()
        query.addCriteria(Criteria.where("age").ne(60))
        query.with(Sort(Sort.Direction.ASC, "name"))
        query.limit(100_000)

        (1..2).forEach {
            val start = System.nanoTime()
            logger.info("Searching $it")
//            val find = personRepository.findByAgeNot(PageRequest(0, 10000, Sort.Direction.ASC, "name"), 60)
            val find = mongoTemplate.find(query, Person::class.java)
            objectMapper.writeValue(NullOutputStream(), find) // Force it to iterate all fields

            val timeNano = System.nanoTime() - start
            logger.info("Found ${find.size} in $timeNano ns")
        }
    }

    @Test
    fun populate() {
        logger.info("Creating Addresses")
        val addresses = addressRepository.save((1..1000)
                .map { Address(value = "$it ${directions.random()} ${streets.random()}") })

        logger.info("Creating Most Bossy Jobs")
        val mostBossyJobs = jobRepository.save((1..1000)
                .map { Job(companyName = "Job $it", salary = 1_000_000_000) })

        val mostBossyPersons = personRepository.save((1..1_000_000)
                .map { Person(name="Most Bossy$it", address = addresses.random(), job = mostBossyJobs.random(), age = 60) })

        logger.info("Creating Really Bossy Jobs")
        val reallyBossyJobs = jobRepository.save((1..1000)
                .map { Job(companyName = "Job $it", salary = 1_000_000, boss = mostBossyPersons.random()) })

        val reallyBossyPeople = personRepository.save((1..1_000_000)
                .map { Person(name="Really Bossy$it", address = addresses.random(), job=reallyBossyJobs.random(), age = 50) })

        logger.info("Creating Pretty Bossy Jobs")
        val prettyBossyJobs = jobRepository.save((1..1000)
                .map { Job(companyName = "Job $it", salary = 1_000, boss = reallyBossyPeople.random()) })

        val prettyBossyPeople = personRepository.save((1..1_000_000)
                .map { Person(name = "Pretty Bossy$it", address = addresses.random(), job = prettyBossyJobs.random(), age = 40) })

        logger.info("Creating Underling Jobs")
        val underlingJobs = jobRepository.save((1..1000)
                .map { Job(companyName = "Job $it", salary = 1, boss = prettyBossyPeople.random()) })

        personRepository.save((1..1_000_000)
                .map { Person(name = "Pretty Bossy$it", address = addresses.random(), job = underlingJobs.random(), age = 30) })
    }
}

val random = Random()
private fun <E> List<E>.random(): E {
    return this[random.nextInt(this.size)]
}

class NullOutputStream : OutputStream() {
    @Throws(IOException::class)
    override fun write(b: Int) {
    }
}