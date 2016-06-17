/**
  * Created by Eric on 17/06/2016.
  */

class Stat (val fuerza: Int){


}

//val nuevoStat = unHeroe.stat.copy(fuerza + 10)
//val nuevoHeroe = unHeroe.copy(nuevoStat)
//return nuevoHeroe




class Item (val name: String, val precio: Int, val restriccion: (Heroe) => Boolean){
  def validarRestriccion(unHeroe: Heroe): Boolean = restriccion(unHeroe)
}


class Heroe (val name: String, val stat: Stat, var inventory: List[Item]){

  def agregarItem(item: Item) = {
    if (item.validarRestriccion(this)){

      inventory :+ item
    }
  }


}
