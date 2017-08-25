package com.workfront.mongotest

import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(MongoTestApp::class))
@ActiveProfiles(profiles = arrayOf("test"))
abstract class BaseSpringIntegration {

}
