import Model._
import org.scalatest.{FreeSpec, Matchers}
import scala.util.Try

class ProjectSpec extends FreeSpec with Matchers {

  "El vikingo y su dragón" - {
      val vikingo = Vikingo(Atributos(peso = 100, velocidad = 20, barbarosidad = 20, danio = 10), 10, List[Item]())
      val unDragon = FuriaNocturna(danio = 100, peso = 1000, listaDeRequerimientos = None)
      val otroDragon = FuriaNocturna(danio = 100, peso = 100, listaDeRequerimientos = None)

      val jineteExitoso: Try[Jinete] = vikingo.montar(unDragon)
      val jineteNoExitoso: Try[Jinete] = vikingo.montar(otroDragon)

      "jinete exitoso" in {
        assert(jineteExitoso.isSuccess)
      }

      "jinete no exitoso" in {
        jineteNoExitoso.failed.get shouldBe NoPuedeMontarElDragon
      }

  }
}
