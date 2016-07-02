//  Items
case class Item(name: String, precio: Int, parte: Posicion, efecto: (Heroe => Stats), condicion: (Heroe => Boolean)){

  def aplicarEfecto(h: Heroe): Heroe = h.copy(stats = efecto(h))
  def validarCondicion(unHeroe:Heroe):Boolean = condicion(unHeroe)
  def validaMismaParte(otroItem: Item): Boolean = {
    parte != otroItem.parte ||
      parte == Cuello ||
      (if (parte == Mano(1) && otroItem.parte == Mano(2)) parte != Mano(1)
      else if (parte==Mano(2) && otroItem.parte == Mano(1)) parte != Mano(2)
      else false)
  }
}


//  Inventario
case class Inventario(items: List[Item] = Nil){

  def cantidadItems = items.size

  def meter(item: Item) = copy(items = item :: items.filter(_.validaMismaParte(item)))

  def sacar(item: Item) = copy(items = items.filter(_ != item))

  def sumarTodosAtributos(unHeroe: Heroe) : Stats = {
    val nuevoStat = unHeroe.trabajo match {
        case Some(x) => x.sumarAtributos(unHeroe.stats)
        case None => unHeroe.stats
      }
    val h = unHeroe.copy(stats = nuevoStat)
    items.foldRight(h) ((unItem,heroe) => unItem.aplicarEfecto(heroe)).stats// regreso los nuevos atributos
  }
}