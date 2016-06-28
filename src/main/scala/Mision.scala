
class Mision (tareas: List[Tarea], recompensa: Int){

  def condicionTareas(unHeroe: Option[Heroe]): Boolean =  tareas.forall((t:Tarea)=> t.condicionTarea(unHeroe))
  def realizarTareas(unHeroe: Option[Heroe]): Option[Heroe] =  tareas.foldLeft(unHeroe){(res:Option[Heroe], t:Tarea)=>t.realizarTarea(res)}

  //def superaFacilidad(unEquipo:Equipo) =>

  def realizar_Mision(unEquipo: Equipo,unaTarea: Tarea): (Equipo,Boolean) ={

    if (tareas.exists{(t:Tarea)=> t.condicion_Facilidad(unEquipo)}){
      val unHeroe:Option[Heroe] = unEquipo.mayorFacilidad_De_Realizar(unaTarea)

      if (condicionTareas(unHeroe)){
        (realizarTareas(unHeroe),true)
    }else(unEquipo,false)

  }

  }

}