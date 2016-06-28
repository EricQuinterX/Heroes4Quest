
class Tarea (name: String, condicion: (Option[Heroe] => Boolean), facilidad: ((Equipo) => Int), condicion_facilidad:(Equipo)=> Boolean, efecto: ((Option[Heroe], Item) => Option[Heroe]),unItem: Item){

  def realizarTarea(unHeroe: Option[Heroe]):Option[Heroe] = efecto(unHeroe,unItem)

  def condicionTarea(unHeroe:Option[Heroe]):Boolean = condicion (unHeroe)

  def condicion_Facilidad(unEquipo:Equipo): Boolean = condicion_facilidad(unEquipo)



}








