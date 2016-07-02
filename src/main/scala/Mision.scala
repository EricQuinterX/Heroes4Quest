trait ResultadoMision
case object MisionFallida extends ResultadoMision
case object MisionSuperada extends ResultadoMision
case object MisionPorHacer extends ResultadoMision // case object sustituto del Option --> None


case class Mision (tareas: List[Tarea],
                   recompensa: (Equipo => Equipo),
                   equipo: Option[Equipo] = None,
                   resultado: ResultadoMision = MisionPorHacer,
                   tareaFallida: Option[Tarea] = None){

  def realizarMision(unEquipo: Equipo): Mision = {
    if (resultado == MisionFallida) return this // no ejecuto la mision en caso que la mision anterior haya fallado
    val ultimaTareaFinalizada = tareas.tail.foldLeft(tareas.head.realizarTarea(unEquipo)) { (t1,t2) =>
      t1 match {
        case Tarea(_,_,_,_,Some(teamS), TareaSuperada) => t2.realizarTarea(teamS)
        case x => x
      }
    }
    if (ultimaTareaFinalizada.resultado == TareaSuperada)
      copy(equipo = Some(recompensa(ultimaTareaFinalizada.equipo.get)), resultado = MisionSuperada)
    else
      copy(equipo = Some(unEquipo), resultado = MisionFallida, tareaFallida = Some(ultimaTareaFinalizada))
  }

}