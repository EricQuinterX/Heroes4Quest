import scala.util.Try

trait ResultadoTarea
case object TareaFallida extends ResultadoTarea
case object TareaSuperada extends ResultadoTarea
case object TareaPorHacer extends ResultadoTarea // case object sustituto del Option --> None


case class Tarea (name: String,
                  condicion: (Equipo => Boolean),
                  facilidad: ((Heroe, Equipo) => Int),
                  efecto:((Heroe, Equipo) => Equipo),
                  equipo: Option[Equipo] = None,
                  resultado: ResultadoTarea = TareaPorHacer){

  def realizarTarea(unEquipo: Equipo): Tarea = {
    if (resultado == TareaFallida) return this // No ejecuto la tarea en caso que haya fallado la anterior
    val elegido: Option[Heroe] = if (condicion(unEquipo)) unEquipo.mejorHeroeSegun(facilidad(_,unEquipo)) else None
    Try{ // por si elegido es None
      val e: Equipo = efecto(elegido.get, unEquipo) // existen efectos para el heroe y/o equipo
      copy(equipo = Some(e), resultado = TareaSuperada)
    } getOrElse copy(equipo = Some(unEquipo), resultado = TareaFallida)
  }
}








