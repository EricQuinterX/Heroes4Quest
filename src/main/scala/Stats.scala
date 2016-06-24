
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
  def setearATodos(i: Int): Stats = copy(hp=i, fuerza= i, velocidad= i, inteligencia= i)
  def setearHp(i: Int): Stats = copy(hp = i)
  def setearFuerza(i: Int): Stats = copy(fuerza = i)
  def setearInteligencia(i: Int): Stats = copy(inteligencia = i)
  def setearVelocidad(i: Int): Stats = copy(velocidad = i)
  def bajarHp(i: Int): Stats = copy(hp= hp - i)

  def verificarNoNegativos(): Stats= {
    if (hp < 1) copy(hp = 1).verificarNoNegativos()
    else if (fuerza < 1) copy(fuerza = 1).verificarNoNegativos()
    else if (inteligencia < 1) copy(inteligencia = 1).verificarNoNegativos()
    else if (velocidad < 1) copy(velocidad = 1).verificarNoNegativos()
    else this
  }
}