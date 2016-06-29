import org.junit.Assert._
import org.junit.{Before, Test}

class TareaTest {

  val pelearContraMonstruo = (unHeroe:Heroe) => if (unHeroe.atributos().fuerza<20) unHeroe.pelearContraMonstruo() else unHeroe
  val facilidadPelearContraMonstruo= (unEquipo:Equipo) =>  unEquipo.facilidadPelearContraMonstruo()


  val forzarPuerta = (unHeroe :Heroe) => if (!unHeroe.trabajaDe(Mago) && !unHeroe.trabajaDe(Ladron)) unHeroe.forzarPuerta() else unHeroe
  val facilidadForzarPuerta = (unHeroe:Heroe,unEquipo:Equipo) =>  unEquipo.facilidadForzarPuerta(unHeroe)

  val robarTalisman = (unHeroe:Heroe, unItem:Item) =>  unHeroe.robarTalisman(unItem)
  val facilidadRobarTalisman = (unHeroe:Heroe,unEquipo:Equipo) =>  unEquipo.facilidadRobarTalisman(unHeroe)

  val condicion_tareaDificil = (e: Equipo) => e.heroes.size >= 2
  val facilidad_tareaDificil = (h: Heroe) => h.atributos().principal(h.trabajo)
  val efecto_tareaDificl = (h: Heroe, e: Equipo) => {
    val s = h.stats
    val h1 = h.copy(stats = s.copy(hp = s.hp + 10))
    val newTeam = e.reemplazarMiembro(h1, h)
    (h1, newTeam)
  }

  var equipoVacio  :Equipo = _
  var losSinTrabajo:Equipo = _
  var losMagicos:Equipo = _
  var losLadrones:Equipo = _
  var losDosLadrones:Equipo = _
  var losGuerreros:Equipo = _
  var losVeganos: Equipo = _




  var unLadron:Heroe = _
  var unGuerrero:Heroe = _
  var unMago:Heroe = _
  var unSinTrabajo:Heroe = _
  var unoDe_Treinta_Cinco:Heroe = _
  var un_debil:Heroe = _
  var brocoli: Heroe = _
  var peregil: Heroe = _
  var repollo: Heroe = _



  var  cascoVikingo:Item = _
  val condicion_cascoVikingo = (h: Heroe) => h.stats.fuerza > 30
  val efecto_cascoVikingo    = (h: Heroe) => {
    val s = h.stats
    s.setear(s.copy(hp = s.hp+10))
  }


  var tareaDificil: Tarea = _
  var elRegresoDeBroly: Tarea = _


  @Before
  def initialize() = {
    unLadron = new Heroe(new Stats(25,25,25,25),Some(Ladron))
    unGuerrero = new Heroe(new Stats(25,25,25,25),Some(Guerrero))
    unMago = new Heroe(new Stats(25,25,25,25),Some(Mago))
    unSinTrabajo = new Heroe(new Stats(25,25,25,25))
    unoDe_Treinta_Cinco = new Heroe(new Stats(35,35,35,35))
    un_debil = new Heroe(new Stats(1,1,1,1))
    brocoli = new Heroe(Stats(40,19,48,99))
    peregil = new Heroe(Stats(60,55,62,94))
    repollo = new Heroe(Stats(60,95,30,10))


    losSinTrabajo = new Equipo("Los Sin Trabajo", List(unSinTrabajo, unoDe_Treinta_Cinco),100)
    losMagicos = new Equipo("Las Magicos", List(un_debil, unMago),100)
    losLadrones = new Equipo("Las Magicos", List(un_debil, unLadron),100)
    losDosLadrones = new Equipo("Las Magicos", List(un_debil, unLadron,unLadron),100)
    losGuerreros = new Equipo("Las Magicos", List(un_debil, unGuerrero),100)
    losVeganos = new Equipo("Los Veganos",List(repollo,peregil,brocoli))


    cascoVikingo = new Item("Casco Vikingo", 200, Cabeza, efecto_cascoVikingo, condicion_cascoVikingo)


    tareaDificil = new Tarea("Tarea Dificil",condicion_tareaDificil,facilidad_tareaDificil,efecto_tareaDificl)

  }

  //Test para probar de la tarea el pelear contra un mounstruo: 4
  //Desc: "reduce la vida de cualquier héroe con fuerza <20"
  @Test
  def pelearContraMonstruo_un_Guerrero(): Unit = {  //(10,15,0,-10)
    assertEquals(pelearContraMonstruo(unGuerrero).atributos().hp,35)
  }

  @Test
  def pelearContraMonstruo_un_Ladron(): Unit = {   //(-5,0,10,0)
    assertEquals(pelearContraMonstruo(unLadron).atributos().hp,20)
  }

  @Test
  def pelearContraMonstruo_un_Mago(): Unit = {   //(0,-20,0,20)
    assertEquals(pelearContraMonstruo(unMago).atributos().hp,1)
  }
  @Test
  def pelearContraMonstruo_un_SinTrabajo(): Unit = {
    assertEquals(pelearContraMonstruo(unSinTrabajo).atributos().hp,25)
  }


