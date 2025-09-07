package com.zillowbrapi.common.database

import java.time.Instant
import java.util.*

interface BaseModel {
    val id: UUID?
    val createdAt: Instant?
    val updatedAt: Instant?
    val deletedAt: Instant?
}