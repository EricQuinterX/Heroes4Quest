
trait StatPrincipal
case object Fuerza       extends StatPrincipal
case object Inteligencia extends StatPrincipal
case object Velocidad    extends StatPrincipal

case class Stats (hp: Int = 1, fuerza: Int = 1, velocidad: Int = 1, inteligencia: Int = 1){

//  def atributoMaximo() = fuerza max velocidad max inteligencia

  def setear(s: Stats): Stats = {
    copy(hp = if (s.hp<1) 1 else s.hp,
      fuerza = if (s.fuerza<1) 1 else s.fuerza,
      velocidad = if (s.velocidad<1) 1 else s.velocidad,
      inteligencia = if (s.inteligencia<1) 1 else s.inteligencia)
  }

  def principal(unTrabajo: Option[Trabajo]) : Int = unTrabajo match {
    case Some(Guerrero) => fuerza
    case Some(Mago) => inteligencia
    case Some(Ladron) => velocidad
    case None => fuerza max velocidad max inteligencia
  }

}