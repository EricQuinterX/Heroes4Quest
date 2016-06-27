//  Para corregir
class Taberna (tablon: List[Mision]) {

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
//  def entrenar(equipo: Equipo, criterio: (Equipo,Equipo)=>Boolean): ResultadoEntrenamiento = {
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
