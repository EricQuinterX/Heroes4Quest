//import org.junit.Assert._
//import org.junit.Test
//
///**
//  * Created by jon-VBox-w7 on 23/06/2016.
//  */
//class TareaTest {
//  //Test para validar tareas
//
//  val facilidad_pelear_contra_monstruo = (h :Heroe) =>
//    h.equipo match {
//      case Some(equipo) => equipo.lider() match {
//        case Some(lider) => lider.trabajo match {
//          case Some(Guerrero) => 20
//          case _ => 10
//        }
//        case _ => 11
//      }
//      case _ => 12
//    }
//
//  val efecto_pelear_contra_monstruo = (h :Heroe) => if (h.stats.fuerza<20) h.copy(stats = h.stats.setearHp(1)) else h
//
//  val efecto_Forzar_Puerta = (unHeroe :Heroe) =>
//    if (!unHeroe.trabajaDe(Mago) && !unHeroe.trabajaDe(Ladron)) unHeroe.copy(stats = unHeroe.stats.subirFuerza(1).subirHp(-5)) else unHeroe
//
//  val facilidad_Forzar_Puerta = (unHeroe :Heroe) =>
//  unHeroe.equipo match {
//      case None => unHeroe.stats.inteligencia
//      case Some(equipo) => unHeroe.stats.inteligencia + equipo.heroes.filter(heroe => heroe.trabajaDe(Ladron)).map(heroe => 10).sum
//    }
//
//
//  @Test
//  def efectoForzarPuertaGuerrero(): Unit = {
//    val alf = new Heroe(new Stats(10,19,10,10), Some(Guerrero))
//    val auxAlf = efecto_Forzar_Puerta(alf)
//    assertEquals(auxAlf.stats.hp,5)
//    assertEquals(auxAlf.stats.fuerza,20)
//  }
//
//  @Test
//  def efectoForzarPuertaMago(): Unit = {
//    val duende = new Heroe(new Stats(10,19,10,10), Some(Mago))
//    val auxDuende = efecto_Forzar_Puerta(duende)
//    assertEquals(auxDuende.stats.hp,10)
//    assertEquals(auxDuende.stats.fuerza,19)
//  }
//
//  @Test
//  def facilidadForzarPuertaConLadronesEnElEquipo(): Unit = {
//    val jonny = new Heroe(new Stats(10,19,10,10), Some(Guerrero)) //(20,25,10,1)
//    val jonas = new Heroe(new Stats(20,20,1,1), Some(Mago)) // (20,1,1,21)
//    val matias = new Heroe(new Stats(30,10,5,5), Some(Ladron)) // (25,10,15,5)
//    val losDragones = new Equipo("Los Dragones", List(jonny, jonas, matias),0)
//
//    jonny.unirseAEquipo(losDragones)
//    jonas.unirseAEquipo(losDragones)
//    matias.unirseAEquipo(losDragones)
//
//    assertEquals(facilidad_Forzar_Puerta(jonny),20)
//    assertEquals(facilidad_Forzar_Puerta(jonas),11)
//  }
//
//
//  @Test
//  def facilidadForzarPuertaSinLadronesEnElEquipo(): Unit = {
//    val alf = new Heroe(new Stats(10,19,10,10), Some(Guerrero)) //(20,25,10,1)
//    val duende = new Heroe(new Stats(20,20,1,1), Some(Mago)) // (20,1,1,21)
//    val malditos = new Equipo("Los Dragones", List(duende, alf),0)
//
//    alf.unirseAEquipo(malditos)
//    duende.unirseAEquipo(malditos)
//
//    assertEquals(facilidad_Forzar_Puerta(alf),10)
//    assertEquals(facilidad_Forzar_Puerta(duende),1)
//  }
//
//  @Test
//  def facilidadForzarPuertaSinEquipo(): Unit = {
//    val alf = new Heroe(new Stats(10,19,10,10), Some(Guerrero)) //(20,25,10,1)
//
//    assertEquals(facilidad_Forzar_Puerta(alf),10)  //Si el heroe es ladron que pasa??? se suma +10???
//  }
//
//
//  @Test
//  def tareaMasCondicion(): Unit = {
//    val jonny = new Heroe(new Stats(10,19,10,10), Some(Guerrero)) //(20,25,10,1)
//    val jonas = new Heroe(new Stats(20,20,1,1), Some(Mago)) // (20,1,1,21)
//    val matias = new Heroe(new Stats(30,10,5,5), Some(Ladron)) // (25,10,15,5)
//    val losDragones = new Equipo("Los Dragones", List(jonny, jonas, matias))
//    jonny.unirseAEquipo(losDragones)
//    jonas.unirseAEquipo(losDragones)
//    matias.unirseAEquipo(losDragones)
//
//    val condicion_pelearContraMonstruo = (e: Equipo) => true
//    val pelearContraMonstruo = new Tarea("Pelea Contra Monstruo",condicion_pelearContraMonstruo, facilidad_pelear_contra_monstruo,efecto_pelear_contra_monstruo)
//
//    //facilidad ok
//    assertEquals(facilidad_pelear_contra_monstruo(jonas),20)
//    assertEquals(losDragones.lider().get,jonny)
//    assertEquals(efecto_pelear_contra_monstruo(jonny).stats.hp,1) //efecto ok
////    assertEquals(pelearContraMonstruo,9) //Tarea ----> esto devuelve una tarea no un numero
//  }
//
//}
