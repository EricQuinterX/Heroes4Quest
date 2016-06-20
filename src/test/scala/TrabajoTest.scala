import org.junit.Assert._
import org.junit.Test

class TrabajoTest {

  @Test
  def asignarTrabajoHeroe(): Unit = {
    val juan: Heroe = new Heroe(new Stats(100,50,30,10))
    juan.adquirirTrabajo(Guerrero)
    assertEquals(juan.atributos().fuerza, 65)
    assertEquals(juan.stats.fuerza, 50)
  }

  @Test
  def heroeDejaTrabajo(): Unit = {
    val gago = new Heroe(new Stats(10,10,10,10))
    gago.adquirirTrabajo(Guerrero) // (10,15,0,-10)
    assertEquals(gago.atributos(), new Stats(20,25,10,1)) // (20,25,10,1)
    gago.dejarTrabajo()
    assertEquals(gago.atributos(), new Stats(10,10,10,10)) // (10,10,10,10)
  }

  @Test
  def heroeCambiaDeTrabajoConAtributoNegativo(): Unit = {
    val richi = new Heroe(new Stats(100,5,5,5))
    richi.adquirirTrabajo(Mago) // (0,-20,0,20)
    assertEquals(richi.atributos(), new Stats(100,1,5,25))
    richi.adquirirTrabajo(Ladron) // (-5,0,10,0)
    assertEquals(richi.atributos(), new Stats(95,5,15,5))
  }

}
