
trait ResultadoMision
case class MisionSuperada(e: Equipo) extends ResultadoMision
case class MisionFallida (e: Equipo, t: Tarea) extends ResultadoMision

class Mision (tareas: List[Tarea], recompensa: (Equipo => Unit)){

  def ejecutar(e: Equipo, ts: List[Tarea] = tareas): ResultadoMision = ts match {
    case Nil =>
      recompensa(e)
      MisionSuperada(e)
    case t :: resto => t.ejecutar(e) match {
      case TareaSuperada(e1) => ejecutar(e1, resto)
      case TareaFallida(_,tarea) => MisionFallida(e, tarea)
    }
  }
}