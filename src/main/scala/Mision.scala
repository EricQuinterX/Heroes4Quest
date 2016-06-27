
trait ResultadoMision
case class MisionSuperada(e: Equipo) extends ResultadoMision
case class MisionFallida (e: Equipo, t: Tarea) extends ResultadoMision

class Mision (tareas: List[Tarea], recompensa: (Equipo => Unit)){

  def ejecutarMision(unEquipo: Equipo, tareas: List[Tarea] = tareas): ResultadoMision = tareas match {
    case Nil =>
      recompensa(unEquipo)
      MisionSuperada(unEquipo)
    case tarea :: restoTareas => tarea.puedeRealizarTarea(unEquipo) match {
      case TareaPuedeRealizarse(equipo, heroe) => ejecutarMision(tarea.aplicarEfecto(heroe, equipo), restoTareas)
      case TareaNosePuedeRealizar(_,tarea1) => MisionFallida(unEquipo, tarea1)
    }
  }

  def ejecutarMision2(equipoInicial: Equipo): ResultadoMision = {
      val equipoFinal = tareas.foldLeft(equipoInicial) {(equipo,unaTarea) => realizarTarea(equipo, unaTarea)}
      MisionSuperada(equipoFinal)
  }

  def realizarTarea(unEquipo: Equipo, unaTarea: Tarea): Equipo = {
    unaTarea.puedeRealizarTarea(unEquipo) match{
      case TareaPuedeRealizarse(equipo, heroe) => unaTarea.aplicarEfecto(heroe, equipo)
//      case TareaNosePuedeRealizar(_,tarea) => MisionFallida(unEquipo, tarea)

    }
  }


}