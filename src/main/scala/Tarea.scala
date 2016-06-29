import scala.util.Try

case class Facilidad(condicion_facilidad:(Equipo)=> Boolean, valor: ((Heroe,Equipo) => Int)){

  def condicion_Facilidad(unEquipo:Equipo): Boolean = condicion_facilidad(unEquipo)
  def valorDe_Facilidad(unHeroe:Heroe,unEquipo:Equipo)= valor(unHeroe,unEquipo)

}

trait ResultadoTarea
case object TareaFallida extends ResultadoTarea
case object TareaSuperada extends ResultadoTarea
case object TareaPorHacer extends ResultadoTarea // case object sustituto del Option --> None


case class Tarea (name: String,
                  condicion: (Equipo => Boolean),
                  facilidad: (Heroe => Int),
                  efecto:((Heroe, Equipo) => (Heroe, Equipo)),
                  equipo: Option[Equipo] = None,
                  resultado: ResultadoTarea = TareaPorHacer){

  def realizarTarea(unEquipo: Equipo): Tarea = {
    if (resultado == TareaFallida) return this // No ejecuto la tarea en caso que haya fallado la anterior
    val elegido: Option[Heroe] = if (condicion(unEquipo)) unEquipo.mejorHeroeSegun(facilidad) else None
    Try{ // por si elegido es None
      val tupla: (Heroe,Equipo) = efecto(elegido.get, unEquipo) // existen efectos para el heroe y/o equipo
      val newTeam = Option(tupla._2.reemplazarMiembro(tupla._1,elegido.get))
      copy(equipo = newTeam, resultado = TareaSuperada)
    } getOrElse copy(equipo = Some(unEquipo), resultado = TareaFallida)
  }

  /* Eric: Como era case Tarea y misiones, cree unos atributos para que las clases sean inmutables,
      no necesito devolver un Equipo solo*/
//  def aplicarEfecto(mejorHeroe: Heroe, unEquipo: Equipo): Equipo = {
//    val nuevoHeroe = efecto(mejorHeroe)
//    unEquipo.reemplazarMiembro(nuevoHeroe, mejorHeroe)
//  }

    /* Eric: No necesariamente el efecto debe ser entregar un item, puede dar stats, oro u otro personaje,
       eso depende de la funcion efecto*/
//  def realizarTarea(unHeroe:Heroe):Heroe = efecto(unHeroe,unItem)


}








