
//Posiciones de un heroe
abstract class Posicion(val cantidad: Int = 1)
case object Cabeza extends Posicion
case class Mano(override val cantidad: Int) extends Posicion
case object Pecho extends Posicion
case object Cuello extends Posicion


case class Heroe (stats: Stats, var trabajo: Option[Trabajo] = None, var inventario: Inventario = new Inventario(), var equipo:Option[Equipo]=None){

  def trabajaDe(unTrabajo: Trabajo) = trabajo match {case Some(x) => x == unTrabajo; case None => false}

  def atributos() : Stats = inventario.sumarTodosAtributos(this)

  def atributoPrincipal() : Int = trabajo match {
    case Some(Guerrero) => atributos().fuerza
    case Some(Mago) => atributos().inteligencia
    case Some(Ladron) => atributos().velocidad
    case None => atributos().atributoMaximo() // En caso que no tenga trabajo elijo el atributo maximo
  }

  def equiparseItem(item: Item) = if (item.validarCondicion(this)) inventario.reemplazarItem(item)
  def descartarItem(item: Item) = inventario.descartarItem(item)

  def nivelMejora(itemNuevo: Item) : Int = {
    val beneViejoItem: Int = atributoPrincipal()
    val beneNuevoitem: Int = copy(inventario = inventario.reemplazaritemIM(itemNuevo)).atributoPrincipal()
    beneNuevoitem - beneViejoItem
  }

  def dejarTrabajo() = trabajo = None

  def adquirirTrabajo(unTrabajo: Trabajo) = trabajo = Some(unTrabajo)
}

