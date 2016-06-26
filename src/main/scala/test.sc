object test{
  class Clase(num:Int){

    def valor(): Int ={
      num
    }
  }


  var o1= new Clase(14)
  var o2= new Clase(14)
  var o3= new Clase(14)

  val miLista = List(o1,o2,o3)


  val algo= miLista.foldLeft(miLista.head){(res,objeto) => if (res.valor() > objeto.valor())res else objeto}

  miLista.filter(_.valor() ==algo.valor())
//  def validar(unaClase:Clase,otraClase:Clase): Clase ={
//
//    if (unaClase.valor() > otraClase.valor()) unaClase
//    else if (unaClase.valor() < otraClase.valor()) otraClase
//    else null
//   }




}