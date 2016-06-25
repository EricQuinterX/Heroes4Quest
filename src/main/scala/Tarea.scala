trait ResultadoTarea
case class TareaPuedeRealizarse(equipo: Equipo, heroe: Heroe) extends ResultadoTarea
case class TareaNosePuedeRealizar(e: Equipo, t: Tarea) extends ResultadoTarea


class Tarea (name: String,
             condicion: (Equipo => Boolean),
             fxFacilidad: (Heroe => Int),
             efecto: (Heroe => Heroe)){

  def puedeRealizarTarea(unEquipo: Equipo): ResultadoTarea = {
    if (!condicion(unEquipo)) TareaNosePuedeRealizar(unEquipo, this)

    unEquipo.mejorHeroeSegun(fxFacilidad) match {
      case None => TareaNosePuedeRealizar(unEquipo, this)
      case Some(mejorHeroe) => TareaPuedeRealizarse(unEquipo, mejorHeroe)
    }
  }

  def aplicarEfecto(mejorHeroe: Heroe, unEquipo: Equipo): Equipo = {
      val nuevoHeroe = efecto(mejorHeroe)
      unEquipo.reemplazarMiembro(nuevoHeroe, mejorHeroe)
  }
}



