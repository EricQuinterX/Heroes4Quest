
case class Mision (tareas: List[Tarea], recompensa: Int){

  def realizar_Mision(unEquipo: Equipo): (Equipo,Boolean) ={

    if (tareas.exists{(t:Tarea)=> t.facilidad.condicion_facilidad(unEquipo)}){
      val heroeConMayorFacilidad = unEquipo.mayorFacilidad_De_Realizar(tareas)

      val  heroeDespuesDeAplicarTareas= tareas.foldLeft(heroeConMayorFacilidad){(res:Heroe, t:Tarea)=>t.realizarTarea(heroeConMayorFacilidad)}

      unEquipo.reemplazarMiembro(heroeDespuesDeAplicarTareas,heroeConMayorFacilidad)
      (unEquipo,true)
    }else(unEquipo,false)

  }

}