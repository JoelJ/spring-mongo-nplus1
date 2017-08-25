package com.workfront.mongotest

import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import java.util.Random

class MongoTest : BaseSpringIntegration() {
    companion object : Loggable

    @Autowired lateinit var jobRepository: JobRepository
    @Autowired lateinit var addressRepository: AddressRepository
    @Autowired lateinit var personRepository: PersonRepository
    @Autowired lateinit var mongoTemplate: MongoTemplate

    private val directions = listOf("east", "west", "north", "south")
    private val streets = listOf("street", "road", "court", "drive", "parkway")

    @Test
    fun search() {
        val query = Query()
        query.addCriteria(Criteria.where("salary").ne(1_000_000_000))
        query.with(Sort(Sort.Direction.ASC, "name"))
        query.limit(100_000)

        (1..10).forEach {
            val start = System.nanoTime()
            logger.info("Searching $it")
            val find = mongoTemplate.find(query, Person::class.java)

            val timeNano = System.nanoTime() - start
            logger.info("Found ${find.size} in $timeNano ns")
        }
    }

    @Test
    fun populate() {
        logger.info("Creating Addresses")
        var addresses = (1..1000)
                .map { Address(value = "$it ${directions.random()} ${streets.random()}") }
                .map { addressRepository.save(it) }

        logger.info("Creating Most Bossy Jobs")
        var mostBossyJobs = (1..1000)
                .map { Job(companyName = "Job $it", salary = 1_000_000_000) }
                .map { jobRepository.save(it) }

        var mostBossyPersons = (1..1_000_000)
                .map { Person(name="Most Bossy$it", address = addresses.random(), job = mostBossyJobs.random()) }
                .map { personRepository.save(it) }

        logger.info("Creating Really Bossy Jobs")
        var reallyBossyJobs = (1..1000)
                .map { Job(companyName = "Job $it", salary = 1_000_000, boss = mostBossyPersons.random()) }
                .map { jobRepository.save(it) }

        var reallyBossyPeople = (1..1_000_000)
                .map { Person(name="Really Bossy$it", address = addresses.random(), job=reallyBossyJobs.random()) }
                .map { personRepository.save(it) }

        mostBossyJobs = listOf()  // free some references
        mostBossyPersons = listOf()  // free some references

        logger.info("Creating Pretty Bossy Jobs")
        var prettyBossyJobs = (1..1000)
                .map { Job(companyName = "Job $it", salary = 1_000, boss = reallyBossyPeople.random()) }
                .map { jobRepository.save(it) }

        var prettyBossyPeople = (1..1_000_000)
                .map { Person(name = "Pretty Bossy$it", address = addresses.random(), job = prettyBossyJobs.random()) }
                .map { personRepository.save(it) }

        reallyBossyJobs = listOf()  // free some references
        reallyBossyPeople = listOf()  // free some references

        logger.info("Creating Underling Jobs")
        var underlingJobs = (1..1000)
                .map { Job(companyName = "Job $it", salary = 1, boss = prettyBossyPeople.random()) }
                .map { jobRepository.save(it) }

        (1..1_000_000)
                .map { Person(name = "Pretty Bossy$it", address = addresses.random(), job = underlingJobs.random()) }
                .map { personRepository.save(it) }

        prettyBossyJobs = listOf()
        prettyBossyPeople = listOf()
        underlingJobs = listOf()
        addresses = listOf()
    }
}

val random = Random()
private fun <E> List<E>.random(): E {
    return this[random.nextInt(this.size)]
}
