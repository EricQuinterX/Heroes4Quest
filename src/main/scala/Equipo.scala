import scala.util.Try

case class Equipo (name: String, heroes: List[Heroe], pozoDeOro: Int = 0, lider: Option[Heroe] = None) {

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

  def obtenerLider: Equipo = {
    if (heroes.isEmpty) {
      copy(lider = None)
    }
    else {
      val posibleLider = heroes.foldLeft(heroes.head) { (resultado, heroe) =>
        if (resultado.atributos.principal(resultado.trabajo) > heroe.atributos.principal(heroe.trabajo))
          resultado
        else heroe
      }
      val att_principal = posibleLider.atributos.principal(posibleLider.trabajo)
      val otraLista = heroes.filter(x => x.atributos.principal(x.trabajo) == att_principal)
      if (otraLista.size == 1) copy(lider = Some(posibleLider)) else copy(lider = None)

    }
  }
}