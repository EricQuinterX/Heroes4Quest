/**
  * Created by jon-VBox-w7 on 17/06/2016.
  */
class Stats (var hp:Int , val fuerza:Int , val velocidad:Int,val inteligencia:Int){
  def aumentarHp(hpaux:Int): Unit ={
    hp=hp+hpaux
  }
}
