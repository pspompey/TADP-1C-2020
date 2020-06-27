import Participante._
import Dragon._
import Postas._
import Requerimiento._
import Torneos._
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
      "Mejor Montura para carrera puede montar un Dragon" in {
        val vikingo: Vikingo = Vikingo(Stats(1, 1, 200))
        val primoDeChimuelo: FuriaNocturna = FuriaNocturna(20, 10)
        val nadder: NadderMortifero = NadderMortifero(20)


        val jinete: Jinete = vikingo.intentarMontarDragon(primoDeChimuelo).get // 150 - 1 = 149

        val postaCarrera: Carrera = Carrera(10,false)

        //primoDeChimuelo.velocidad shouldBe 150
        //jinete.velocidad shouldBe 149
        vikingo.mejorMontura(List(primoDeChimuelo,nadder))(postaCarrera) shouldBe jinete

      }
      "Mejor Montura para carrera, le conviene participar solo" in {
        val vikingo: Vikingo = Vikingo(Stats(1, 150, 200))  // Vel vikingo 150
        val primoDeChimuelo: FuriaNocturna = FuriaNocturna(20, 10)
        val nadder: NadderMortifero = NadderMortifero(20)


        val jinete: Jinete = vikingo.intentarMontarDragon(primoDeChimuelo).get // vel jin 150 - 1 = 149

        val postaCarrera: Carrera = Carrera(10,false)

        //primoDeChimuelo.velocidad shouldBe 150
        //jinete.velocidad shouldBe 149
        vikingo.mejorMontura(List(primoDeChimuelo,nadder))(postaCarrera) shouldBe vikingo

      }
      "Mejor Montura para carrera, le conviene participar solo. Pero el req de la posta es ser jinete" in {
        val vikingo: Vikingo = Vikingo(Stats(1, 150, 200))  // Vel vikingo 150
        val primoDeChimuelo: FuriaNocturna = FuriaNocturna(20, 10)
        val nadder: NadderMortifero = NadderMortifero(20)


        val jinete: Jinete = vikingo.intentarMontarDragon(primoDeChimuelo).get // vel jin 150 - 1 = 149

        val postaCarrera: Carrera = Carrera(10,true)


        vikingo.mejorMontura(List(primoDeChimuelo,nadder))(postaCarrera) shouldBe jinete

      }


      "Mejor Montura para carrera entre dos dragones es un dragon" in {
        val vikingo: Vikingo = Vikingo(Stats(1, 150, 200))  // Vel vikingo 150
        val primoDeChimuelo: FuriaNocturna = FuriaNocturna(20, 10)
        val primoDeChimueloFlash: FuriaNocturna = FuriaNocturna(20, 9)


        val jinete: Jinete = vikingo.intentarMontarDragon(primoDeChimuelo).get // vel jin 150 - 1 = 149
        val jineteFlash: Jinete = vikingo.intentarMontarDragon(primoDeChimueloFlash).get // 153 - 1 = 152

        val postaCarrera: Carrera = Carrera(10,false)


        vikingo.mejorMontura(List(primoDeChimuelo,primoDeChimueloFlash ))(postaCarrera) shouldBe jineteFlash

      }
      "Mejor Montura para combate entre dos dragones uno no lo puede montar" in {
        val vikingo: Vikingo = Vikingo(Stats(1, 150, 200))  // Daño vik 200
        val primoDeChimuelo: FuriaNocturna = FuriaNocturna(20, 10) // Daño Drag 20
        val nadder: NadderMortifero = NadderMortifero(200) // Daño 150


        val jinete: Jinete = vikingo.intentarMontarDragon(primoDeChimuelo).get // daño 220
        //val jineteNadder: Jinete = vikingo.intentarMontarDragon(nadder).get // No puede montar nadder

        val postaCombate: Combate = Combate(10)

        vikingo.mejorMontura(List(primoDeChimuelo,nadder ))(postaCombate) shouldBe jinete

      }
      "Mejor Montura para combate entre dos dragones que puede montar" in {
        val vikingo: Vikingo = Vikingo(Stats(1, 150, 100))  // Daño vik 100
        val primoDeChimuelo: FuriaNocturna = FuriaNocturna(20, 10) // Daño Drag 20
        val nadder: NadderMortifero = NadderMortifero(200) // Daño 150


        val jinete: Jinete = vikingo.intentarMontarDragon(primoDeChimuelo).get // daño 120
        val jineteNadder: Jinete = vikingo.intentarMontarDragon(nadder).get // daño 250

        val postaCombate: Combate = Combate(10)

        vikingo.mejorMontura(List(primoDeChimuelo,nadder ))(postaCombate) shouldBe jineteNadder

      }

      "Torneo estandar 2 participantes 1 prueba" in {
        val vikingo: Vikingo = Vikingo(Stats(1, 150, 100))  // Daño vik 100
        val vikingoNader: Vikingo = Vikingo(Stats(1, 100, 100))  // Daño vik 100
        val primoDeChimuelo: FuriaNocturna = FuriaNocturna(20, 10) // Daño Drag 20
        val nadder: NadderMortifero = NadderMortifero(200) // Daño 150


        val jinete: Jinete = vikingo.intentarMontarDragon(primoDeChimuelo).get // daño 120
        val jineteNadder: Jinete = vikingoNader.intentarMontarDragon(nadder).get // daño 250

        val postaCombate: Combate = Combate(10)
        val estandar: Estandar = Estandar()
        val torneo: Torneo = Torneo(Estandar(),List(postaCombate),List(vikingoNader,vikingo),List(nadder,primoDeChimuelo))
        torneo.seleccion(postaCombate, torneo.vikingos) shouldBe List(jineteNadder,jinete)
        torneo.ganador() shouldBe Some(jineteNadder.participar(postaCombate))
        //torneo.ganador() shouldBe Some(jineteNadder.participar(postaCombate))


      }
      "Torneo estandar 4 participantes 2 prueba" in {
        val vikingo: Vikingo = Vikingo(Stats(1, 150, 101))  // Daño vik 100
        val vikingo1: Vikingo = Vikingo(Stats(1, 149, 100))  // Daño vik 100
        val vikingoNader: Vikingo = Vikingo(Stats(1, 100, 101))  // Daño vik 101
        val vikingoNader1: Vikingo = Vikingo(Stats(1, 99, 100))  // Daño vik 100
        val primoDeChimuelo: FuriaNocturna = FuriaNocturna(20, 10) // Daño Drag 20
        val primoDeChimuelo1: FuriaNocturna = FuriaNocturna(19, 10) // Daño Drag 19
        val nadder: NadderMortifero = NadderMortifero(200) // Daño 150
        val nadder1: NadderMortifero = NadderMortifero(199) // Daño 150


        val jinete: Jinete = vikingo.intentarMontarDragon(primoDeChimuelo).get // daño 121
        val jinete1: Jinete = vikingo1.intentarMontarDragon(primoDeChimuelo1).get // daño 120
        val jineteNadder: Jinete = vikingoNader.intentarMontarDragon(nadder).get // daño 251
        val jineteNadder1: Jinete = vikingoNader1.intentarMontarDragon(nadder1).get // daño 250

        val postaCombate: Combate = Combate(10)
        val estandar: Estandar = Estandar()
        val torneo: Torneo = Torneo(Estandar(),List(postaCombate, postaCombate),List(vikingoNader, vikingoNader1,vikingo, vikingo1),List(nadder,primoDeChimuelo, nadder1,primoDeChimuelo1))

        torneo.ganador() shouldBe Some(jineteNadder.participar(postaCombate).aumentarHambre(5))
        //torneo.ganador() shouldBe Some(jineteNadder.participar(postaCombate))


      }
      "Torneo estandar 4 participantes 3 prueba. En la ultima ni hace falta ver si puede pasar" in {
        val vikingo: Vikingo = Vikingo(Stats(1, 150, 101))  // Daño vik 100
        val vikingo1: Vikingo = Vikingo(Stats(1, 149, 100))  // Daño vik 100
        val vikingoNader: Vikingo = Vikingo(Stats(1, 100, 101))  // Daño vik 101
        val vikingoNader1: Vikingo = Vikingo(Stats(1, 99, 100))  // Daño vik 100
        val primoDeChimuelo: FuriaNocturna = FuriaNocturna(20, 10) // Daño Drag 20
        val primoDeChimuelo1: FuriaNocturna = FuriaNocturna(19, 10) // Daño Drag 19
        val nadder: NadderMortifero = NadderMortifero(200) // Daño 150
        val nadder1: NadderMortifero = NadderMortifero(199) // Daño 150


        val jinete: Jinete = vikingo.intentarMontarDragon(primoDeChimuelo).get // daño 121
        val jinete1: Jinete = vikingo1.intentarMontarDragon(primoDeChimuelo1).get // daño 120
        val jineteNadder: Jinete = vikingoNader.intentarMontarDragon(nadder).get // daño 251
        val jineteNadder1: Jinete = vikingoNader1.intentarMontarDragon(nadder1).get // daño 250

        val postaCombate: Combate = Combate(10)
        val estandar: Estandar = Estandar()
        val torneo: Torneo = Torneo(Estandar(),List(postaCombate, postaCombate,postaCombate),List(vikingoNader, vikingoNader1,vikingo, vikingo1),List(nadder,primoDeChimuelo, nadder1,primoDeChimuelo1))

        torneo.ganador() shouldBe Some(jineteNadder.participar(postaCombate).aumentarHambre(5))



      }
      //Pesca: Puede existir requerimiento de peso minimo a levantar para el participante
      //Combate: Debe tener al menos X grado de barbaridad o arma equipada
      //Carrera: Puede requerir uso de montura
      //(No pueden participar vikingos que luego de este, lleguen al 100% de hambre). Descartar vikingos no cumplan pre-requisitos. Efecto sobre participantes. Resultado de la posta
      //
      //Ganador posta
      //Varios participantes en posta
      //torneo varios
      //reglas varias
    }

  }
}


