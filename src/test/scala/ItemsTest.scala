import org.junit.Assert.assertEquals
import org.junit.Test

class ItemsTest {

  //  Condiciones y Efectos de los Items
  val condicion_cascoVikingo = (h: Heroe) => h.stats.fuerza > 30
  val efecto_cascoVikingo    = (h: Heroe) => h.stats.subirHp(10)

  val condicion_palitoMagico = (h: Heroe) => h.trabajaDe(Mago) || (h.trabajaDe(Ladron) && h.stats.inteligencia > 30)
  val efecto_palitoMagico    = (h: Heroe) => h.stats.subirInteligencia(20)

  val condicion_armaduraEleganteSport = (h: Heroe) => true
  val efecto_armaduraEleganteSport    = (h: Heroe) => h.stats.subirHp(-30).subirVelocidad(30)



  val condicion_arcoViejo = (h: Heroe) => true
  val efecto_arcoViejo    = (h: Heroe) => h.stats.subirFuerza(2)

  val condicion_escudoAntiRobo = (h: Heroe) => !(h.trabajaDe(Ladron) || h.stats.fuerza < 20)
  val efecto_escudoAntiRobo    = (h: Heroe) => h.stats.subirHp(20)

  val condicion_talismanDedicacion = (h: Heroe) => true
  val efecto_talismanDedicacion    = (h: Heroe) => {
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
  val efecto_talismanMinimalista    = (h: Heroe) => h.stats.subirHp(50 - 10 * (h.inventario.cantidadItems - 1))

  val condicion_vinchaBufaloAgua = (h: Heroe) => h.trabajo.isEmpty // si no esta trabajando
  val efecto_vinchaBufaloAgua    = (h: Heroe) =>
      if (h.atributos().fuerza>h.atributos().inteligencia){
        h.atributos().subirInteligencia(30)}  //??? lo hago asi para que no sea solamente al stat base
      else {
        h.atributos().subirATodosMenosInteligencia(10)  //??? lo hago asi para que no sea solamente al stat base
      }



 /* val condicion_vichaDelBufaloDeAgua = ???
  val efecto_vichaDelBufaloDeAgua    = ???

  val condicion_talismanMaldito = ???
  val efecto_talismanMaldito    = ???*/

  val condicion_espadaDeLaVida = (h:Heroe) => true
  val efecto_espadaDeLaVida = (h:Heroe) => h.stats.fuerzaIgualarA(h.stats.hp) //by jon: es sobre






  //  Son 10 items en el enunciado
  val cascoVikingo = new Item("Casco Vikingo", 200, Cabeza, efecto_cascoVikingo, condicion_cascoVikingo)
  val palitoMagico = new Item("Palito Magico", 150, Mano(1), efecto_palitoMagico, condicion_palitoMagico)
  val armaduraEleganteSport = new Item("Armadura Elegante Sport", 300, Pecho, efecto_armaduraEleganteSport, condicion_armaduraEleganteSport)
  val arcoViejo = new Item("Arco Viejo", 200, Mano(2), efecto_arcoViejo, condicion_arcoViejo)
  val escudoAntiRobo = new Item("Escudo Anti-Robo", 500, Mano(1), efecto_escudoAntiRobo, condicion_escudoAntiRobo)
  val talismanDedicacion = new Item("Talisman de Dedicacion", 300, Cuello, efecto_talismanDedicacion, condicion_talismanDedicacion)
  val talismanMinimalista = new Item("Talisman Minimalista", 50, Cuello, efecto_talismanMinimalista, condicion_talismanMinimalista)
  //val vinchaDelBufaloDeAgua = new Item("Vincha del Bufalo de Agua", 100, Cabeza, efecto_vichaDelBufaloDeAgua, condicion_vichaDelBufaloDeAgua)
  //val talismanMaldito = new Item("Talisman Maldito", 1000, Cuello, efecto_talismanMaldito, condicion_talismanMaldito)
  val espadaDeLaVida = new Item("Espada de la Vida",500,Mano(1),efecto_espadaDeLaVida,condicion_espadaDeLaVida)




  //Test para validar Items en general
  @Test
  def _equiparItem(): Unit = {
    val jonny = new Heroe(new Stats(50,50,50,50))
    jonny.equiparseItem(cascoVikingo)
    assertEquals(jonny.inventario.cantidadItems, 1)
   }

  @Test
  def _equiparItem_Duplicados(): Unit = {
    val jonny = new Heroe(new Stats(50,50,50,50))
    jonny.equiparseItem(cascoVikingo)
    jonny.equiparseItem(cascoVikingo)
    jonny.equiparseItem(cascoVikingo)
    assertEquals(jonny.inventario.cantidadItems, 1)
    assertEquals(jonny.atributos().hp,60)
  }

  @Test
  def _descartarItem_Que_No_Existe_en_el_Inventario(): Unit = {
    val jonny = new Heroe(new Stats(50,50,50,50))
    jonny.descartarItem(cascoVikingo)
    assertEquals(jonny.inventario.cantidadItems, 0)
  }

  @Test
  def _descartarItem_Que_Existe_en_el_Inventario(): Unit = {
    val jonny = new Heroe(new Stats(50,50,50,50))
    jonny.equiparseItem(cascoVikingo)
    assertEquals(jonny.atributos().hp,60)
    jonny.descartarItem(cascoVikingo)
    assertEquals(jonny.inventario.cantidadItems, 0)
    assertEquals(jonny.atributos().hp,50)
  }



  //Test para cascoVikingo: total=3
  @Test
  def cascoVikingo_CumpleCondicion(): Unit = {
    val jonny = new Heroe(new Stats(50,50,50,50))
    jonny.equiparseItem(cascoVikingo)
    assertEquals(jonny.atributos().hp,60)
    assertEquals(jonny.stats, new Stats(50,50,50,50))
  }
  @Test
  def cascoVikingo_No_CumpleCondicion(): Unit = {
    val jonny = new Heroe(new Stats(10,5,50,50))
    jonny.equiparseItem(cascoVikingo)
    assertEquals(jonny.atributos().hp,10)
    assertEquals(jonny.inventario.cantidadItems, 0)
  }

  @Test
  def cascoVikingo_Con_Trabajo_Guerrero(): Unit = {
    val jonny = new Heroe(new Stats   (100,50,50,50))
    jonny.adquirirTrabajo(Guerrero) //(10,15,0,-10)
    jonny.equiparseItem(cascoVikingo)//(10,0,0,0)
    assertEquals(jonny.atributos().hp,120)
    assertEquals(jonny.inventario.cantidadItems, 1)
  }


  //Test para PalitoMagico : total=4
  @Test
  def palitoMagico_CumpleCondicion_Mago(): Unit = {
    val jonny = new Heroe(new Stats  (50,50,50,50))
    jonny.adquirirTrabajo(Mago)     //(0,-20,0,20)
    jonny.equiparseItem(palitoMagico)// (0,0,0,20)
    assertEquals(jonny.atributos().fuerza,30)
    assertEquals(jonny.atributos().inteligencia,90)
    assertEquals(jonny.stats, new Stats(50,50,50,50))
  }

  @Test
  def palitoMagico_CumpleCondicion_Ladron(): Unit = {
    val jonny = new Heroe(new Stats  (50,50,50,50))
    jonny.adquirirTrabajo(Ladron)     //(-5,0,10,0)
    jonny.equiparseItem(palitoMagico)// (0,0,0,20)
    assertEquals(jonny.atributos().hp,45)
    assertEquals(jonny.atributos().inteligencia,70)
    assertEquals(jonny.atributos().velocidad,60)
    assertEquals(jonny.inventario.cantidadItems, 1)
  }

  @Test
  def palitoMagico_No_CumpleCondicion(): Unit = {
    val jonny = new Heroe(new Stats  (50,50,50,50))
    jonny.adquirirTrabajo(Guerrero)   //(10,15,0,-10)
    jonny.equiparseItem(palitoMagico)// (0,0,0,20)
    assertEquals(jonny.atributos().inteligencia,40)
    assertEquals(jonny.inventario.cantidadItems, 0)
  }

  @Test
  def palitoMagico_No_CumpleCondicion_Ladron(): Unit = {
    val jonny = new Heroe(new Stats  (50,50,50,20))
    jonny.adquirirTrabajo(Ladron)     //(-5,0,10,0)
    jonny.equiparseItem(palitoMagico)// (0,0,0,20)
    assertEquals(jonny.atributos().hp,45)
    assertEquals(jonny.atributos().inteligencia,20)
    assertEquals(jonny.atributos().velocidad,60)
    assertEquals(jonny.inventario.cantidadItems, 0)
  }


  //Test para Armadura EleganteSport : total=2
  @Test
  def armaduraElefanteSport_Equiarse_Hereo(): Unit = {
    val jonny = new Heroe(new Stats  (10,50,5,50))
    jonny.equiparseItem(armaduraEleganteSport)// (-30,0,+30,0)
    assertEquals(jonny.inventario.cantidadItems, 1)
    assertEquals(jonny.atributos().velocidad,35)
    assertEquals(jonny.atributos().hp,1)
   }

  @Test
  def armaduraElefanteSport_Equiarse_Hereo_Ladron(): Unit = {
    val jonny = new Heroe(new Stats  (50,50,5,50))
    jonny.adquirirTrabajo(Ladron)    //(-5,0,10,0)
    jonny.equiparseItem(armaduraEleganteSport)// (-30,0,+30,0)
    assertEquals(jonny.inventario.cantidadItems, 1)
    assertEquals(jonny.atributos().velocidad,45)
    assertEquals(jonny.atributos().hp,15)
  }


  



  @Test //by jon: (Pregunta hecha a los ayudantes) falta verificar si lo que añade el talisman de dedicacion es al acumulado o el del trabajo
  def jon_asignarTrabajoItems2(): Unit = {
    val lucas = new Heroe(new Stats(30,30,30,30))
    lucas.adquirirTrabajo(Ladron) // (25,30,40,30) < --- -5,0,10,0
    lucas.equiparseItem(arcoViejo) // (25,32,40,30)
    lucas.equiparseItem(talismanDedicacion) // (26,33,41,31) //vemos que añade solo el del trabajo
    assertEquals(lucas.atributos(),new Stats(26,33,41,31))
  }

  @Test //by jon: Hecho el test puedo agregar el item pero no puedo calcular sus atributos => habria que validar un "pisada" si omitirla o pisar al anterior
  def jon_asignarTrabajoItemsPisarItems(): Unit = {
    val lucas = new Heroe(new Stats(30,30,30,30))
    lucas.adquirirTrabajo(Ladron) // (25,30,40,30) < --- -5,0,10,0
    lucas.equiparseItem(arcoViejo) // (25,32,40,30)
    lucas.equiparseItem(talismanDedicacion) // (26,33,41,31)
    lucas.equiparseItem(espadaDeLaVida) // (26,33,41,31)  //veremos que efecto tiene poner un item encima de otro
    //assertEquals(lucas.atributos(),new Stats(26,26,41,31))
    assertEquals(lucas.inventario.cantidadItems, 3)
  }
  @Test //by jon: testeo que el efecto lo hace correctamente sobre el acumulado
  def jon_ProbandoespadaDeLaVida(): Unit = {
    val lucas = new Heroe(new Stats(30,30,30,30))
    lucas.adquirirTrabajo(Ladron) // (25,30,40,30) < --- -5,0,10,0
    lucas.equiparseItem(talismanDedicacion) // (26,31,41,31)
    lucas.equiparseItem(espadaDeLaVida) // (26,33,41,31)
    assertEquals(lucas.atributos(),new Stats(26,26,41,31))
   // assertEquals(lucas.inventario.cantidadItems, 3)
  }

}
