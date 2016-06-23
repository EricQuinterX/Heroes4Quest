trait ResultadoTarea
case class Superada(e: Equipo) extends ResultadoTarea
case class Derrotada(e: Equipo) extends ResultadoTarea

class Mision (tareas: List[Tarea], botin: (Equipo => Unit)){



}