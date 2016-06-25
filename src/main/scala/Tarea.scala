trait ResultadoTarea
case class TareaSuperada(e: Equipo) extends ResultadoTarea
case class TareaFallida(e: Equipo, t: Tarea) extends ResultadoTarea


class Tarea (name: String,
             condicion: (Equipo => Boolean),
             fxFacilidad: (Heroe => Int),
             efecto: (Heroe => Heroe)){

  def puedeRealizarTarea(unEquipo: Equipo): ResultadoTarea = {
    if (!condicion(unEquipo)) TareaFallida(unEquipo, this)

    unEquipo.mejorHeroeSegun(fxFacilidad) match {
      case None => TareaFallida(unEquipo, this)
      case  Some(mejorHeroe) => efecto(mejorHeroe)
                                TareaSuperada(unEquipo)
    }
  }



  //Consideraciones:
  //by jon: hago que el heroe conozca a su equipo pero se genera algo sucio como lo que sigue, no hago el test porque quiero validar con ustedes.
  //atencion! : Cuando se crea el equipo, se le tiene que avisar a todos los heroes a que equipo pertenecen. Tambien quiero validar esto.
  //Por mi parte, elegiria la opcion 2 que les comente en el houngouts queda menos sucio.
  //No se como sacar el siguiente metodo afuera de la clase, sin tener que ponerlo como val en el Test!!!

}



