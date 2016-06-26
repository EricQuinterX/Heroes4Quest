
case object EquipoVacioException extends Exception
case object NoHacerNingunaMisionException extends Exception

trait ResultadoEntrenamiento
case class EntrenamientoFallido(t: Tarea) extends ResultadoEntrenamiento
case class EntrenamientoSuperado(e: Equipo) extends ResultadoEntrenamiento



case class Equipo (name: String, heroes: List[Heroe], pozoDeOro: Int, lider: Option[Heroe] = None) {

//  Punto 2
  def mejorHeroeSegun(f: Heroe => Int): Option[Heroe] = heroes match {
    case Nil => None
    case _ => Some(heroes.maxBy(f(_)))
  }

  def obtieneItem(item: Item): Equipo = {
    if (heroes.exists(item.condicion(_))){
      val unHeroe = heroes.foldLeft(heroes.head){(h1,h2) =>
        if (h1.nivelMejora(item) > h2.nivelMejora(item)) h1 else h2}
      val unHeroePlus = unHeroe.equiparseItem(item)
      reemplazarMiembro(unHeroePlus,unHeroe)
    }else vender(item)
  }

  def vender(item: Item)= copy(pozoDeOro= pozoDeOro+item.precio)

  def obtieneMiembro(unHeroe: Heroe) = copy(heroes = unHeroe :: heroes)

  def reemplazarMiembro(nuevoHeroe: Heroe, viejoHeroe: Heroe) = copy(heroes= nuevoHeroe :: heroes.filter(_ != viejoHeroe))

  def obtenerLider(): Equipo =  {
    if (heroes.isEmpty){
      copy(lider = None)
    }else{
      val posibleLibre = heroes.foldLeft(heroes.head){(resultado,heroe) =>
        if (resultado.atributoPrincipal() > heroe.atributoPrincipal()) resultado else heroe}
      val otraLista    = heroes.filter(_.atributoPrincipal()==posibleLibre.atributoPrincipal())
      if(otraLista.size==1) copy(lider = Some(posibleLibre))  else copy(lider = None)
    }

  }







}