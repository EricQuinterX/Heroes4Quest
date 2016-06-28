
//Posiciones de un heroe
abstract class Posicion
case object Cabeza extends Posicion
case class Mano(cantidad: Int) extends Posicion
case object Pecho extends Posicion
case object Cuello extends Posicion


case class Heroe (stats: Stats, trabajo: Option[Trabajo] = None, inventario: Inventario = new Inventario, equipo: Option[Equipo] = None){

  //Funciones generales: 3
  def atributos() : Stats = inventario.sumarTodosAtributos(this)

  def atributoPrincipal() : Int = trabajo match {
    case Some(Guerrero) => atributos().fuerza
    case Some(Mago) => atributos().inteligencia
    case Some(Ladron) => atributos().velocidad
    case None => atributos().atributoMaximo()
  }

  def nivelMejora(itemNuevo: Item) : Int = {
    val beneViejoItem: Int = atributoPrincipal()
    val beneNuevoitem: Int = equiparseItem(itemNuevo).atributoPrincipal()
    beneNuevoitem - beneViejoItem
  }



  //Funciones para el trabajo:3
  def trabajaDe(unTrabajo: Trabajo) = trabajo match {case Some(x) if x == unTrabajo => true ; case _ => false}
  def dejarTrabajo() = copy(trabajo = None)
  def adquirirTrabajo(unTrabajo: Trabajo) = copy(trabajo = Some(unTrabajo))


  //Funciones para los items:2
  def equiparseItem(item: Item) : Heroe = { if (item.validarCondicion(this)) copy(inventario = inventario.meter(item)) else this}
  def descartarItem(item: Item) = copy(inventario = inventario.sacar(item))


  //Funciones para los equipos:2
  def unirseAEquipo(e: Equipo) = copy(equipo = Some(e))
  def dejarEquipo() = copy(equipo = None)


  //funciona para las Tareas:3
  def pelearContraMonstruo () : Heroe = copy(stats = stats.setear(stats.copy(hp = 1,fuerza = stats.fuerza,velocidad = stats.velocidad,inteligencia = stats.inteligencia)))
  def forzarPuerta         () : Heroe = copy(stats = stats.setear(stats.copy(hp = stats.hp-5,fuerza = stats.fuerza+1,velocidad = stats.velocidad,inteligencia = stats.inteligencia)))
  def robarTalisman  (i:Item) : Heroe = this.equiparseItem(i)

}

