/**
  * Created by Eric on 17/06/2016.
  */

import org.junit.Assert.assertEquals
import org.junit.{Before, Test}

class Heroe_test {
  @Before
  val cascoVikingo = (unHeroe: Heroe) => unHeroe.stat.fuerza > 30

  @Test
  def evaluarHeroe() = {
    val item1 = new Item("Estada", 100, cascoVikingo)
    val estado = new Stat(20)
    val arturo = new Heroe("arturo", estado, List())

    assertEquals(arturo.inventory.length, 0)
    arturo.agregarItem(item1)
    assertEquals(arturo.inventory.length, 1)
//    val jorge = arturo.copy()
    assertEquals(arturo.stat.fuerza, 20)

  }
}
