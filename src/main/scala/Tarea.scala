trait ResultadoTarea
case class TareaSuperada(e: Equipo) extends ResultadoTarea
case class TareaFallida(e: Equipo, t: Tarea) extends ResultadoTarea


class Tarea (name: String,
             condicion: (Equipo => Boolean),
             fxFacilidad: (Heroe => Int),
             efecto: (Heroe => Heroe)){

  def puedeRealizarTarea(unEquipo: Equipo): ResultadoTarea = {
    if (!condicion(unEquipo)) TareaFallida(unEquipo, this)

    unEquipo.mejorHeroeSegun(fxFacilidad) match {
      case None => TareaFallida(unEquipo, this)
      case  Some(mejorHeroe) => efecto(mejorHeroe)
                                TareaSuperada(unEquipo)
    }
  }
}



