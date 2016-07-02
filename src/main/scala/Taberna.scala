class Taberna (misiones: List[Mision]) {

  def elegirMision(e: Equipo, criterio: (Equipo,Equipo)=>Boolean): Option[Mision] = {
    val misionCandidata = misiones.foldLeft(misiones.head){(m1,m2) =>
      val e1 = m1.realizarMision(e).equipo.get
      val e2 = m2.realizarMision(e).equipo.get
//      if (e1==e || e2==e)
//        List(m1,m2).find(_.realizarMision(e).equipo.get != e).get
//      else
      if (criterio(e1,e2)) m1 else m2
    }
    if (misionCandidata.equipo.get == e) None
    else Some(misionCandidata)
  }

  def entrenar(e: Equipo, criterio: (Equipo,Equipo)=>Boolean, tablonReducido: List[Mision] = misiones): Equipo = {
    if (tablonReducido.isEmpty) return e
    val mejorMision = elegirMision(e, criterio)
    val tablon = tablonReducido.filter(_ != mejorMision)
    val misionHecha = mejorMision.get.realizarMision(e)
    entrenar(misionHecha.equipo.get,criterio,tablon)
  }
}
