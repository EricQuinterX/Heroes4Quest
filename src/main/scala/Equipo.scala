import scala.util.Try

//case object EquipoVacioException extends Exception
//case object NoHacerNingunaMisionException extends Exception

//trait ResultadoEntrenamiento
//case class EntrenamientoFallido(t: Tarea) extends ResultadoEntrenamiento
//case class EntrenamientoSuperado(e: Equipo) extends ResultadoEntrenamiento

case class Equipo (name: String, heroes: List[Heroe], pozoDeOro: Int, lider: Option[Heroe] = None) {

//  Punto 2:5
  def mejorHeroeSegun(f: Heroe => Int): Option[Heroe] = heroes match {
    case Nil => None
    case _ => Some(heroes.maxBy(f(_)))
  }

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
        if (resultado.atributoPrincipal() > heroe.atributoPrincipal()) resultado else heroe
      }
      val otraLista = heroes.filter(_.atributoPrincipal() == posibleLider.atributoPrincipal())
      if (otraLista.size == 1) copy(lider = Some(posibleLider)) else copy(lider = None)

    }
  }


  //Punto 3
  def facilidadPelearContraMonstruo(): Int = if (obtenerLider().lider.get.trabajaDe(Guerrero)) 20 else 10
  def facilidadForzarPuerta(unHeroe:Heroe): Int = unHeroe.atributos().inteligencia + heroes.filter(_.trabajaDe(Ladron)).size *10
  def facilidadRobarTalisman(unHeroe:Heroe):Int = if (obtenerLider().lider.get.trabajaDe(Ladron)) unHeroe.atributos().velocidad else -1



}