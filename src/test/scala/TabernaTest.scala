/**
  * Created by jon-VBox-w7 on 01/07/2016.
  */
import org.junit.Assert._
import org.junit.{Before, Test}

class TabernaTest {
  //Punto 3
  val condicion_facilidad_Pelear = (e: Equipo)=> true
  val condicion_facilidad_Forzar = (e: Equipo) => true
  val condicion_facilidad_Robar  = (e: Equipo) => e.lider.get.trabajaDe(Ladron)
  val condicion_tareaDificil = (e: Equipo) => e.heroes.size >= 2
  val condicion_elRegresoDeBroly = (e: Equipo) => e.lider.get.atributos.velocidad > 50
  val condicion_tanque = (e: Equipo) => e.heroes.size >= 2
  val condicion_nivel1 = (e: Equipo) => e.heroes.exists(_.stats.hp > 20)
  val condicion_nivel2 = (e: Equipo) => e.heroes.exists(_.atributos.fuerza > 100)
  val condicion_nivel3 = (e: Equipo) => e.pozoDeOro > 200


  val facilidad_Valor_Pelear = (h: Heroe, e: Equipo) => if (e.lider.get.trabajaDe(Guerrero)) 20 else 10
  val facilidad_Valor_Forzar= (h: Heroe, e: Equipo) => h.atributos.inteligencia + e.heroes.count(_.trabajaDe(Ladron)) *10
  val facilidad_Valor_Robar = (h: Heroe, e: Equipo) =>  if (e.lider.get.trabajaDe(Ladron)) h.atributos.velocidad else -1
  val facilidad_tareaDificil = (h: Heroe, e: Equipo) => h.atributos.principal(h.trabajo)
  val facilidad_elRegresoDeBroly = (h: Heroe, e: Equipo) => h.stats.velocidad
  val facilidad_tanque = (h: Heroe, e: Equipo) => h.stats.hp
  val facilidad_nivel1 = (h: Heroe, e: Equipo) => h.stats.fuerza
  val facilidad_nivel2 = (h: Heroe, e: Equipo) => h.atributos.velocidad + h.atributos.inteligencia
  val facilidad_nivel3 = (h: Heroe, e: Equipo) => h.stats.hp + h.stats.fuerza



  val pelear_Efecto = (h: Heroe, i: Item) => if (h.atributos.fuerza<20) h.copy(stats = h.stats.copy(hp = 1)) else h
  val forzar_Efecto = (h: Heroe, i: Item) => {
    if(!h.trabajaDe(Mago) && !h.trabajaDe(Ladron))
      h.copy(stats = h.stats.setear(h.stats.copy(hp = h.stats.hp-5,fuerza = h.stats.fuerza+1)))
    else h
  }
  val robar_Efecto  = (h: Heroe, i: Item) => h.equiparseItem(i)
  val efecto_tareaDificl = (h: Heroe, e: Equipo) => {
    val s = h.stats
    val h1 = h.copy(stats = s.copy(hp = s.hp + 10))
    e.reemplazarMiembro(h1, h)
  }
  val efecto_elRegresoDeBroly = (h: Heroe, e: Equipo) => e.copy(pozoDeOro = e.pozoDeOro + 100)
  val efecto_tanque = (h: Heroe, e: Equipo) => e.copy(pozoDeOro = e.pozoDeOro + 100)
  val efecto_nivel1 = (h: Heroe, e: Equipo) => {
    val nuevoHeroe = h.copy(stats = h.stats.setear(Stats(10,10,10,10)))
    e.copy(heroes = nuevoHeroe :: e.heroes.filter(_ != h))
  }
  val efecto_nivel2 = (h: Heroe, e: Equipo) => e.copy(pozoDeOro = e.pozoDeOro + 100)
  val efecto_nivel3 = (h: Heroe, e: Equipo) => e.obtieneMiembro(unLadron)

  // Recompensas
  val recompensa_ZombiePlant = (e: Equipo) => e.copy(pozoDeOro = e.pozoDeOro + 1000)
  val recompensa_MisionImposible = (e: Equipo) => e.copy(pozoDeOro = e.pozoDeOro * 100)
  val recompensa_Resistencia = (e: Equipo) => e.copy(pozoDeOro = e.pozoDeOro * 5)


