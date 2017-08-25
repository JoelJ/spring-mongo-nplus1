package com.workfront.mongotest

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.full.companionObject

val <T : Loggable> T.logger: Logger
    get() {
        val ofClass = this::class.java
        if (ofClass.enclosingClass != null && ofClass.enclosingClass.kotlin.companionObject?.java == ofClass) {
            return LoggerFactory.getLogger(ofClass.enclosingClass)
        } else {
            return LoggerFactory.getLogger(ofClass)
        }
    }
