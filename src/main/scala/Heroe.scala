import scala.collection.mutable.Set


class Item (val name: String, val precio: Int, val restriccion: (Heroe) => Boolean){
  def validarRestriccion(unHeroe: Heroe): Boolean = restriccion(unHeroe)
}


class Heroe (val name: String, val stats: Stats){

  var inventory: Set[Item] = Set[Item]()

  def agregarItem(item: Item) = {
    if (item.validarRestriccion(this)){
      inventory.add(item)
    }
  }


}
