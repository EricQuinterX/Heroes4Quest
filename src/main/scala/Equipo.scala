import scala.util.Try

//case object EquipoVacioException extends Exception
//case object NoHacerNingunaMisionException extends Exception

//trait ResultadoEntrenamiento
//case class EntrenamientoFallido(t: Tarea) extends ResultadoEntrenamiento
//case class EntrenamientoSuperado(e: Equipo) extends ResultadoEntrenamiento

case class Equipo (name: String, heroes: List[Heroe], pozoDeOro: Int = 0, lider: Option[Heroe] = None) {

//  Punto 2:5
  def mejorHeroeSegun(f: Heroe => Int): Option[Heroe] = Try(Some(heroes.maxBy(f(_)))) getOrElse None
//  heroes match {
//    case Nil => None
//    case _ => Some(heroes.maxBy(f(_)))
//  }

  def obtieneItem(item: Item): Equipo = {
    val candidato = heroes.foldLeft(heroes.headOption) { (resultado, unHeroe) =>
      val h2 = unHeroe.nivelMejora(item)
      val h1 = Try(resultado.get.nivelMejora(item)) getOrElse (h2 - 1)
      if (h1 > h2) resultado else Some(unHeroe)
    }
    if (Try(candidato.get.nivelMejora(item)>0) getOrElse false) {
      val elegido = candidato.get.equiparseItem(item)
      reemplazarMiembro(elegido,candidato.get)
    }
    else
      copy(pozoDeOro = pozoDeOro + item.precio)
  }

  def obtieneMiembro(unHeroe: Heroe) = copy(heroes = unHeroe :: heroes)

  def reemplazarMiembro(nuevoHeroe: Heroe, viejoHeroe: Heroe) = copy(heroes= nuevoHeroe :: heroes.filter(_ != viejoHeroe))

  def obtenerLider(): Equipo = {
    if (heroes.isEmpty) {
      copy(lider = None)
    }
    else {
      val posibleLider = heroes.foldLeft(heroes.head) { (resultado, heroe) =>
        if (resultado.atributos().principal(resultado.trabajo) > heroe.atributos().principal(heroe.trabajo))
          resultado
        else heroe
      }
      val att_principal = posibleLider.atributos().principal(posibleLider.trabajo)
      val otraLista = heroes.filter(x => x.atributos().principal(x.trabajo) == att_principal)
      if (otraLista.size == 1) copy(lider = Some(posibleLider)) else copy(lider = None)

    }
  }


  //Punto 3
  def facilidadPelearContraMonstruo():      Int = if (obtenerLider().lider.get.trabajaDe(Guerrero)) 20 else 10
  def facilidadForzarPuerta(unHeroe:Heroe): Int = unHeroe.atributos().inteligencia + heroes.count(_.trabajaDe(Ladron)) *10
  def facilidadRobarTalisman(unHeroe:Heroe):Int = if (obtenerLider().lider.get.trabajaDe(Ladron)) unHeroe.atributos().velocidad else -1


  //Aqui rompe, lo que hay que hacer es preguntar por cada tarea que heroe tiene mayor facilidad
  // luego devolverlo
//  def mayorFacilidad_De_Realizar(tareas: List[Tarea]):Heroe= {
//    val listaNumeros = heroes.map(_.esMejorPara(tareas.head, this))
//
//    val zorro = new Heroe(new Stats(25,25,25,25),Some(Ladron))
//    zorro
//  }

}