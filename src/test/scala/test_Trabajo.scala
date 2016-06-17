import org.junit.Assert._
import org.junit.{Before, Test}

/**
  * Created by jon-VBox-w7 on 17/06/2016.
  */
class test_Trabajo {
//  var hp:Int=
//  var fuerza:Int
//  var velocidad:Int
//  var inteligencia:Int
  var unTrabajoGuerrero = Guerrero
  var unTrabajoMago = Mago
  var unTrabajoLadron = Ladron
  @Before
  def before(): Unit = {

  }

  @Test
  def test(): Unit = {
    assertEquals(10, unTrabajoGuerrero.hp)
  }
}
