object test{

    type funcionTarea =(Int =>Int)
  //def mapTarea(f:Int=>Int,valor:Int)= f(valor)

  val porDos:funcionTarea = _ * 2
  val cuadrado:funcionTarea = {(a:Int) => a*a}
  val suma:funcionTarea = (a:Int) => a + 50


  //mapTarea(porDos,8)
  //mapTarea(cuadrado,9)

  val miLista = List(porDos,cuadrado,suma)

  def aplicandoMap_Funciones (valor:Int)= miLista.map{(t:funcionTarea)=>t(valor)}

  def aplicandoFold_Funciones (valor:Int)= miLista.foldLeft(valor) { (res: Int, t: funcionTarea) =>  t(res) }
  aplicandoFold_Funciones(5)



// var o1= new Clase(14)
// var o2= new Clase(14)
// var o3= new Clase(14)
//
// val miLista = List(o1,o2,o3)
//
//
// val algo= miLista.foldLeft(miLista.head){(res,objeto) => if (res.valor() > objeto.valor())res else objeto}
//
//  miLista.filter(_.valor() ==algo.valor())
//  def validar(unaClase:Clase,otraClase:Clase): Clase ={
//
//    if (unaClase.valor() > otraClase.valor()) unaClase
//    else if (unaClase.valor() < otraClase.valor()) otraClase
//    else null
//   }




}