/**
  * Created by jon-VBox-w7 on 17/06/2016.
  */
abstract class Trabajo(val hp:Int,val fuerza:Int,val velocidad:Int,val inteligencia:Int,val stat_principal:String)
case object Guerrero extends Trabajo (10,15,0,-10,"fuerza")
case object Mago extends Trabajo (0,-20,0,20,"inteligencia")
case object Ladron extends Trabajo (-5,0,10,0,"velocidad")
