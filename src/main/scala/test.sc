object test{
  class Clase{

    def valor(): Int ={
      10
    }
  }


  var o1= new Clase()
  var o2= new Clase()
  var o3= new Clase()
  List(o1,o2,o3).foldLeft(0){(res,objeto) => res + objeto.valor()}
}