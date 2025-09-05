package com.zillowbrapi.common.database

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@MappedSuperclass
@SQLDelete(sql = "UPDATE #{#entityName} SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    var createdAt: Instant? = null

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: Instant? = null

    @Column(name = "deleted_at")
    var deletedAt: Instant? = null

    fun isDeleted(): Boolean = deletedAt != null

    fun softDelete() {
        deletedAt = Instant.now()
    }

    fun restore() {
        deletedAt = null
    }
}