
class Trabajo(hp: Int, fuerza: Int, velocidad: Int, inteligencia: Int, principal: StatPrincipal) {

  def sumarAtributos(e : Stats) : Stats =
    e.setear(e.copy(hp = e.hp + hp,
      fuerza = e.fuerza + fuerza,
      velocidad = e.velocidad + velocidad,
      inteligencia = e.inteligencia + inteligencia))

  def valorAtributoPrincipal(): Int = {
    principal match {
      case Fuerza => fuerza
      case Inteligencia => inteligencia
      case Velocidad => velocidad
    }
  }
}

case object Guerrero extends Trabajo (10,15,0,-10,Fuerza)
case object Mago extends Trabajo (0,-20,0,20,Inteligencia)
case object Ladron extends Trabajo (-5,0,10,0,Velocidad)