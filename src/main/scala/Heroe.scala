import scala.collection.mutable.Set


//class Item (val name: String, val precio: Int, val restriccion: (Heroe) => Boolean, val state:Stats){
//  def validarRestriccion(unHeroe: Heroe): Boolean = restriccion(unHeroe)
//}
//abstract class Item(val hp:Int,val fuerza:Int,val velocidad:Int,val inteligencia:Int,val stat_principal:String){
//
//  def validarRestriccion(unHeroe:Heroe):Boolean
//  def agregar(unHeroe:Heroe):Unit
//}
//case class CascoVikingo() extends Item (10,0,0,0,"fuerza"){
//  def validarRestriccion(unHeroe:Heroe):Boolean ={
//    unHeroe.fuerza()>30
//  }
//
//  override def agregar(unHeroe:Heroe):Unit={
//    val statsaux:Stats= unHeroe.stats
//    statsaux.aumentarHp(10)
//  }
//}


//case class PalitoMagico extends Item (0,-20,0,20,"inteligencia")
//case object ArmaduraEleganteSport extends Item (-5,0,10,0,"velocidad")
//case object ArcoViejo extends Item
//case object EscudoAntirrobo extends Item
//case object TalismanDedicacion extends Item
//case object TalismanMinimalista extends Item
//case object VinchaBufaloAgua extends Item
//case object TalismanMaldito extends Item
//case object EspadaDeLaVida extends Item
//
//class Inventario(val items:Set[Item]){
//  def agregarItem(item: Item,unHeroe:Heroe) = {
//    if (item.validarRestriccion(unHeroe)){
//      items.add(item)
//      item.agregar(unHeroe)
//    }
//  }
//}

//class Heroe (val stats: Stats,val trabajo: Trabajo, val inventario:Inventario){
//
////  var inventory: Set[Item] = Set[Item]()
//
//
//  def fuerza():Int = {
//    stats.fuerza //+inventory.fold(0) { (item, i) => a + i}
//  }
//  def hp():Int = {
//    stats.hp
//  }
//  def agregarItem(unItem:Item): Unit ={
//    inventario.agregarItem(unItem,this)
//  }
//
////  def fuertaItems(): Int ={
////    //inventory.fold(0){(item, x)=> x + item.recibe}
////  }
//}
