/**
  * Created by Eric on 17/06/2016.
  */

import java.util

import org.junit.Assert.assertEquals
import org.junit.{Before, Test}

class Heroe_test {
  @Before
  val cascoVikingo = (unHeroe: Heroe) => unHeroe.stats.fuerza > 30

  @Test
  def evaluarHeroe() = {
    val item1 = new Item("Estada", 100, cascoVikingo)
    val estado = new Stats(0,50,0,0)
    val arturo = new Heroe("arturo", estado)

    arturo.agregarItem(item1)

    assertEquals(arturo.inventory.size, 1)
    assertEquals(arturo.stats.fuerza, 50)

  }
}
