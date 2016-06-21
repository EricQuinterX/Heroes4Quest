
trait StatPrincipal
case object Fuerza       extends StatPrincipal
case object Inteligencia extends StatPrincipal
case object Velocidad    extends StatPrincipal

case class Stats (hp: Int, fuerza: Int, velocidad: Int, inteligencia: Int){

  def atributoMaximo() = fuerza max velocidad max inteligencia
  def subirATodos(i: Int): Stats = copy(hp= hp + i, fuerza = fuerza + i, velocidad = velocidad + i, inteligencia = inteligencia +i)
  def subirHp(i: Int): Stats = copy(hp= hp + i)
  def subirFuerza(i: Int): Stats = copy(fuerza= fuerza + i)
  def subirInteligencia(i: Int): Stats = copy(inteligencia= inteligencia + i)
  def subirVelocidad(i: Int): Stats = copy(velocidad= velocidad + i)
  def subirATodosMenosInteligencia(i: Int): Stats = copy(velocidad= velocidad + i ,fuerza= fuerza + i,hp = hp + i )// Que pasa si seguimos añadiendo restricciones?
  def fuerzaIgualarA(i: Int): Stats = copy(fuerza = i)//by jon: Que pasa si seguimos añadiendo restricciones?

  def verificarNoNegativos(): Stats= {
    if (hp < 1) copy(hp = 1).verificarNoNegativos()
    else if (fuerza < 1) copy(fuerza = 1).verificarNoNegativos()
    else if (inteligencia < 1) copy(inteligencia = 1).verificarNoNegativos()
    else if (velocidad < 1) copy(velocidad = 1).verificarNoNegativos()
    else this
  }
}