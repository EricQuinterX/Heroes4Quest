import org.junit.Assert._
import org.junit.{Before, Test}

class MisionTest {

  //Punto 3
  val condicion_facilidad_Pelear = (unEquipo:Equipo)=> true
  val condicion_facilidad_Forzar = (unEquipo:Equipo) =>true
  val condicion_facilidad_Robar  = (unEquipo:Equipo) =>unEquipo.obtenerLider().lider.get.trabajaDe(Ladron)


  val pelear_Condicion= (unHeroe:Heroe) => unHeroe.atributos().fuerza<20
  val pelear_Facilidad= (unEquipo:Equipo) =>   if (unEquipo.obtenerLider().lider.get.trabajaDe(Guerrero)) 20 else 10
  val pelear_Efecto   = (unHeroe:Heroe, unItem:Item) => unHeroe.pelearContraMonstruo


  val forzar_Condicion = (unHeroe :Heroe) => !unHeroe.trabajaDe(Mago) && !unHeroe.trabajaDe(Ladron)
  //val forzar_Facilidad = (unEquipo:Equipo) => unEquipo.mejorHeroeSegun().atributos().inteligencia + unEquipo.heroes.filter(_.trabajaDe(Ladron)).size *10
  val forzar_Efecto    = (unHeroe:Heroe, unItem:Item) => unHeroe.forzarPuerta()

  val robar_Condicion = (unHeroe :Heroe) => true
  //val robar_Facilidad = (unEquipo:Equipo) =>  if (unEquipo.obtenerLider().lider.get.trabajaDe(Ladron)) unHeroe.atributos().velocidad else -1
  val robar_Efecto    = (unHeroe:Heroe, unItem:Item) =>  unHeroe.robarTalisman(unItem)

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

    tarea_Pelear = new Tarea("Pelear",pelear_Condicion,pelear_Facilidad,condicion_facilidad_Pelear ,pelear_Efecto)
    //tarea_Forzar = new Tarea("Pelear",forzar_Condicion,forzar_Facilidad,condicion_facilidad_Forzar, forzar_Efecto)
    //tarea_Robar = new Tarea("Pelear",robar_Condicion,robar_Facilidad,condicion_facilidad_Robar, robar_Efecto)

    misionPelear = new Mision(List(tarea_Pelear),100)


  }

  //Test para probar una mision de pelear contra un mounstruo: 1
  //Desc: "reduce la vida de cualquier héroe con fuerza <20"
  @Test
  def pelearContraMonstruo_un_Guerrero(): Unit = {  //(10,15,0,-10)
    assertEquals(misionPelear.realizar_Mision(unGuerrero,cascoVikingo)._1.atributos().hp,45)
    //assertEquals(misionPelear.realizar_Mision(unGuerrero,cascoVikingo)._2,true)
  }

  @Test
  def pelearContraMonstruo_un_Debil(): Unit = {
    assertEquals(misionPelear.realizar_Mision(un_debil,cascoVikingo)._1.atributos().hp,1)
    //assertEquals(misionPelear.realizar_Mision(un_debil,cascoVikingo)._2,false)
  }

}
