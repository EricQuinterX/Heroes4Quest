import scala.collection.mutable.Set
import org.junit.Assert.assertEquals
import org.junit.{Before, Test}

class Heroe_test {
 // @Before
  //val cascoVikingo = (unHeroe: Heroe) => unHeroe.stats.fuerza > 30

  @Test
  def evaluarHeroe() = {
    val unStats:Stats=new Stats(15,50,15,15)
    val lista:Set[Item]=Set[Item]()
    val unInventario:Inventario=new Inventario(lista)
    val Alvaro:Heroe=new Heroe(unStats,null, unInventario)
    val casco:Item= CascoVikingo()
    Alvaro.agregarItem(casco)
    assertEquals(Alvaro.hp, 25)

  }
}
