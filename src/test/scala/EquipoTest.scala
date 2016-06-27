import org.junit.Assert._
import org.junit.{Before, Test}

class EquipoTest {

  //Variables que se usan en los test
  var equipoVacio  :Equipo = _
  var losSinTrabajo:Equipo = _
  var sinLider:Equipo = _

  //para los items
  val condicion_cascoVikingo = (h: Heroe) => h.stats.fuerza > 30
  val efecto_cascoVikingo    = (h: Heroe) => {
    val s = h.stats
    s.setear(s.copy(hp = s.hp+10))
  }

  val condicion_arcoViejo = (h: Heroe) => h.stats.hp > 30
  val efecto_arcoViejo    = (h: Heroe) => {
    val s = h.stats
    s.setear(s.copy(fuerza = s.fuerza+2))
  }


  val condicion_palitoMagico = (h: Heroe) => h.trabajaDe(Mago) || (h.trabajaDe(Ladron) && h.stats.inteligencia > 30)
  val efecto_palitoMagico    = (h: Heroe) => {
    val s = h.stats
    s.setear(s.copy(inteligencia = s.inteligencia+20))
  }

  @Before
  def initialize() = {
    val juan = new Heroe(new Stats(10,10,10,10))
    val juan2 = new Heroe(new Stats(20,20,20,20))
    val juan3 = new Heroe(new Stats(25,25,25,25))

    losSinTrabajo = new Equipo("Los Sin Trabajo", List(juan, juan2, juan3),100)
    equipoVacio = new Equipo("Equipo Vacio", Nil,100)

  }


  //Test para probar el mejor heroe segun un criterio de un Equipo: 2
  @Test
  def mejorHeroeSegun(): Unit = {
    val auxHeroe:Option[Heroe] = losSinTrabajo.mejorHeroeSegun(_.stats.fuerza)
    assertEquals(auxHeroe.get.stats.fuerza, 25)
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
    assertEquals(losSinTrabajo3.heroes.size, 4)
   }

  //Test para probar el metodo Lider de un Equipo: 4
  @Test
  def liderDeUnEquipo_SinTrabajo(): Unit = {
    val juan4 = new Heroe(new Stats(40,40,40,40))
    val losSinTrabajo2= losSinTrabajo.obtieneMiembro(juan4)
    assertEquals(losSinTrabajo2.obtenerLider().lider,Some(juan4))
  }


  @Test
  def liderDeUnEquipo_ConTrabajo_Guerrero(): Unit = {
    val juanGuerrero = new Heroe(new Stats(30,30,30,30),Some(Guerrero))
    val losSinTrabajo2= losSinTrabajo.obtieneMiembro(juanGuerrero)
    assertEquals(losSinTrabajo2.obtenerLider().lider,Some(juanGuerrero))
  }


  @Test
  def sinlider_PorEquipoVacio(): Unit = {
    assertEquals(equipoVacio.obtenerLider().lider, None)
  }

  @Test
  def sinliderDeUnEquipo(): Unit = {
    val juan3b = new Heroe(new Stats(25,25,25,25))
    val losSinTrabajo2= losSinTrabajo.obtieneMiembro(juan3b)
    assertEquals(losSinTrabajo2.obtenerLider().lider, None)
  }

  //Test para probar el obtener item de un Equipo: 4
  @Test
  def obtenerItem_CumpleConciconUnHeroe(): Unit = {

    val juanGuerrero = new Heroe(new Stats(40,60,30,30))
    val losSinTrabajo2= losSinTrabajo.obtieneMiembro(juanGuerrero)

    val arcoViejo = new Item("Arco Viejo", 200, Mano(2), efecto_arcoViejo, condicion_arcoViejo)
    assertEquals(juanGuerrero.equiparseItem(arcoViejo).atributos().fuerza, 62)
    val losSinTrabajo3 = losSinTrabajo2.obtieneItem(arcoViejo)
    val losSinTrabajo4 = losSinTrabajo3.obtenerLider()
    assertEquals(losSinTrabajo4.pozoDeOro, 100)
    assertEquals(losSinTrabajo4.lider.get.atributos().fuerza,62)
  }

  @Test
  def obtenerItem_NoCumpleCondicion(): Unit = {
    val cascoVikingo = new Item("Casco Vikingo", 200, Cabeza, efecto_cascoVikingo, condicion_cascoVikingo)

    val losSinTrabajo2 = losSinTrabajo.obtieneItem(cascoVikingo)
    assertEquals(losSinTrabajo2.pozoDeOro, 300)
    assertEquals(losSinTrabajo2.obtenerLider().lider.get.stats.hp, 25)
  }

  @Test
  def obtenerItem_NoCumpleCondicion_Por_EquipoVacio(): Unit = {
    val cascoVikingo = new Item("Casco Vikingo", 200, Cabeza, efecto_cascoVikingo, condicion_cascoVikingo)
    val equipoVacio2 = equipoVacio.obtieneItem(cascoVikingo)
    assertEquals(equipoVacio2.pozoDeOro, 300)
  }

  @Test
  def obtenerItem_CumpleCondicion_Con_Mago(): Unit = {

    val mago = new Heroe(new Stats(75,50,50,100),Some(Mago))//(0,-20,0,20)
    val losSinTrabajo2= losSinTrabajo.obtieneMiembro(mago)
    val palitoMagico = new Item("Palito Magico", 150, Mano(1), efecto_palitoMagico, condicion_palitoMagico)

    val losSinTrabajo3 = losSinTrabajo2.obtieneItem(palitoMagico) // (0,0,0,20)
    assertEquals(mago.equiparseItem(palitoMagico).atributos().inteligencia, 140)
    assertEquals(mago.equiparseItem(palitoMagico).atributos().fuerza, 30)
    assertEquals(losSinTrabajo3.obtenerLider().lider.get.atributos().hp,75)
  }
}
