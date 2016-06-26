
//Posiciones de un heroe
abstract class Posicion
case object Cabeza extends Posicion
case class Mano(cantidad: Int) extends Posicion
case object Pecho extends Posicion
case object Cuello extends Posicion


case class Heroe (stats: Stats, trabajo: Option[Trabajo] = None, inventario: Inventario = new Inventario, equipo: Option[Equipo] = None){


  def trabajaDe(unTrabajo: Trabajo) = trabajo match {case Some(x) if x == unTrabajo => true ; case _ => false}

  def atributos() : Stats = inventario.sumarTodosAtributos(this)

  def atributoPrincipal() : Int = trabajo match {
    case Some(Guerrero) => atributos().fuerza
    case Some(Mago) => atributos().inteligencia
    case Some(Ladron) => atributos().velocidad
    case None => atributos().atributoMaximo() // En caso que no tenga trabajo elijo el atributo maximo
  }

  def equiparseItem(item: Item) : Heroe = if (item.validarCondicion(this)) copy(inventario = inventario.meter(item)) else this
  def descartarItem(item: Item) = copy(inventario = inventario.sacar(item))

  def nivelMejora(itemNuevo: Item) : Int = {
    val beneViejoItem: Int = atributoPrincipal()
    val beneNuevoitem: Int = copy(inventario = inventario.meter(itemNuevo)).atributoPrincipal()

    beneNuevoitem - beneViejoItem
  }

  def dejarTrabajo() = copy(trabajo = None)

  def adquirirTrabajo(unTrabajo: Trabajo) = copy(trabajo = Some(unTrabajo))

  def unirseAEquipo(e: Equipo) = copy(equipo = Some(e))

  def dejarEquipo() = copy(equipo = None)

}

