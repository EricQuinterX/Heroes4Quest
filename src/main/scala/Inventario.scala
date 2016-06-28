
//  Items
case class Item(name: String, precio: Int, parte: Posicion, efecto: (Heroe => Stats), condicion: (Heroe => Boolean)){

  def aplicarEfecto(h: Heroe): Heroe = h.copy(stats = efecto(h))
  def validarCondicion(unHeroe:Heroe):Boolean = condicion(unHeroe)
}


//  Inventario
case class Inventario(items: List[Item] = Nil){

  def cantidadItems = items.size

  def meter(item: Item) = copy(items = (item :: items.filter(_.parte!=item.parte)).reverse)

  def sacar(item: Item) = copy(items = items.filter(_ != item))

  def sumarTodosAtributos(unHeroe: Heroe) : Stats = {
    val nuevoStat = unHeroe.trabajo match {
        case Some(x) => x.sumarAtributos(unHeroe.stats)
        case None => unHeroe.stats
      }
    val h = unHeroe.copy(stats = nuevoStat)
    reductoInventario(h,items)
    //items.foldLeft (h) ((heroe,unItem) => unItem.aplicarEfecto(heroe)).stats// regreso los nuevos atributos
  }

  def reductoInventario(heroe: Heroe,items:List[Item]): Stats = items match {
    case Nil => heroe.copy().stats
    case item :: restoItems => reductoInventario(item.aplicarEfecto(heroe), restoItems)
  }
}