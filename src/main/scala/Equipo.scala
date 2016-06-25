
case object EquipoVacioException extends Exception
case object NoHacerNingunaMisionException extends Exception

trait ResultadoEntrenamiento
case class EntrenamientoFallido(t: Tarea) extends ResultadoEntrenamiento
case class EntrenamientoSuperado(e: Equipo) extends ResultadoEntrenamiento



case class Equipo (name: String, heroes: List[Heroe], pozoDeOro: Int) {

//  Punto 2
  def mejorHeroeSegun(f: Heroe => Int): Option[Heroe] = heroes match {
    case Nil => None
    case heroes => Some(heroes.maxBy(f(_)))
  }
/*
  def mejorHeroeSegun(f: Heroe => Int): Heroe = {
    if (heroes.isEmpty) throw EquipoVacioException
    heroes.maxBy(f(_))
*/

  def obtieneItem(item: Item): Unit = {
    if (heroes.isEmpty) return
    heroes.filter(h => h.nivelMejora(item) > 0 && item.validarCondicion(h)) match {
      case Nil => pozoDeOro + item.precio
      case x :: Nil => x.equiparseItem(item)
      case hs => hs.maxBy(_.nivelMejora(item)).equiparseItem(item) //by jon: (Pregunta hecha a los ayudantes) Que pasa si hay dos heroes que presentan igual incremento?
    }
  }

  def obtieneMiembro(unHeroe: Heroe) = copy(heroes = unHeroe :: heroes)

  def reemplazarMiembro(nuevoHeroe: Heroe, viejoHeroe: Heroe) = this.copy(heroes= nuevoHeroe :: heroes.filter(_ != viejoHeroe))//(heroes = nuevoHeroe :: heroes.filter(_ != viejoHeroe))

  def lider(): Option[Heroe] = heroes match {
    case Nil => None
    case p1 :: Nil => Some(p1)
    case x =>
      val lider1 = heroes.maxBy(_.atributoPrincipal())
      val lider2 = heroes.filter(_ != lider1).maxBy(_.atributoPrincipal())
      if (lider1.atributoPrincipal()==lider2.atributoPrincipal()) None
      else Some(lider1)
  }


//  Punto 3
  def hacerMision(m: Mision): ResultadoMision = m.ejecutarMision(this)

//  Punto 4
  def elegirMejorMision(tablon: List[Mision], criterio: ((Equipo,Equipo)=>Boolean)): Mision = tablon match {
    case Nil => throw NoHacerNingunaMisionException
    case x :: resto => resto.foldLeft(x)((m1,m2) => {
      val eq1 = copy().hacerMision(m1) match {
        case MisionFallida(_,_) => None
        case MisionSuperada(e1) => Some(e1)
      }
      val eq2 = copy().hacerMision(m2) match {
        case MisionFallida(_,_) => None
        case MisionSuperada(e2) => Some(e2)
      }
      if (eq1.isEmpty) m2
      else if (eq2.isEmpty) m1
      else if (criterio(eq1.get, eq2.get)) m1
      else m2
    })
  }

  def entrenar(misiones: List[Mision], criterio: (Equipo,Equipo)=>Boolean): ResultadoEntrenamiento = {
    var i = true
    var ta: Option[Tarea] = None
    var equipo = copy()
    while (misiones.nonEmpty && i) {
      val m = elegirMejorMision(misiones, criterio)
      equipo.hacerMision(m) match {
        case MisionSuperada(e) => equipo = e
        case MisionFallida(e,t) => i = false; ta = Some(t)
      }
    }
    if (i) new EntrenamientoSuperado(equipo)
    else new EntrenamientoFallido(ta.get)
  }

}