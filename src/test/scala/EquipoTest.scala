import org.junit.Assert._
import org.junit.{Test}

class EquipoTest {


//  @Test
//  def mejorHeroeSegun(): Unit = {
//    val jonny = new Heroe(new Stats(10,10,10,10), Some(Guerrero)) //(20,25,10,1)
//    val jonas = new Heroe(new Stats(20,20,1,1), Some(Mago)) // (20,1,1,21)
//    val matias = new Heroe(new Stats(30,15,5,5), Some(Ladron)) // (25,10,15,5)
//    val losDragones = new Equipo("Los Dragones", List(jonny, jonas, matias),100)
//
//    val auxHeroe = losDragones.mejorHeroeSegun(_.stats.fuerza)
//    assertEquals(auxHeroe.stats.fuerza, 20)
//  }

  @Test
  def mejorHeroeSegunEquipoVacio(): Unit = {
    val losDragones = new Equipo("Los Dragones", List(),100)
    val auxHeroe = losDragones.mejorHeroeSegun(_.stats.fuerza)

    assertEquals(auxHeroe, None)
  }

    @Test
    def pozoDeOro(): Unit = {
      val jonny = new Heroe(new Stats(10,10,10,10), Some(Guerrero)) //(20,25,10,1)
      val jonas = new Heroe(new Stats(20,20,1,1), Some(Mago)) // (20,1,1,21)
      val matias = new Heroe(new Stats(30,10,5,5), Some(Ladron)) // (25,10,15,5)
      val losDragones = new Equipo("Los Dragones", List(jonny, jonas, matias),100)

      assertEquals(losDragones.pozoDeOro, 100)
    }


    @Test
    def liderDeUnEquipo(): Unit = {
      val jonny = new Heroe(new Stats(10,10,10,10), Some(Guerrero)) //(20,25,10,1)
      val jonas = new Heroe(new Stats(20,20,1,1), Some(Mago)) // (20,1,1,21)
      val matias = new Heroe(new Stats(30,10,5,5), Some(Ladron)) // (25,10,15,5)
      val losDragones = new Equipo("Los Dragones", List(jonny, jonas, matias),0)
      assertEquals(losDragones.lider().get, jonny)
    }

    @Test
    def reemplazarMiembroDeUnEquipo(): Unit = {
      val jonny = new Heroe(new Stats(10,10,10,10), Some(Guerrero)) //(20,25,10,1)
      val jonas = new Heroe(new Stats(20,20,1,1), Some(Mago)) // (20,1,1,21)
      val lagartos = new Equipo("Los Lagartos", List(jonny, jonas),0)
      assertEquals(lagartos.lider().get, jonny)
      assertEquals(lagartos.heroes.size, 2)
      val matias = new Heroe(new Stats(30,30,5,5), Some(Guerrero)) // (25,10,15,5)
      val lagartos2 = lagartos.reemplazarMiembro(matias, jonny)
      assertEquals(lagartos2.lider().get, matias)
      //assertEquals(lagartos.heroes.size, 2)
    }
}
