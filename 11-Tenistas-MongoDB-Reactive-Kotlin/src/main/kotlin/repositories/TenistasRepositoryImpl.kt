package repositories

import db.MongoDbManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.reactive.asFlow
import models.Tenista
import mu.KotlinLogging
import org.litote.kmongo.Id
import org.litote.kmongo.reactivestreams.deleteOneById
import org.litote.kmongo.reactivestreams.findOneById
import org.litote.kmongo.reactivestreams.getCollection
import org.litote.kmongo.reactivestreams.save

private val logger = KotlinLogging.logger {}

class TenistasRepositoryImpl : TenistasRepository {
    override fun findAll(): Flow<Tenista> {
        return MongoDbManager.database.getCollection<Tenista>()
            .find().asFlow()
    }

    override suspend fun findById(id: Id<Tenista>): Tenista? {
        logger.debug { "findById($id)" }
        return MongoDbManager.database.getCollection<Tenista>()
            .findOneById(id)
            .asFlow()
            .firstOrNull()
    }

    override suspend fun save(entity: Tenista): Tenista? {
        logger.debug { "save($entity)" }
        return MongoDbManager.database.getCollection<Tenista>()
            .save(entity).asFlow().firstOrNull() as Tenista?
        // tambien puedes usar awaitFirstOrNull()
    }

    private suspend fun insert(entity: Tenista): Tenista {
        logger.debug { "save($entity) - creando" }
        return MongoDbManager.database.getCollection<Tenista>()
            .save(entity)
            .asFlow()
            .firstOrNull() as Tenista
    }

    private suspend fun update(entity: Tenista): Tenista {
        logger.debug { "save($entity) - actualizando" }
        return MongoDbManager.database.getCollection<Tenista>().save(entity)
            .asFlow()
            .firstOrNull() as Tenista
    }

    override suspend fun delete(entity: Tenista): Boolean {
        logger.debug { "delete($entity)" }
        return MongoDbManager.database.getCollection<Tenista>()
            .deleteOneById(entity.id)
            .asFlow()
            .firstOrNull() != null

        // o usar awaitFirstOrNull() y comparar con null
    }
}