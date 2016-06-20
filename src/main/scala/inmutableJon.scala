//import scala.collection.mutable.Set
//
//type Stats = (Int,Int,Int,Int)
//
//def cv(st: Stats):Stats ={ //CascoVikingo
//  (st._1+10,st._2,st._3,st._4)
//}
//def cv_res(unHeroe: Heroe):Boolean ={  //CascoVikingo Restriccion
//  unHeroe.stats_base._2>30
//}
//
//class Inventario(val items:Set[Item]){
//  def stats(a:Stats):Stats={
//    items.foldLeft (a) ((a,b)=>b.beneficio(a))
//  }
//  def agregar_item(item: Item): Unit ={
//    items += item
//  }
//}
//
//class Heroe (val inventario:Inventario,val stats_base:Stats){
//  def stats():Stats={
//    inventario.stats(stats_base)
//  }
//  def agregar_item(item: Item): Unit ={
//    if (item.restriccion(this)){
//      inventario.agregar_item(item)
//    }
//  }
//}
//class Item(val beneficio:(Stats=>Stats),val restriccion:(Heroe=>Boolean))
//case object CascoVikingo extends Item(cv,cv_res)