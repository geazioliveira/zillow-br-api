package com.zillowbrapi.common.database

import java.time.Instant

interface BaseModel {
    val id: Long?
    val createdAt: Instant?
    val updatedAt: Instant?
    val deletedAt: Instant?
}