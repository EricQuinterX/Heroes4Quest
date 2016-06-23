import org.junit.Assert._
import org.junit.Test

/**
  * Created by jon-VBox-w7 on 23/06/2016.
  */
class TareaTest {
  //Test para validar tareas

  val facilidad_pelear_contra_monstruo = (h :Heroe) =>
    h.equipo match {
      case Some(equipo) => equipo.lider() match {
        case Some(lider) => lider.trabajo match {
          case Some(Guerrero) => 20
          case _ => 10
        }
        case _ => 11
      }
      case _ => 12
    }

  val efecto_pelear_contra_monstruo = (h :Heroe) =>
    if (h.stats.fuerza<20) {
      new Heroe(h.stats.setearHp(h.stats.hp - 1),h.trabajo,h.inventario)
      //By jon: Le puse que se reduce 1 por poner un valor cualquiera para probar solamente
    }else{h}

  @Test
  def tareaMasCondicion(): Unit = {
    val jonny = new Heroe(new Stats(10,19,10,10), Some(Guerrero)) //(20,25,10,1)
    val jonas = new Heroe(new Stats(20,20,1,1), Some(Mago)) // (20,1,1,21)
    val matias = new Heroe(new Stats(30,10,5,5), Some(Ladron)) // (25,10,15,5)
    val losDragones = new Equipo("Los Dragones", List(jonny, jonas, matias))
    jonny.unirseAEquipo(losDragones)
    jonas.unirseAEquipo(losDragones)
    matias.unirseAEquipo(losDragones)

    val pelearContraMonstruo = new Tarea(facilidad_pelear_contra_monstruo,efecto_pelear_contra_monstruo,losDragones)

    //facilidad ok
    assertEquals(facilidad_pelear_contra_monstruo(jonas),20)
    assertEquals(losDragones.lider().get,jonny)

    //efecto ok
    assertEquals(efecto_pelear_contra_monstruo(jonny).stats.hp,9)

    //Tarea
    assertEquals(pelearContraMonstruo,9)


  }

}
