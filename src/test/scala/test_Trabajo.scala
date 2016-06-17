import org.junit.Assert._
import org.junit.{Before, Test}

/**
  * Created by jon-VBox-w7 on 17/06/2016.
  */
class test_Trabajo {
  var unTrabajoGuerrero = Guerrero
  var unTrabajoMago = Mago
  var unTrabajoLadron = Ladron

  @Before
    val cascoVikingo = (unHeroe: Heroe) => unHeroe.stats.fuerza > 30


  @Test
  def test(): Unit = {
    assertEquals(10, unTrabajoGuerrero.hp)
  }

//  @Test
//  def heroeSinTrabajo(): Unit = {
//    val estado = new Stats(0,50,0,0)
//    val jon = new Heroe("jon", estado,null)
//    assertEquals(jon.stats.fuerza, 50)
//  }

//  @Test
//  def heroeConTrabajoGuerrero(): Unit = {
//    val estado = new Stats(0,50,0,0)
//    val jon = new Heroe("jon", estado,Guerrero)
//    assertEquals(jon.fuerza, 65)
//  }

//  @Test
//  def heroeConTrabajoGuerreroVikingo(): Unit = {
//    val estado = new Stats(0,50,0,0)
//    val jon = new Heroe("jon", estado,Guerrero)
//    val item1 = new Item("Estada", 100, cascoVikingo)
//    jon.agregarItem(item1)
//    assertEquals(jon.fuerza, 65)
//  }
}
