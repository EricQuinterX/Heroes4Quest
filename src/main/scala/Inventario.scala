
//  Items
case class Item(name: String, precio: Int, parte: Posicion, efecto: (Heroe => Stats), condicion: (Heroe => Boolean)){

  def aplicarEfecto(h: Heroe): Heroe = h.copy(stats = efecto(h))
  def validarCondicion(unHeroe:Heroe):Boolean = condicion(unHeroe)
}


//  Inventario
case class Inventario(var items: List[Item] = Nil){

  def cantidadItems = items.size

  def meter(item: Item): Inventario = copy(items = item :: items.filter(_.parte!=item.parte))

  def sacar(item: Item) = copy(items = items.filter(_ != item))

  def sumarTodosAtributos(unHeroe: Heroe) : Stats = {
    val nuevoStat =
      unHeroe.trabajo match {
        case Some(x) => x.sumarAtributos(unHeroe.stats)
        case None => unHeroe.stats
      }
    val h2 = unHeroe.copy(stats = nuevoStat.verificarNoNegativos())
    items.foldLeft (h2) ((a,i) => i.aplicarEfecto(a)).stats.verificarNoNegativos()// regreso los nuevos atributos
  }
}