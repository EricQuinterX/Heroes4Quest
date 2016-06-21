import org.junit.Assert._
import org.junit.Test

class TrabajoTest {

  //Test para validar trabajo de Guerreros
  @Test
  def heroeTrabajaDeGuerrero(): Unit = {
    val juan: Heroe = new Heroe(new Stats(100,50,30,10))
    juan.adquirirTrabajo(Guerrero)
    assertEquals(juan.atributos().fuerza, 65)
    assertEquals(juan.stats.fuerza, 50)
  }

  @Test
  def heroeDejaDeTrabajarDeGuerrero(): Unit = {
    val gago = new Heroe(new Stats(10,10,10,10))
    gago.adquirirTrabajo(Guerrero) // (10,15,0,-10)
    assertEquals(gago.atributos(), new Stats(20,25,10,1))
    gago.dejarTrabajo()
    assertEquals(gago.atributos(), new Stats(10,10,10,10))
  }

  //Test para validar trabajo de Mago
  @Test
  def heroeTrabajaDeMago(): Unit = {
    val harry = new Heroe(new Stats(20,20,20,20))
    harry.adquirirTrabajo(Mago) // (0,-20,0,20)
    assertEquals(harry.atributos(), new Stats(20,1,20,40))
  }

  @Test
  def heroeDejaDeTrabajarDeMago(): Unit = {
    val harry = new Heroe(new Stats(10,10,10,10))
    harry.adquirirTrabajo(Mago) // (0,-20,0,20)
    assertEquals(harry.atributos(), new Stats(10,1,10,30))
    harry.dejarTrabajo()
    assertEquals(harry.atributos(), new Stats(10,10,10,10))
  }

  //Test para validar trabajo de Ladron
  //Stats (hp: Int, fuerza: Int, velocidad: Int, inteligencia: Int)
  @Test
  def heroeTrabajaDeLadron(): Unit = {
    val harry = new Heroe(new Stats(20,20,20,20))
    harry.adquirirTrabajo(Ladron) // (-5,0,+10,0)
    assertEquals(harry.atributos(), new Stats(15,20,30,20))
  }

  @Test
  def heroeDejaDeTrabajarDeLadron(): Unit = {
    val harry = new Heroe(new Stats(1,10,10,10))
    harry.adquirirTrabajo(Ladron) // (-5,0,+10,0)
    assertEquals(harry.atributos(), new Stats(1,10,20,10))
    harry.dejarTrabajo()
    assertEquals(harry.atributos(), new Stats(1,10,10,10))
  }

   //Test para validar el cambio de trabajo, pasa de Ladron a Guerrero
  @Test
  def heroeTrabajaDeLadronLuegoGuerrero(): Unit = {
    val harry = new Heroe(new Stats(20,20,20,20))
    harry.adquirirTrabajo(Ladron)   // (-5,0,+10,0)
    harry.adquirirTrabajo(Guerrero) // (10,15,0,-10)
    assertEquals(harry.atributos(), new Stats(30,35,20,10))
  }

  //Stats (hp: Int, fuerza: Int, velocidad: Int, inteligencia: Int)
  //Test para validar el cambio de trabajo, pasa de Ladron a Guerrero
  // y por ultimo vuelve a stat original
  @Test
  def heroeTrabajaDeLadronLuegoGuerreroYLuegoRenuncia(): Unit = {
    val harry = new Heroe(new Stats(20,20,20,20))
    harry.adquirirTrabajo(Ladron)   // (-5,0,+10,0)
    harry.adquirirTrabajo(Guerrero) // (10,15,0,-10)
    harry.dejarTrabajo()
    assertEquals(harry.atributos(),new Stats(20,20,20,20))
  }




}
