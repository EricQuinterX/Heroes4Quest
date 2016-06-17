import org.junit.Assert._
import org.junit.{Before, Test}

/**
  * Created by jon-VBox-w7 on 17/06/2016.
  */
class test_stats {
  var unStats: Stats = null
  var hp:Int=100
  var fuerza:Int=20
  var velocidad:Int=10
  var inteligencia:Int=5

  @Before
  def before(): Unit = {
    unStats = new Stats(hp,fuerza,velocidad,inteligencia)
  }

  @Test
  def test(): Unit = {
    assertEquals(100, unStats.hp)
    assertEquals(20, unStats.fuerza)
    assertEquals(10, unStats.velocidad)
    assertEquals(5, unStats.inteligencia)
  }
}
