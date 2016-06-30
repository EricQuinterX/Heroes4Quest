//Posiciones de un heroe
abstract class Posicion
case object Cabeza extends Posicion
case class Mano(cantidad: Int) extends Posicion
case object Pecho extends Posicion
case object Cuello extends Posicion


case class Heroe (stats: Stats,
                  trabajo: Option[Trabajo] = None,
                  inventario: Inventario = new Inventario,
                  equipo: Option[Equipo] = None){

  //Funciones generales: 3
  def atributos() : Stats = inventario.sumarTodosAtributos(this)

  def nivelMejora(itemNuevo: Item) : Int = {
    val beneViejoItem: Int = atributos().principal(trabajo)
    val beneNuevoitem: Int = equiparseItem(itemNuevo).atributos().principal(trabajo)
    beneNuevoitem - beneViejoItem
  }

  //Funciones para el trabajo:3
  def trabajaDe(unTrabajo: Trabajo) = trabajo.contains(unTrabajo) // metodo piola
  def dejarTrabajo() = copy(trabajo = None)
  def adquirirTrabajo(unTrabajo: Trabajo) = copy(trabajo = Some(unTrabajo))


  //Funciones para los items:2
  def equiparseItem(item: Item) = if (item.validarCondicion(this)) copy(inventario = inventario.meter(item)) else this
  def descartarItem(item: Item) = copy(inventario = inventario.sacar(item))

  //funciona para las Tareas:3
  def pelearContraMonstruo() : Heroe = copy(stats = stats.copy(hp = 1))
  def forzarPuerta() : Heroe = copy(stats = stats.setear(stats.copy(hp = stats.hp-5,fuerza = stats.fuerza+1)))
  def robarTalisman(i:Item) : Heroe = equiparseItem(i)

}

