import org.junit.Assert.assertEquals
import org.junit.{Before, Test}

class pruebaFacil {
  var foo: Foo = null

  @Before
  def before(): Unit = {
    foo = new Foo
  }

  @Test
  def test(): Unit = {
    assertEquals(1, foo.unidad())
    assertEquals(2, foo.unidad()+1)
  }
}
