package models

import extensions.toLocalDate
import extensions.toLocalMoney
import extensions.toLocalNumber
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.time.LocalDate

@Serializable
data class Tenista(
    @BsonId @Contextual
    val id: Id<Tenista> = newId<Tenista>(),
    var nombre: String,
    var ranking: Int,
    @Contextual
    var fechaNacimiento: LocalDate,
    var añoProfesional: Int,
    var altura: Int,
    var peso: Int,
    var ganancias: Double,
    var manoDominante: ManoDominante,
    var tipoReves: TipoReves,
    var puntos: Int,
    var pais: String,
    var raqueta: Raqueta? = null, // No tiene por que tener raqueta
) {

    override fun toString(): String {
        return "Tenista(uuid=$id, nombre='$nombre', ranking=$ranking, " +
                "fechaNacimiento=${fechaNacimiento.toLocalDate()}, " +
                "añoProfesional=$añoProfesional, " +
                "altura=${(altura.toDouble() / 100).toLocalNumber()} cm, " +
                "peso=$peso Kg, " +
                "ganancias=${ganancias.toLocalMoney()}, " +
                "manoDominante=${manoDominante.mano}, " +
                "tipoReves=${tipoReves.tipo}, " +
                "puntos=$puntos, pais=$pais" +
                "raqueta=${raqueta})"
    }

    // ENUMS de la propia clase
    enum class ManoDominante(val mano: String) {
        DERECHA("DERECHA"),
        IZQUIERDA("IZQUIERDA");

        companion object {
            fun from(manoDominante: String): ManoDominante {
                return when (manoDominante.uppercase()) {
                    "DERECHA" -> DERECHA
                    "IZQUIERDA" -> IZQUIERDA
                    else -> throw IllegalArgumentException("ManoDominante no reconocida")
                }
            }
        }
    }

    enum class TipoReves(val tipo: String) {
        UNA_MANO("UNA MANO"),
        DOS_MANOS("DOS MANOS");

        companion object {
            fun from(tipoReves: String): TipoReves {
                return when (tipoReves.uppercase()) {
                    "UNA MANO" -> UNA_MANO
                    "DOS MANOS" -> DOS_MANOS
                    else -> throw IllegalArgumentException("TipoReves no reconocida")
                }
            }
        }
    }


}