import org.junit.Assert.assertEquals
import org.junit.Test


class Heroes4Quest_test {

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

  val condicion_vinchaBufaloAgua = (h: Heroe) => h.trabajo match {case Some(_) => false; case None => true}
//  val efecto_vinchaBufaloAgua = (h: Heroe) => ... consulta enviada a los ayudantes


//  Items
  val cascoVikingo = new Item("Casco Vikingo", 200, Cabeza, efecto_cascoVikingo, condicion_cascoVikingo)
  val palitoMagico = new Item("Palito Magico", 150, Mano(1), efecto_palitoMagico, condicion_palitoMagico)
  val armaduraEleganteSport = new Item("Armadura Elegante Sport", 300, Pecho, efecto_armaduraEleganteSport, condicion_armaduraEleganteSport)
  val arcoViejo = new Item("Arco Viejo", 200, Mano(2), efecto_arcoViejo, condicion_arcoViejo)
  val escudoAntiRobo = new Item("Escudo Anti-Robo", 500, Mano(1), efecto_escudoAntiRobo, condicion_escudoAntiRobo)
  val talismanDedicacion = new Item("Talisman de Dedicacion", 300, Cuello, efecto_talismanDedicacion, condicion_talismanDedicacion)
  val talismanMinimalista = new Item("Talisman Minimalista", 50, Cuello, efecto_talismanMinimalista, condicion_talismanMinimalista)


  @Test
  def asignarTrabajoHeroe(): Unit = {
    val juan: Heroe = new Heroe(new Stats(100,50,30,10))
    juan.adquirirTrabajo(Guerrero)
    assertEquals(juan.atributos().fuerza, 65)
    assertEquals(juan.stats.fuerza, 50)
  }

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
