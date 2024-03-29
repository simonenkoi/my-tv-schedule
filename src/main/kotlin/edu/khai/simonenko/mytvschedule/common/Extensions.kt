package edu.khai.simonenko.mytvschedule.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger

inline fun <reified T : Logging> T.logger(): Logger =
    getLogger(T::class.java)