  //Test para probar de la tarea el Forzar Puerta:4
  //Desc: "no le hace nada a los magos ni a los ladrones, pero sube la  fuerza de todos los demás en 1 y baja en 5 su hp"
  @Test
  def forzarPuerta_Guerrero(): Unit = {
    assertEquals(forzarPuerta(unGuerrero).stats.hp,20)   //(10,15,0,-10)
    assertEquals(forzarPuerta(unGuerrero).stats.fuerza,26)
  }

  @Test
  def forzarPuerta_Ladron(): Unit = {   //(-5,0,10,0)
    assertEquals(forzarPuerta(unLadron).stats.hp,25)
    assertEquals(forzarPuerta(unLadron).stats.fuerza,25)
  }

  @Test
  def forzarPuerta_Mago(): Unit = {   //(0,-20,0,20)
    assertEquals(forzarPuerta(unMago).stats.hp,25)
    assertEquals(forzarPuerta(unMago).stats.fuerza,25)
  }

  @Test
  def forzarPuerta_SinTrabajo(): Unit = {
    assertEquals(forzarPuerta(unSinTrabajo).stats.hp,20)
    assertEquals(forzarPuerta(unSinTrabajo).stats.fuerza,26)
  }

  //Test para probar de la tarea el robar Talisman:5
  //Desc: "le agrega un talismán al héroe"
  @Test
  def robarTalisman_Con_unHeroeDe_Treinta_Cinco(): Unit = {  // + (10,0,0,0)
    assertEquals(robarTalisman(unoDe_Treinta_Cinco, cascoVikingo).atributos().hp,45)
  }

  @Test
  def robarTalisman_Guerrero(): Unit = {   //+(10,15,0,-10) + (10,0,0,0)
    assertEquals(robarTalisman(unGuerrero, cascoVikingo).atributos().hp,35)
  }

  @Test
  def robarTalisman_Ladron(): Unit = {   //(-5,0,10,0) + (10,0,0,0)
    assertEquals(robarTalisman(unLadron, cascoVikingo).atributos().hp,20)
  }

  @Test
  def robarTalisman_Mago(): Unit = {   //(0,-20,0,20) + (10,0,0,0)
    assertEquals(robarTalisman(unMago, cascoVikingo).atributos().hp,25)
  }

  @Test
  def robarTalisman_SinTrabajo(): Unit = { //+ (10,0,0,0)
    assertEquals(robarTalisman(unSinTrabajo, cascoVikingo).stats.hp,25)
    assertEquals(robarTalisman(unSinTrabajo, cascoVikingo).stats.fuerza,25)
  }

  //Test para probar la facilidad de pelear contra mounstruo: 4
  //Desc: "tiene una facilidad de 10 para cualquier héroe o 20 si  el líder del equipo es un guerrero"
  @Test
  def facilidadDe_pelearContraMonstruo_ConLider_Guerrero(): Unit = {
    assertEquals(facilidadPelearContraMonstruo(losGuerreros),20)
  }

  @Test
  def facilidadDe_pelearContraMonstruo_ConLider_Ladron(): Unit = {
    assertEquals(facilidadPelearContraMonstruo(losLadrones),10)
  }

  @Test
  def facilidadDe_pelearContraMonstruo_ConLider_Mago(): Unit = {
    assertEquals(facilidadPelearContraMonstruo(losMagicos),10)
  }

  @Test
  def facilidadDe_pelearContraMonstruo_ConLider_SinTrabajo(): Unit = {
    assertEquals(facilidadPelearContraMonstruo(losSinTrabajo),10)
  }


  //Test para probar la facilidad de forzar la puerta : 3
  //Desc: "tiene facilidad igual a la inteligencia del  héroe + 10 por cada ladrón en su equipo"
  @Test
  def facilidadDe_ForzarPuerta_Sin_Ladrones_en_el_Equipo(): Unit = {
    assertEquals(facilidadForzarPuerta(unSinTrabajo,losSinTrabajo),25)
  }

  @Test
  def facilidadDe_ForzarPuerta_Con_un_Ladron_en_el_Equipo(): Unit = {
    assertEquals(facilidadForzarPuerta(un_debil,losLadrones),11)
  }

  @Test
  def facilidadDe_ForzarPuerta_Con_Dos_Ladron_en_el_Equipo(): Unit = {
    assertEquals(facilidadForzarPuerta(un_debil,losDosLadrones),21)
  }

  //Test para probar la facilidad de Robar Talisman: 2
  //Desc: "tiene facilidad igual a la  velocidad del héroe, pero no puede ser hecho por equipos cuyo líder no sea un ladrón"
  @Test
  def facilidad_RobarTalisman_ConLider_Ladron(): Unit = {
    assertEquals(facilidadRobarTalisman(unSinTrabajo,losLadrones),25)
  }

  @Test
  def facilidad_RobarTalisman_SinLider_Ladron(): Unit = {
    assertEquals(facilidadRobarTalisman(unSinTrabajo,losSinTrabajo),-1)
  }

  @Test
  def realizarTareaDificil(): Unit = {
    val tareaRealizada = tareaDificil.realizarTarea(losVeganos)
    assertEquals(tareaRealizada.resultado, TareaSuperada)
    assertEquals(tareaRealizada.equipo.get.obtenerLider().lider, 109)
  }

}
