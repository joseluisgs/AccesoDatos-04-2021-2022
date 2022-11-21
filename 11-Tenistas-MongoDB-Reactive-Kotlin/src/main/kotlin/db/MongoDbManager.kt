package db


import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoDatabase
import mu.KotlinLogging
import org.litote.kmongo.reactivestreams.KMongo


private val logger = KotlinLogging.logger {}


object MongoDbManager {
    private lateinit var mongoClient: MongoClient
    lateinit var database: MongoDatabase

    // Deberíamos leer de las propiedades la conexión y esas cosas

    init {
        logger.debug("Inicializando conexion a MongoDB")
        mongoClient = KMongo.createClient("mongodb://mongoadmin:mongopass@localhost/tenistas?authSource=admin")
        database = mongoClient.getDatabase("tenistas")
    }
}

