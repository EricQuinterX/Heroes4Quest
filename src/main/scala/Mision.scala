
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




//  //  Punto 3
//  def hacerMision(m: Mision): ResultadoMision = m.ejecutarMision(this)
//
//  //  Punto 4
//  def elegirMejorMision(tablon: List[Mision], criterio: ((Equipo,Equipo)=>Boolean)): Mision = tablon match {
//    case Nil => throw NoHacerNingunaMisionException
//    case x :: resto => resto.foldLeft(x)((m1,m2) => {
//      val eq1 = copy().hacerMision(m1) match {
//        case MisionFallida(_,_) => None
//        case MisionSuperada(e1) => Some(e1)
//      }
//      val eq2 = copy().hacerMision(m2) match {
//        case MisionFallida(_,_) => None
//        case MisionSuperada(e2) => Some(e2)
//      }
//      if (eq1.isEmpty) m2
//      else if (eq2.isEmpty) m1
//      else if (criterio(eq1.get, eq2.get)) m1
//      else m2
//    })
//  }
//
//  def entrenar(misiones: List[Mision], criterio: (Equipo,Equipo)=>Boolean): ResultadoEntrenamiento = {
//    var i = true
//    var ta: Option[Tarea] = None
//    var equipo = copy()
//    while (misiones.nonEmpty && i) {
//      val m = elegirMejorMision(misiones, criterio)
//      equipo.hacerMision(m) match {
//        case MisionSuperada(e) => equipo = e
//        case MisionFallida(e,t) => i = false; ta = Some(t)
//      }
//    }
//    if (i) new EntrenamientoSuperado(equipo)
//    else new EntrenamientoFallido(ta.get)
//  }

}