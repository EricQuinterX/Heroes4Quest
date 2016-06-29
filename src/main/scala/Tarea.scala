case class Facilidad(condicion_facilidad:(Equipo)=> Boolean, valor: ((Heroe,Equipo) => Int)){

  def condicion_Facilidad(unEquipo:Equipo): Boolean = condicion_facilidad(unEquipo)
  def valorDe_Facilidad(unHeroe:Heroe,unEquipo:Equipo)= valor(unHeroe,unEquipo)

}


case class Tarea (name: String, efecto:((Heroe, Item) => Heroe),facilidad:Facilidad, unItem:Item){

  def realizarTarea(unHeroe:Heroe):Heroe = efecto(unHeroe,unItem)


}








