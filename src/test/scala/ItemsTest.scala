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

  val condicion_vichaDelBufaloDeAgua = (h: Heroe) => h.trabajo.isEmpty
  val efecto_vichaDelBufaloDeAgua    = (h: Heroe) => {
    if (h.stats.fuerza > h.stats.inteligencia)
      h.stats.subirInteligencia(30)
    else
      h.stats.subirHp(10).subirFuerza(10).subirVelocidad(10)
  }


  val condicion_talismanMaldito = (h: Heroe) => true
  val efecto_talismanMaldito    = (h: Heroe) => h.stats.setearATodos(1)


  val condicion_espadaDeLaVida = (h:Heroe) => true
  val efecto_espadaDeLaVida = (h:Heroe) => h.stats.setearFuerza(h.stats.hp)






  //  Son 10 items en el enunciado
  val cascoVikingo = new Item("Casco Vikingo", 200, Cabeza, efecto_cascoVikingo, condicion_cascoVikingo)
  val palitoMagico = new Item("Palito Magico", 150, Mano(1), efecto_palitoMagico, condicion_palitoMagico)
  val armaduraEleganteSport = new Item("Armadura Elegante Sport", 300, Pecho, efecto_armaduraEleganteSport, condicion_armaduraEleganteSport)
  val arcoViejo = new Item("Arco Viejo", 200, Mano(2), efecto_arcoViejo, condicion_arcoViejo)
  val escudoAntiRobo = new Item("Escudo Anti-Robo", 500, Mano(1), efecto_escudoAntiRobo, condicion_escudoAntiRobo)
  val talismanDedicacion = new Item("Talisman de Dedicacion", 300, Cuello, efecto_talismanDedicacion, condicion_talismanDedicacion)
  val talismanMinimalista = new Item("Talisman Minimalista", 50, Cuello, efecto_talismanMinimalista, condicion_talismanMinimalista)
  val vinchaDelBufaloDeAgua = new Item("Vincha del Bufalo de Agua", 100, Cabeza, efecto_vichaDelBufaloDeAgua, condicion_vichaDelBufaloDeAgua)
  val talismanMaldito = new Item("Talisman Maldito", 1000, Cuello, efecto_talismanMaldito, condicion_talismanMaldito)
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
   }

  @Test
  def palitoMagico_No_CumpleCondicion(): Unit = {
    val jonny = new Heroe(new Stats  (50,50,50,50))
    jonny.adquirirTrabajo(Guerrero)   //(10,15,0,-10)
    jonny.equiparseItem(palitoMagico)// (0,0,0,20)
    assertEquals(jonny.atributos().inteligencia,40)
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
    assertEquals(jonny.atributos().velocidad,35)
    assertEquals(jonny.atributos().hp,1)
   }

  @Test
  def armaduraElefanteSport_Equiarse_Hereo_Ladron(): Unit = {
    val jonny = new Heroe(new Stats  (50,50,5,50))
    jonny.adquirirTrabajo(Ladron)    //(-5,0,10,0)
    jonny.equiparseItem(armaduraEleganteSport)// (-30,0,+30,0)
    assertEquals(jonny.atributos().velocidad,45)
    assertEquals(jonny.atributos().hp,15)
  }


  //Test para ArcoViejo : total=1
  @Test
  def arcoViejo_Equiarse_Hereo(): Unit = {
    val jonny = new Heroe(new Stats  (10,50,5,50))
    jonny.equiparseItem(arcoViejo)// (0,+2,0,0)
    assertEquals(jonny.atributos().fuerza,52)
  }


  //Test para Escudo AntiRobo : total=3
  @Test
  def escudo_AntiRobo_CumpleCondicion(): Unit = {
    val jonny = new Heroe(new Stats  (10,50,5,50))
    jonny.equiparseItem(escudoAntiRobo)// (+20,0,0,0)
    assertEquals(jonny.atributos().hp,30)
  }

  @Test
  def escudo_AntiRobo_No_CumpleCondicion_Por_Ladron(): Unit = {
    val jonny = new Heroe(new Stats  (10,50,5,50))
    jonny.adquirirTrabajo(Ladron)    //(-5,0,10,0)
    jonny.equiparseItem(escudoAntiRobo)// (+20,0,0,0)
    assertEquals(jonny.atributos().hp,5)
    assertEquals(jonny.atributos().velocidad,15)
  }


  @Test
  def escudo_AntiRobo_No_CumpleCondicion_Por_Debil(): Unit = {
    val jonny = new Heroe(new Stats  (10,10,5,50))
    jonny.equiparseItem(escudoAntiRobo)// (+20,0,0,0)
    assertEquals(jonny.atributos().hp,10)
  }


  //Test para talismanDe_Dedicacion : total=3
  @Test
  def talismanDe_Dedicacion_Sin_Trabajo(): Unit = {
    val jonny = new Heroe(new Stats  (10,10,10,10))
    jonny.equiparseItem(talismanDedicacion)// (*1.1,*1.1,*1.1,*1.1)
    assertEquals(jonny.atributos().hp,10)
  }


  @Test
  def talismanDe_Dedicacion_Con_Trabajo_Mago(): Unit = {
    val jonny = new Heroe(new Stats  (10,10,10,10))
    jonny.adquirirTrabajo(Mago)     //(0,-20,0,20)
    jonny.equiparseItem(talismanDedicacion)// (*1.1,*1.1,*1.1,*1.1)
    assertEquals(jonny.atributos().hp,12)
  }

  @Test
  def talismanDe_Dedicacion_Con_Trabajo_Mago_conPalito(): Unit = {
    val jonny = new Heroe(new Stats  (10,10,10,10))
    jonny.adquirirTrabajo(Mago)     //(0,-20,0,20)
    jonny.equiparseItem(palitoMagico)// (0,0,0,20)
    jonny.equiparseItem(talismanDedicacion)// (*1.1,*1.1,*1.1,*1.1)
    assertEquals(jonny.atributos().hp,12)
    assertEquals(jonny.atributos().inteligencia,52)
  }


  //Test para talismanDel_Minimalismo: total=2
  @Test
  def talismanDel_Minimalismo_SinItems_Equipados(): Unit = {
    val jonny = new Heroe(new Stats  (10,10,10,10))
    jonny.equiparseItem(talismanMinimalista)// (+50-10*inventario.size,0,0,0)
    assertEquals(jonny.atributos().hp,60)
  }

  @Test
  def talismanDel_Minimalismo_Con_2_Items_Equipados(): Unit = {
    val jonny = new Heroe(new Stats  (10,50,10,10))
    jonny.equiparseItem(escudoAntiRobo)// (+20,0,0,0)
    jonny.equiparseItem(cascoVikingo) // (+10,0,0,0)
    jonny.equiparseItem(talismanMinimalista)// (+50-10*inventario.size,0,0,0)
    assertEquals(jonny.atributos().hp,70)
    assertEquals(jonny.inventario.cantidadItems, 3)
  }


  //Test para Vincha del bufalo de agua: total=3
  @Test
  def vinchaDel_Bufalo_de_Agua_CumpleCondicion_MayorInteligencia(): Unit = {
    val jonny = new Heroe(new Stats (10,10,10,10))
    jonny.equiparseItem(vinchaDelBufaloDeAgua)// (0,0,0,+30) o (+10,+10,+10,0)
    assertEquals(jonny.atributos().hp,20)
    assertEquals(jonny.atributos().velocidad,20)
    assertEquals(jonny.atributos().fuerza,20)
  }

  @Test
  def vinchaDel_Bufalo_de_Agua_CumpleCondicion_MayorFuerza(): Unit = {
    val jonny = new Heroe(new Stats  (10,20,10,10))
    jonny.equiparseItem(vinchaDelBufaloDeAgua)// (0,0,0,+30) o (+10,+10,+10,0)
    assertEquals(jonny.atributos().inteligencia,40)
  }

  @Test
  def vinchaDel_Bufalo_de_Agua_No_CumpleCondicion(): Unit = {
    val jonny = new Heroe(new Stats  (10,20,10,5))
    jonny.adquirirTrabajo(Guerrero)
    jonny.equiparseItem(vinchaDelBufaloDeAgua)// (0,0,0,+30) o (+10,+10,+10,0)
    assertEquals(jonny.atributos().inteligencia,1)
  }


  //Test para el talisman maldito: total=3
  @Test
  def talisman_Maldito(): Unit = {
    val jonny = new Heroe(new Stats  (10,10,10,10))
    jonny.equiparseItem(talismanMaldito)// (1,1,1,1)
    assertEquals(jonny.atributos().hp,1)
    assertEquals(jonny.atributos().velocidad,1)
    assertEquals(jonny.atributos().fuerza,1)
    assertEquals(jonny.atributos().inteligencia,1)
  }

  @Test
  def talisman_Maldito_Mago(): Unit = {
    val jonny = new Heroe(new Stats  (10,10,10,10))
    jonny.adquirirTrabajo(Mago)      //(0,-20,0,20)
    jonny.equiparseItem(armaduraEleganteSport)// (-30,0,+30,0)
    jonny.equiparseItem(talismanMaldito)// (1,1,1,1)
    assertEquals(jonny.atributos().hp,1)
    assertEquals(jonny.atributos().velocidad,1)
    assertEquals(jonny.atributos().fuerza,1)
    assertEquals(jonny.atributos().inteligencia,1)
  }

  @Test
  def talisman_Maldito_Mago_conItem_ArmaduraDeElefante(): Unit = {
    val jonny = new Heroe(new Stats  (10,10,10,10))
    jonny.adquirirTrabajo(Mago)      //(0,-20,0,20)
    jonny.equiparseItem(talismanMaldito)// (1,1,1,1)
    jonny.equiparseItem(armaduraEleganteSport)// (-30,0,+30,0)
    assertEquals(jonny.atributos().hp,1)
    assertEquals(jonny.atributos().fuerza,1)
    assertEquals(jonny.atributos().velocidad,1)
    assertEquals(jonny.atributos().inteligencia,1)
  }


  //Test para la Espada de la Vida: total=2
  @Test
  def espadaDeLa_Vida_conPocaVida_y_muchaFuerza(): Unit = {
    val jonny = new Heroe(new Stats  (10,100,10,10))
    jonny.equiparseItem(espadaDeLaVida)// (igual,igual,0,0)
    assertEquals(jonny.atributos().fuerza,10)
  }

  @Test
  def espadaDeLa_Vida_conMuchaVida_y_pocaFuerza(): Unit = {
    val jonny = new Heroe(new Stats  (100,10,10,10))
    jonny.equiparseItem(espadaDeLaVida)// (igual,igual,0,0)
    assertEquals(jonny.atributos().fuerza,100)
  }

}
