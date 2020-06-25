import Participante._
import Dragon.{FuriaNocturna, Gronckle}
import Postas._
import Requerimiento._
import org.scalatest.{FreeSpec, Matchers}

class ProjectSpec extends FreeSpec with Matchers {

  "Este proyecto" - {

    "cuando está correctamente configurado" - {
      "debería resolver las dependencias y pasar este test" in {
        Prueba.materia shouldBe "tadp"
      }
    }
    "Pesca: Vikingo carga 50% de su peso + el doble de barbosidad. " - {
      "Incrementa 5% su hambre" in {

      }
    }
    "Combate: vikingo barbosidad + daño item." - {
      "Incrementa 10% su hambre" in {

      }
    }
    "Carrera vikingo mas veloz." - {
      "Incrementa hambre 1% por kilometro" in {

      }
    }
    "Patapez" - {
      "No puede participar Posta si hambre > 50%" in {

      }
      "Doble de hambre luego de posta" in {

      }
    }
    "Hipo" - {
      "item sistema de vuelo" in {

      }
    }
    "Astrid" - {
      "item hacha aumenta 30 puntos daño" in {
        val astrid: Vikingo = Vikingo(Stats(60, 3, 5), Some(Hacha))
        astrid.danio shouldBe 35

      }
    }
    "Patan" - {
      "item maza aumenta 100 puntos daño" in {

      }
    }

    "Dragones" - {
      "Chimuelo: velocidad triple que dragon base" in {

      }
      "Nadder: produce 150 de daño" in {

      }
      "Gronckle: velocidad base a la mitad. Daño es 5 veces su peso" in {
        val gronckle: Gronckle = Gronckle(10)
        gronckle.velocidadBase shouldBe 30
        gronckle.velocidad shouldBe 20
      }
      "Gronckle:Daño es 5 veces su peso" in {

      }
    }

    "Jinetes" - {
      //Jinete exitoso y fracasado (Sin causar efecto sobre draon o vikingo). Dragon puede cargar el 20% de su peso
      //Chimuelo: vikingo tenga sistema vuelo
      //Nadder: vikingo tengo daño menor
      //Gronckle: vikingo no supere peso X
      //
      //Pesca jinete: Cargan tanto como carga posible dragon - peso vikingo
      //Combate jinete: Suma daño dragon y daño vikingo
      //Carrera jinete velocidad dragon - 1km/h por kilo vikingo
      //Para estos tres anteriores hambre vikingo +5%
      //(estos si tienen efecto)
    }
    "Postas" - {
      "Requerimiento Pesca" in {
        val vikingo: Vikingo = Vikingo(Stats(50, 10, 10))
        val requerimientoPesca: PesoMinimoALevantar = PesoMinimoALevantar(40)

        requerimientoPesca(vikingo) shouldBe true
      }

      "Vikingo mejor que otro en pesca" in {
        val vikingoCapo: Vikingo = Vikingo(Stats(80, 10, 10)) // peso/2 + barbarosidad * 2 = pesoTolerado = 60
        val vikingoChiquito: Vikingo = Vikingo(Stats(50, 10, 10)) // 45

        val postaPesca: Pesca = Pesca(40)

        vikingoCapo.esMejorQue(vikingoChiquito)(postaPesca) shouldBe true
      }
      "Pesca con varios participantes" in {
        val vikingoCapo: Vikingo = Vikingo(Stats(80, 10, 10)) // peso/2 + barbarosidad * 2 = pesoTolerado = 60
        val vikingoChiquito: Vikingo = Vikingo(Stats(50, 10, 10)) // 45
        val vikingoAlto: Vikingo = Vikingo(Stats(70, 10, 10)) // 55
        val vikingoNoParticipa: Vikingo = Vikingo(Stats(40, 10, 5)) // 30

        val postaPesca: Pesca = Pesca(40)

        postaPesca(List(vikingoAlto, vikingoCapo, vikingoChiquito, vikingoNoParticipa)) shouldBe List(vikingoCapo.aumentarHambre(5), vikingoAlto.aumentarHambre(5), vikingoChiquito.aumentarHambre(5))

      }

      "Pesca con varios participantes y un jinete" in {
        val vikingoCapo: Vikingo = Vikingo(Stats(80, 10, 10)) // peso/2 + barbarosidad * 2 = pesoTolerado = 60
        val vikingoChiquito: Vikingo = Vikingo(Stats(50, 10, 10)) // 45
        val vikingoAlto: Vikingo = Vikingo(Stats(70, 10, 10)) // 55
        val vikingoNoParticipa: Vikingo = Vikingo(Stats(40, 10, 5)) // 30
        val primoDeChimuelo: FuriaNocturna = FuriaNocturna(20, 600)

        val vikingoParticipaPorSerJinete: Jinete = vikingoNoParticipa.intentarMontarDragon(primoDeChimuelo).get

        val postaPesca: Pesca = Pesca(40)

        postaPesca(List(vikingoAlto, vikingoCapo, vikingoChiquito, vikingoParticipaPorSerJinete)) shouldBe List(vikingoNoParticipa.aumentarHambre(5),vikingoCapo.aumentarHambre(5), vikingoAlto.aumentarHambre(5), vikingoChiquito.aumentarHambre(5))

      }
      "Combate con varios participantes" in {
        val vikingoCapo: Vikingo = Vikingo(Stats(80, 10, 100)) // barbarosidad = 100
        val vikingoChiquito: Vikingo = Vikingo(Stats(50, 10, 10)) // 10
        val vikingoAlto: Vikingo = Vikingo(Stats(70, 10, 5), Some(Hacha)) // 35
        val vikingoNoParticipa: Vikingo = Vikingo(Stats(40, 10, 5)) // 5
        val primoDeChimuelo: FuriaNocturna = FuriaNocturna(20, 600)

        val vikingoNoParticipaYEsJinete: Jinete = vikingoNoParticipa.intentarMontarDragon(primoDeChimuelo).get // 35

        val postaCombate: Combate = Combate(8)

        postaCombate(List(vikingoAlto, vikingoCapo, vikingoChiquito, vikingoNoParticipaYEsJinete)) shouldBe List(vikingoCapo.aumentarHambre(10), vikingoAlto.aumentarHambre(10), vikingoChiquito.aumentarHambre(10))

      }

      "Combate con varios participantes y uno que no participa, entra por el item" in {
        val vikingoCapo: Vikingo = Vikingo(Stats(80, 10, 100)) // barbarosidad = 100
        val vikingoChiquito: Vikingo = Vikingo(Stats(50, 10, 10)) // 10
        val vikingoAlto: Vikingo = Vikingo(Stats(70, 10, 5), Some(Hacha)) // 35
        val vikingoNoParticipa: Vikingo = Vikingo(Stats(40, 10, 10)) // 10
        val primoDeChimuelo: FuriaNocturna = FuriaNocturna(20, 600)

        val vikingoParticipaPorSerJinete: Jinete = vikingoNoParticipa.intentarMontarDragon(primoDeChimuelo).get // 30

        val postaCombate: Combate = Combate(8)

         postaCombate(List(vikingoAlto, vikingoCapo, vikingoChiquito, vikingoParticipaPorSerJinete)) shouldBe List(vikingoCapo.aumentarHambre(10), vikingoAlto.aumentarHambre(10),vikingoNoParticipa.aumentarHambre(5), vikingoChiquito.aumentarHambre(10))

      }
      "Requerimiento Carrera con montura" in {
        val vikingo: Vikingo = Vikingo(Stats(50, 10, 10))
        val primoDeChimuelo: FuriaNocturna = FuriaNocturna(20, 260)
        val jinete: Jinete = vikingo.intentarMontarDragon(primoDeChimuelo).get

        val postaCarrera: Carrera = Carrera(10,true)
        CumpleConMontura(jinete) shouldBe true
        postaCarrera(List(vikingo, jinete)) shouldBe List(vikingo.aumentarHambre(5))
      }

      "Requerimiento Carrera sin montura requerida" in {
        val vikingo: Vikingo = Vikingo(Stats(1, 1, 10))
        val primoDeChimuelo: FuriaNocturna = FuriaNocturna(20, 10)
        val jinete: Jinete = vikingo.intentarMontarDragon(primoDeChimuelo).get // 150 - 1 = 149
        val vikingoCapo: Vikingo = Vikingo(Stats(80, 20, 100)) // velocidad = 20
        val vikingoChiquito: Vikingo = Vikingo(Stats(50, 10, 10)) // 10

        val postaCarrera: Carrera = Carrera(10,false)

        //primoDeChimuelo.velocidad shouldBe 150
        //jinete.velocidad shouldBe 149
        postaCarrera(List(jinete, vikingoCapo, vikingoChiquito)) shouldBe List(vikingo.aumentarHambre(5), vikingoCapo.aumentarHambre(10),vikingoChiquito.aumentarHambre(10))
      }

      //Pesca: Puede existir requerimiento de peso minimo a levantar para el participante
      //Combate: Debe tener al menos X grado de barbaridad o arma equipada
      //Carrera: Puede requerir uso de montura
      //(No pueden participar vikingos que luego de este, lleguen al 100% de hambre). Descartar vikingos no cumplan pre-requisitos. Efecto sobre participantes. Resultado de la posta
      //
      //Ganador posta
      //Varios participantes en posta
      //
      //mejor montura
      //torneo varios
      //reglas varias
    }

  }
}


