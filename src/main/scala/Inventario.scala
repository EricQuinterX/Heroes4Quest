import collection.{mutable => m}

//Items
case class Item(name: String, precio: Int, parte: Posicion, efecto: (Heroe => Stats), condicion: (Heroe => Boolean)){

  def aplicarEfecto(h: Heroe): Heroe = h.copy(stats = efecto(h))
  def validarCondicion(unHeroe:Heroe):Boolean = condicion(unHeroe)
}

case class Inventario(var items: m.Set[Item] = m.Set[Item]().empty){

  def cantidadItems = items.size
  //  Metodos Inmutables(IM): reemplazaritemIM, meterItemIM, sacarItemIM
  def reemplazaritemIM(item: Item): Inventario = sacarItemIM(item.parte).meterItemIM(item)
  def meterItemIM(item: Item): Inventario = {
    copy(items = items += item)
  }
  def sacarItemIM(parte: Posicion): Inventario = {
    //  Para meter un item debo sacar el itemviejo que sea de la misma parte del cuerpo a excepcion de los item ubicados en el cuello
    items find (x => x.parte == parte && x.parte != Cuello) match {
      case Some(x) => items -= x
    }
    copy(items = items)
  } //  Fin de los metodos Inmutables

  def sumarTodosAtributos(unHeroe: Heroe) : Stats = {
    val nuevoStat =
      unHeroe.trabajo match {
        case Some(x) => x.sumarAtributos(unHeroe.stats)
        case None => unHeroe.stats
      }
    val h2 = unHeroe.copy(stats = nuevoStat.verificarNoNegativos())
    items.foldLeft (h2) ((a,i) => i.aplicarEfecto(a)).stats.verificarNoNegativos()// regreso los nuevos atributos
  }

  def reemplazarItem(item: Item): Unit = { // hace efecto colateral sobre la lista de items
    items find (x => x.parte == item.parte && x.parte != Cuello) match {
      case Some(x) => items -= x
      case _ => ;
    }
    items += item
  }

  def validarDescarte(item: Item): Boolean = {
    items.contains(item)
  }


  def descartarItem(item: Item): Unit = {
    items -= item
  }

}