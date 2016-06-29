import org.junit.Assert._
import org.junit.{Before, Test}

class MisionTest {

  //Punto 3
  val condicion_facilidad_Pelear = (unEquipo:Equipo)=> true
  val condicion_facilidad_Forzar = (unEquipo:Equipo) =>true
  val condicion_facilidad_Robar  = (unEquipo:Equipo) =>unEquipo.obtenerLider().lider.get.trabajaDe(Ladron)

  val facilidad_Valor_Pelear = (unHeroe:Heroe,unEquipo:Equipo) =>   if (unEquipo.obtenerLider().lider.get.trabajaDe(Guerrero)) 20 else 10
  val facilidad_Valor_Forzar= (unHeroe:Heroe,unEquipo:Equipo) => unHeroe.atributos().inteligencia + unEquipo.heroes.filter(_.trabajaDe(Ladron)).size *10
  val facilidad_Valor_Robar = (unHeroe:Heroe,unEquipo:Equipo) =>  if (unEquipo.obtenerLider().lider.get.trabajaDe(Ladron)) unHeroe.atributos().velocidad else -1


  val pelear_Efecto = (unHeroe:Heroe, unItem:Item) => if (unHeroe.atributos().fuerza<20) unHeroe.pelearContraMonstruo else unHeroe
  val forzar_Efecto = (unHeroe:Heroe, unItem:Item) => if(!unHeroe.trabajaDe(Mago) && !unHeroe.trabajaDe(Ladron)) unHeroe.forzarPuerta() else unHeroe
  val robar_Efecto  = (unHeroe:Heroe, unItem:Item) =>  unHeroe.robarTalisman(unItem)

  var unLadron:Heroe = _
  var unGuerrero:Heroe = _
  var unMago:Heroe = _
  var unSinTrabajo:Heroe = _
  var un_debil:Heroe = _


  var losSinTrabajo:Equipo = _

  var tarea_Pelear:Tarea = _
  var tarea_Forzar:Tarea = _
  var tarea_Robar:Tarea = _

  var misionPelear:Mision = _


  var  cascoVikingo:Item = _
  val condicion_cascoVikingo = (h: Heroe) => h.stats.fuerza > 30
  val efecto_cascoVikingo    = (h: Heroe) => {
    val s = h.stats
    s.setear(s.copy(hp = s.hp+10))
  }

  @Before
  def initialize() = {
    unLadron = new Heroe(new Stats(25,25,25,25),Some(Ladron))
    unGuerrero = new Heroe(new Stats(35,35,35,35),Some(Guerrero))
    unMago = new Heroe(new Stats(25,25,25,25),Some(Mago))
    unSinTrabajo = new Heroe(new Stats(25,25,25,25))
    un_debil = new Heroe(new Stats(10,10,10,10))

    losSinTrabajo = new Equipo("Los Sin Trabajo", List(unSinTrabajo),100)

    val facilidadDePelear = new Facilidad(condicion_facilidad_Pelear,facilidad_Valor_Pelear)
    val facilidadDeRobar = new Facilidad(condicion_facilidad_Robar,facilidad_Valor_Forzar)
    val facilidadDeForzar = new Facilidad(condicion_facilidad_Forzar,facilidad_Valor_Robar)

    tarea_Pelear= new Tarea("Pelear",pelear_Efecto,facilidadDePelear,cascoVikingo)
    tarea_Forzar= new Tarea("Pelear",forzar_Efecto,facilidadDeForzar,cascoVikingo)
    tarea_Robar = new Tarea("Pelear",robar_Efecto,facilidadDeRobar,cascoVikingo)

    misionPelear = new Mision(List(tarea_Pelear),100)


  }

  //Test para probar una mision de pelear contra un mounstruo: 1
  //Desc: "reduce la vida de cualquier héroe con fuerza <20"
  @Test
  def pelearContraMonstruo_un_Guerrero(): Unit = {  //(10,15,0,-10)
    assertEquals(misionPelear.realizar_Mision(losSinTrabajo),45)
  }
//
//  @Test
//  def pelearContraMonstruo_un_Debil(): Unit = {
//    assertEquals(misionPelear.realizar_Mision(un_debil,cascoVikingo)._1.atributos().hp,1)
//    //assertEquals(misionPelear.realizar_Mision(un_debil,cascoVikingo)._2,false)
//  }

}
