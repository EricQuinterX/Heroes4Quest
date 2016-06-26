import org.junit.Assert._
import org.junit.{Before, Test}

class EquipoTest {

  //Variables que se usan en los test
  var equipoVacio  :Equipo = _
  var losSinTrabajo:Equipo = _
  var sinLider:Equipo = _

  @Before
  def initialize() = {
    val juan = new Heroe(new Stats(10,10,10,10))
    val juan2 = new Heroe(new Stats(20,20,20,20))
    val juan3 = new Heroe(new Stats(30,30,30,30))

    losSinTrabajo = new Equipo("Los Sin Trabajo", List(juan, juan2, juan3),100)
    equipoVacio = new Equipo("Equipo Vacio", List(),100)

  }


  //Test para probar el mejor heroe segun un criterio de un Equipo: 2
  @Test
  def mejorHeroeSegun(): Unit = {
    val auxHeroe:Option[Heroe] = losSinTrabajo.mejorHeroeSegun(_.stats.fuerza)
    assertEquals(auxHeroe.get.stats.fuerza, 30)
  }

  @Test
  def mejorHeroeSegunEquipoVacio(): Unit = {
    val auxHeroe = equipoVacio.mejorHeroeSegun(_.stats.fuerza)
    assertEquals(auxHeroe, None)
  }

  //Test para probar el pozo de oro de un Equipo: 1
  @Test
   def pozoDeOro(): Unit = {
     assertEquals(losSinTrabajo.pozoDeOro, 100)
   }


  //Test para probar el obtiene miembro de un Equipo: 1
   @Test
   def agregarMiembroAlEquipo(): Unit = {
     val juan4 = new Heroe(new Stats(40,40,40,40))
     val losSinTrabajo2= losSinTrabajo.obtieneMiembro(juan4)
     assertEquals(losSinTrabajo2.heroes.size, 4)
   }

  //Test para probar el reemplazar miembro de un Equipo: 1
   @Test
   def reemplazarMiembroDeUnEquipo(): Unit = {
     val juanViejo = new Heroe(new Stats(40,40,40,40))
     val losSinTrabajo2= losSinTrabajo.obtieneMiembro(juanViejo)
     val juanNuevo = new Heroe(new Stats(1,1,1,1))

     val losSinTrabajo3 = losSinTrabajo2.reemplazarMiembro(juanNuevo, juanViejo)
     assertEquals(losSinTrabajo3.heroes.contains(juanNuevo), true)
     assertEquals(losSinTrabajo3.heroes.contains(juanViejo), false)
   }

  //Test para probar el metodo Lider de un Equipo: 3
  @Test
  def liderDeUnEquipo(): Unit = {
    val juan4 = new Heroe(new Stats(40,40,40,40))
    val losSinTrabajo2= losSinTrabajo.obtieneMiembro(juan4)
    assertEquals(losSinTrabajo2.lider().get, juan4)
  }


  @Test
  def sinlider_PorEquipoVacio(): Unit = {
    assertEquals(losSinTrabajo.lider().get, None)
  }

  @Test
  def sinliderDeUnEquipo(): Unit = {
    val juan3b = new Heroe(new Stats(30,30,30,30))
    val losSinTrabajo2= losSinTrabajo.obtieneMiembro(juan3b)
    assertEquals(losSinTrabajo2.lider().get, None)
  }

//  @Test
//  def obtenerItem(): Unit = {
//    val jonny = new Heroe(new Stats(10,10,10,10)) //(20,25,10,1)
//    val jonas = new Heroe(new Stats(20,20,1,1)) // (20,1,1,21)
//    val lagartos = new Equipo("Los Lagartos", List(jonny, jonas),0)
//
//    val condicion_cascoVikingo = (h: Heroe) => h.stats.fuerza > 30
//    val efecto_cascoVikingo    = (h: Heroe) => h.stats.subirHp(10)
//    val cascoVikingo = new Item("Casco Vikingo", 200, Cabeza, efecto_cascoVikingo, condicion_cascoVikingo)
//
//    val lagartos2 = lagartos.obtieneItem(cascoVikingo)
//    assertEquals(lagartos2., )
////    val matias = new Heroe(new Stats(30,30,5,5), Some(Guerrero)) // (25,10,15,5)
////    val lagartos2 = lagartos.reemplazarMiembro(matias, jonny)
////    assertEquals(lagartos2.lider().get, matias)
//    //assertEquals(lagartos.heroes.size, 2)
//  }
}
