import org.junit.Assert.assertEquals
import org.junit.Test

class ItemsTest {

  //  Condiciones y Efectos de los Items
  val condicion_cascoVikingo = (h: Heroe) => h.stats.fuerza > 30
  val efecto_cascoVikingo = (h: Heroe) => h.stats.subirHp(10)

  val condicion_palitoMagico = (h: Heroe) => h.trabajaDe(Mago) || (h.trabajaDe(Ladron) && h.stats.inteligencia > 30)
  val efecto_palitoMagico = (h: Heroe) => h.stats.subirInteligencia(20)

  val condicion_armaduraEleganteSport = (h: Heroe) => true
  val efecto_armaduraEleganteSport = (h: Heroe) => {h.stats.subirVelocidad(30); h.stats.subirHp(-30)}

  val condicion_arcoViejo = (h: Heroe) => true
  val efecto_arcoViejo = (h: Heroe) => h.stats.subirFuerza(2)

  val condicion_escudoAntiRobo = (h: Heroe) => !(h.trabajaDe(Ladron) || h.stats.fuerza < 20)
  val efecto_escudoAntiRobo = (h: Heroe) => h.stats.subirHp(20)

  val condicion_talismanDedicacion = (h: Heroe) => true


  val efecto_talismanDedicacion = (h: Heroe) => {
    val subida = {
      h.trabajo match {
        case Some(x) => x.valorAtributoPrincipal() * 0.1;
        case None => 0
      }
    }
    val redondeo = math.ceil(subida).toInt
    h.stats.subirATodos(redondeo)
  }

  val condicion_talismanMinimalista = (h: Heroe) => true
  val efecto_talismanMinimalista = (h: Heroe) => h.stats.subirHp(50 - 10 * (h.inventario.cantidadItems - 1))

  val condicion_vinchaBufaloAgua = (h: Heroe) => h.trabajo.isEmpty // si no esta trabajando
  val efecto_vinchaBufaloAgua = (h: Heroe) =>
      if (h.atributos().fuerza>h.atributos().inteligencia){
        h.atributos().subirInteligencia(30)}  //??? lo hago asi para que no sea solamente al stat base
      else {
        h.atributos().subirATodosMenosInteligencia(10)  //??? lo hago asi para que no sea solamente al stat base
      }


  //  Items
  val cascoVikingo = new Item("Casco Vikingo", 200, Cabeza, efecto_cascoVikingo, condicion_cascoVikingo)
  val palitoMagico = new Item("Palito Magico", 150, Mano(1), efecto_palitoMagico, condicion_palitoMagico)
  val armaduraEleganteSport = new Item("Armadura Elegante Sport", 300, Pecho, efecto_armaduraEleganteSport, condicion_armaduraEleganteSport)
  val arcoViejo = new Item("Arco Viejo", 200, Mano(2), efecto_arcoViejo, condicion_arcoViejo)
  val escudoAntiRobo = new Item("Escudo Anti-Robo", 500, Mano(1), efecto_escudoAntiRobo, condicion_escudoAntiRobo)
  val talismanDedicacion = new Item("Talisman de Dedicacion", 300, Cuello, efecto_talismanDedicacion, condicion_talismanDedicacion)
  val talismanMinimalista = new Item("Talisman Minimalista", 50, Cuello, efecto_talismanMinimalista, condicion_talismanMinimalista)



  @Test
  def asignarItems(): Unit = {
    val jonny = new Heroe(new Stats(50,50,50,50))
    jonny.equiparseItem(cascoVikingo)
    assertEquals(jonny.inventario.cantidadItems, 1)
    assertEquals(jonny.atributos().hp,60)
    jonny.equiparseItem(talismanMinimalista)
    assertEquals(jonny.inventario.cantidadItems, 2)
    assertEquals(jonny.atributos().hp, 100) // +40 = 50 - 10(un item tenia)
    assertEquals(jonny.stats, new Stats(50,50,50,50)) // stat original
  }

  @Test
  def asignarTrabajoItems(): Unit = {
    val lucas = new Heroe(new Stats(30,30,30,30))
    lucas.adquirirTrabajo(Ladron) // (25,30,40,30) < --- -5,0,10,0
    lucas.equiparseItem(arcoViejo) // (25,32,40,30)
    lucas.equiparseItem(talismanDedicacion) // (26,33,41,31)
    lucas.equiparseItem(talismanMinimalista) // (56,33,41,31) + 30hp
    assertEquals(lucas.inventario.cantidadItems, 3)
    assertEquals(lucas.atributos(),new Stats(56,33,41,31))
  }
}