  var unLadron:Heroe = _
  var unGuerrero:Heroe = _
  var unMago:Heroe = _
  var unSinTrabajo:Heroe = _
  var un_debil:Heroe = _
  var brocoli: Heroe = _
  var peregil: Heroe = _
  var repollo: Heroe = _


  var losSinTrabajo:Equipo = _
  var losVeganos: Equipo = _

  var tarea_Pelear:Tarea = _
  var tarea_Forzar:Tarea = _
  var tarea_Robar:Tarea = _
  var tarea_TareaDificil: Tarea = _
  var tarea_ElRegresoDeBroly: Tarea = _
  var tarea_Tanque: Tarea = _
  var tarea_nivel1: Tarea = _
  var tarea_nivel2: Tarea = _
  var tarea_nivel3: Tarea = _


  var mision_ZombiePlant: Mision = _
  var mision_MisionImposible: Mision = _
  var mision_Resistencia: Mision = _


  var  cascoVikingo:Item = _
  val condicion_cascoVikingo = (h: Heroe) => h.stats.fuerza > 30
  val efecto_cascoVikingo    = (h: Heroe) => {
    val s = h.stats
    s.setear(s.copy(hp = s.hp+10))
  }

  var tablon_CampaniaDelDesierto: List[Mision] = _

  val criterio_elegirMision_Oro = (e1:Equipo, e2:Equipo) => e1.pozoDeOro > e2.pozoDeOro
  val criterio_elegirMision_MasHeroes = (e1:Equipo, e2:Equipo) => e1.heroes.length > e2.heroes.length


  var la_taberna_de_warcraft: Taberna = _

  @Before
  def initialize() = {
    unLadron = new Heroe(new Stats(25,25,25,25),Some(Ladron))
    unGuerrero = new Heroe(new Stats(35,35,35,35),Some(Guerrero))
    unMago = new Heroe(new Stats(25,25,25,25),Some(Mago))
    unSinTrabajo = new Heroe(new Stats(25,25,25,25))
    un_debil = new Heroe(new Stats(10,10,10,10))
    brocoli = new Heroe(Stats(40,19,58,99))
    peregil = new Heroe(Stats(60,55,62,94))
    repollo = new Heroe(Stats(60,95,30,10))

    losSinTrabajo = new Equipo("Los Sin Trabajo", List(unSinTrabajo),100)
    losVeganos = new Equipo("Los Veganos",List(repollo,peregil,brocoli))

    tarea_TareaDificil = new Tarea("Tarea Dificil",condicion_tareaDificil,facilidad_tareaDificil,efecto_tareaDificl)
    tarea_ElRegresoDeBroly = new Tarea("El Regreso de Broly", condicion_elRegresoDeBroly, facilidad_elRegresoDeBroly, efecto_elRegresoDeBroly)
    tarea_Tanque = new Tarea("Tanque", condicion_tanque, facilidad_tanque, efecto_tanque)
    tarea_nivel1 = new Tarea("Nivel1", condicion_nivel1, facilidad_nivel1, efecto_nivel1)
    tarea_nivel2 = new Tarea("Nivel2", condicion_nivel2, facilidad_nivel2, efecto_nivel2)
    tarea_nivel3 = new Tarea("Nivel3", condicion_nivel3, facilidad_nivel3, efecto_nivel3)

    mision_ZombiePlant = new Mision(List(tarea_TareaDificil,tarea_ElRegresoDeBroly,tarea_Tanque),recompensa_ZombiePlant)
    mision_MisionImposible = new Mision(List(tarea_nivel1,tarea_nivel2,tarea_nivel3), recompensa_MisionImposible)
    mision_Resistencia = new Mision(List(tarea_nivel1,tarea_nivel2,tarea_nivel3), recompensa_Resistencia)

    tablon_CampaniaDelDesierto = List(mision_ZombiePlant,mision_Resistencia,mision_MisionImposible)

    la_taberna_de_warcraft = new Taberna(tablon_CampaniaDelDesierto)
  }


  @Test
  def elegirMisionDelTablon(): Unit ={
    val mision_elegida:Option[Mision] = la_taberna_de_warcraft.elegirMision(losVeganos,criterio_elegirMision_Oro)
    assertEquals(mision_elegida.getOrElse(None),mision_ZombiePlant)
  }

  @Test
  def entrenar(): Unit ={
    val equipo = la_taberna_de_warcraft.entrenar(losVeganos,criterio_elegirMision_Oro)
    assertEquals(equipo.pozoDeOro, 1200)
  }

}
