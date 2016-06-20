import org.junit.Assert._
import org.junit.Test

class EquipoTest {


  @Test
  def liderDeUnEquipo(): Unit = {
    val jonny = new Heroe(new Stats(10,10,10,10), Some(Guerrero)) //(20,25,10,1)
    val jonas = new Heroe(new Stats(20,20,1,1), Some(Mago)) // (20,1,1,21)
    val matias = new Heroe(new Stats(30,10,5,5), Some(Ladron)) // (25,10,15,5)
    val losDragones = new Equipo("Los Dragones", List(jonny, jonas, matias))
    assertEquals(losDragones.lider().get, jonny)
  }

  @Test
  def reemplazarMiembroDeUnEquipo(): Unit = {
    val jonny = new Heroe(new Stats(10,10,10,10), Some(Guerrero)) //(20,25,10,1)
    val jonas = new Heroe(new Stats(20,20,1,1), Some(Mago)) // (20,1,1,21)
    val lagartos = new Equipo("Los Lagartos", List(jonny, jonas))
    assertEquals(lagartos.lider().get, jonny)
    assertEquals(lagartos.heroes.size, 2)
    val matias = new Heroe(new Stats(30,10,5,5), Some(Ladron)) // (25,10,15,5)
    lagartos.reemplazarMiembro(matias, jonny)
    assertEquals(lagartos.lider().get, jonas)
    assertEquals(lagartos.heroes.size, 2)
  }

}
