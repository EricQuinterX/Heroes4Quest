/* Equipo
Consideraciones:
  - Cuando se crea un equipo, se podra o no definir los heroes
  - No se debera meter luego de haber creado al equipo, a excepcion que se unan heroes
   despues de realizar misiones como recompensa.
 */
case class Equipo (name: String, var heroes: List[Heroe],var pozoDeOro: Int = 0) {



  def lider(): Option[Heroe] = heroes match {
    case Nil => None
    case p1 :: Nil => Some(p1)
    case x =>
      val lider1 = heroes.maxBy(_.atributoPrincipal())
      val lider2 = heroes.filter(_ != lider1).maxBy(_.atributoPrincipal())
      if (lider1.atributoPrincipal()==lider2.atributoPrincipal())
        None
      else
        Some(lider1)
  }

//  def mejorHeroeSegun(f: Heroe => Int) = heroes.maxBy(f(_))
  def mejorHeroeSegun(f: Heroe => Int): Heroe = {
    if (heroes.isEmpty) throw new IllegalArgumentException("No tiene miembros")
    heroes.maxBy(f(_))
}


  def obtieneItem(item: Item): Unit = {
    if (heroes.isEmpty) return
    heroes.filter(h => h.nivelMejora(item) > 0 && item.validarCondicion(h)) match {
      case Nil => pozoDeOro += item.precio
      case x :: Nil => x.equiparseItem(item)
      case hs => hs.maxBy(_.nivelMejora(item)).equiparseItem(item) //by jon: (Pregunta hecha a los ayudantes) Que pasa si hay dos heroes que presentan igual incremento?
    }
  }

  def obtieneMiembro(unHeroe: Heroe) = copy(heroes = unHeroe :: heroes)

  def reemplazarMiembro(nuevoHeroe: Heroe, viejoHeroe: Heroe) = heroes = nuevoHeroe :: heroes.filter(_ != viejoHeroe)
}