import collection.{mutable => m}


// Stats
case class Stats (hp: Int,
                  fuerza: Int,
                  velocidad: Int,
                  inteligencia: Int){
  def atributoMaximo() = fuerza max velocidad max inteligencia
  def subirATodos(i: Int): Stats = copy(hp= hp + i, fuerza = fuerza + i, velocidad = velocidad + i, inteligencia = inteligencia +i)
  def subirHp(i: Int): Stats = copy(hp= hp + i)
  def subirFuerza(i: Int): Stats = copy(fuerza= fuerza + i)
  def subirInteligencia(i: Int): Stats = copy(inteligencia= inteligencia + i)
  def subirVelocidad(i: Int): Stats = copy(velocidad= velocidad + i)
  def verificarNoNegativos(): Stats= {
    if (hp < 1) copy(hp = 1).verificarNoNegativos()
    else if (fuerza < 1) copy(fuerza = 1).verificarNoNegativos()
    else if (inteligencia < 1) copy(inteligencia = 1).verificarNoNegativos()
    else if (velocidad < 1) copy(velocidad = 1).verificarNoNegativos()
    else this
  }
}

trait StatPrincipal
case object Fuerza extends StatPrincipal
case object Inteligencia extends StatPrincipal
case object Velocidad extends StatPrincipal

class Trabajo(hp: Int, fuerza: Int, velocidad: Int, inteligencia: Int, principal: StatPrincipal) {
  def sumarAtributos(e : Stats) : Stats = e.copy(hp = e.hp + hp, fuerza = e.fuerza + fuerza, velocidad = e.velocidad + velocidad, inteligencia = e.inteligencia + inteligencia)
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

abstract class Posicion(val cantidad: Int = 1)
case object Cabeza extends Posicion
case class Mano(override val cantidad: Int) extends Posicion
case object Pecho extends Posicion
case object Cuello extends Posicion

// Heroe
case class Heroe (stats: Stats, var inventario: Inventario = new Inventario(), var trabajo: Option[Trabajo] = None){

  def trabajaDe(unTrabajo: Trabajo) = trabajo match {case Some(x) => x == unTrabajo; case None => false}

  def atributos() : Stats = inventario.sumarTodosAtributos(this)

  def atributoPrincipal() : Int = trabajo match {
    case Some(Guerrero) => atributos().fuerza
    case Some(Mago) => atributos().inteligencia
    case Some(Ladron) => atributos().velocidad
    case None => atributos().atributoMaximo() // En caso que no tenga trabajo elijo el atributo maximo
  }

  def equiparseItem(item: Item) = if (item.validarCondicion(this)) inventario.reemplazaritemM(item)

  def nivelMejora(itemNuevo: Item) : Int = {
    val beneViejoItem: Int = atributoPrincipal()
    val beneNuevoitem: Int = copy(inventario = inventario.reemplazaritemIM(itemNuevo)).atributoPrincipal()
    beneNuevoitem - beneViejoItem
  }

  def dejarTrabajo() = trabajo = None

  def adquirirTrabajo(unTrabajo: Trabajo) = trabajo = Some(unTrabajo)
}


case class Inventario(var items: m.Set[Item] = m.Set[Item]().empty){

  def cantidadItems = items.size
//  Metodos Inmutables(IM): reemplazaritemIM, meterItemIM, sacarItemIM
  def reemplazaritemIM(item: Item): Inventario = sacarItemIM(item.parte).meterItemIM(item)
  def meterItemIM(item: Item): Inventario = {
    copy(items = items += item)
  }
  def sacarItemIM(parte: Posicion): Inventario = {
    //  Para meter un item debo sacar el itemviejo que sea de la misma parte del cuerpo a excepcion de los item ubicados en el cuello
    items find (x => x.parte == parte && x.parte != Cuello) match {
      case Some(x) => items -= x
    }
    copy(items = items)
  } //  Fin de los metodos Inmutables

  def sumarTodosAtributos(unHeroe: Heroe) : Stats = {
    val nuevoStat =
      unHeroe.trabajo match {
        case Some(x) => x.sumarAtributos(unHeroe.stats)
        case None => unHeroe.stats
      }
    val h2 = unHeroe.copy(stats = nuevoStat.verificarNoNegativos())
    items.foldLeft (h2) ((a,i) => i.aplicarEfecto(a)).stats.verificarNoNegativos()// regreso los nuevos atributos
  }

  def reemplazaritemM(item: Item): Unit = { // hace efecto colateral sobre la lista de items
    items find (x => x.parte == item.parte && x.parte != Cuello) match {
      case Some(x) => items -= x
      case _ => ;
    }
    items += item
  }

}

//Items
case class Item(name: String, precio: Int, parte: Posicion, efecto: (Heroe => Stats), condicion: (Heroe => Boolean)){

  def validarCondicion(unHeroe:Heroe):Boolean = condicion(unHeroe)
  def aplicarEfecto(h: Heroe): Heroe = h.copy(stats = efecto(h))
}



case class Equipo (name: String, var heroes: List[Heroe]) {

  var pozoDeOro: Int = 0
  var lider: Option[Heroe] = None

  def mejorHeroeSegun(f: Heroe => Int) = heroes.maxBy(f(_))

  def obtieneItem(item: Item): Unit = {
    if (heroes.isEmpty) return
    heroes.filter(h => h.nivelMejora(item) > 0 && item.validarCondicion(h)) match {
      case Nil => pozoDeOro += item.precio
      case x :: Nil => x.equiparseItem(item)
      case hs => hs.maxBy(_.nivelMejora(item)).equiparseItem(item)
    }
  }

  def obtieneMiembro(unHeroe: Heroe) = copy(heroes = unHeroe :: heroes)

  def reemplazarMiembro(nuevoHeroe: Heroe, viejoHeroe: Heroe): Unit = heroes = nuevoHeroe :: heroes.filter(_ == viejoHeroe)

  def nuevoLider(equipo: List[Heroe]) = {
    heroes match {
      case Nil => lider = None
      case p1 :: Nil => lider = Some(p1)
      case x =>
        val lider1 = heroes.maxBy(_.atributoPrincipal())
        val lider2 = heroes.filter(_ != lider1).maxBy(_.atributoPrincipal())
        if (lider1.atributoPrincipal()==lider2.atributoPrincipal())
          lider = None
        else
          lider = Some(lider1)
    }
  }
}

class Tarea(){}

class Mision (tareas: List[Tarea]){


}