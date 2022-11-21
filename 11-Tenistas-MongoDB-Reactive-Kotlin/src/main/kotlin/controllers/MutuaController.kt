package controllers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import models.Tenista
import mu.KotlinLogging
import org.litote.kmongo.Id
import repositories.TenistasRepository

private val logger = KotlinLogging.logger {}

class MutuaController(
    private val tenistasRepository: TenistasRepository
) {

    // TENISTAS

    fun getTenistas(): Flow<Tenista> {
        logger.info("Obteniendo Tenistas")
        return tenistasRepository.findAll()
            .flowOn(Dispatchers.IO)
    }

    suspend fun createTenista(tenista: Tenista): Tenista {
        logger.debug { "Creando tenista $tenista" }
        tenistasRepository.save(tenista)
        return tenista
    }

    suspend fun getTenistaById(id: Id<Tenista>): Tenista? {
        logger.debug { "Obteniendo tenista con id $id" }
        return tenistasRepository.findById(id)
    }

    suspend fun updateTenista(tenista: Tenista) {
        logger.debug { "Updating tenista $tenista" }
        tenistasRepository.save(tenista)
    }

    suspend fun deleteTenista(tenista: Tenista): Boolean {
        logger.debug { "Borrando tenista $tenista" }
        return tenistasRepository.delete(tenista)
    }
}