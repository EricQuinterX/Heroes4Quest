import scala.util.Try

case class Equipo (name: String, heroes: List[Heroe], pozoDeOro: Int = 0){

  def mejorHeroeSegun(f: Heroe => Int): Option[Heroe] = Try(Some(heroes.maxBy(f(_)))) getOrElse None

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
      vender(item)
  }

  def vender(unItem: Item) = copy(pozoDeOro = pozoDeOro + unItem.precio)

  def obtieneMiembro(unHeroe: Heroe) = copy(heroes = unHeroe :: heroes)

  def reemplazarMiembro(nuevoHeroe: Heroe, viejoHeroe: Heroe) = copy(heroes= nuevoHeroe :: heroes.filter(_ != viejoHeroe))

  def lider : Option[Heroe]= heroes match {
    case Nil => None
    case hs =>
      val candidato = hs.maxBy(h => h.atributos.principal(h.trabajo))
      val ppal = candidato.atributos.principal(candidato.trabajo)
      if (hs.count(h => h.atributos.principal(h.trabajo) == ppal) > 1) None
      else Some(candidato)
  }
}