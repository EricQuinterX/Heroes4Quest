
trait ResultadoMision
case class MisionSuperada(e: Equipo) extends ResultadoMision
case class MisionFallida (e: Equipo, t: Tarea) extends ResultadoMision

class Mision (tareas: List[Tarea], recompensa: (Equipo => Unit)){

  def ejecutar(e: Equipo, ts: List[Tarea] = tareas): ResultadoMision = ts match {
    case Nil =>
      recompensa(e)
      MisionSuperada(e)
    case tarea :: restoTareas => tarea.puedeRealizarTarea(e) match {
      case TareaPuedeRealizarse(equipo, heroe) => ejecutar(tarea.aplicarEfecto(heroe, equipo), restoTareas)
      case TareaNosePuedeRealizar(_,tarea) => MisionFallida(e, tarea)
    }
  }
}