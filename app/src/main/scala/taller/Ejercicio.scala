package taller

object Ejercicio {

  // ---------- Punto 1 ----------
  def areaTotal(figuras: List[Figura]): Double = figuras match {
    case Nil     => 0.0
    case f :: fs => f.area + areaTotal(fs)
  }

  // ---------- Auxiliares para los puntos 2 y 3 ----------
  def pertenece(x: Int, l: List[Int]): Boolean = l match {
    case Nil     => false
    case h :: t  => if (h == x) true else pertenece(x, t)
  }

  // Inserta x en una lista ordenada ascendente, sin repetirlo
  def insertar(x: Int, l: List[Int]): List[Int] = l match {
    case Nil    => List(x)
    case h :: t =>
      if (x == h) l
      else if (x < h) x :: l
      else h :: insertar(x, t)
  }

  // ---------- Punto 2 ----------
  def sinRepetidos(l: List[Int]): List[Int] = l match {
    case Nil    => Nil
    case h :: t => insertar(h, sinRepetidos(t))
  }

  // ---------- Punto 3 ----------
  def comunes(l1: List[Int], l2: List[Int]): List[Int] = l1 match {
    case Nil    => Nil
    case h :: t =>
      if (pertenece(h, l2)) insertar(h, comunes(t, l2))
      else comunes(t, l2)
  }

  // ---------- Punto 4 ----------
  def evaluar(e: Expr): Int = e match {
    case Numero(v)   => v
    case Suma(a, b)  => evaluar(a) + evaluar(b)
    case Resta(a, b) => evaluar(a) - evaluar(b)
    case Prod(a, b)  => evaluar(a) * evaluar(b)
  }

  private def esSumaOResta(e: Expr): Boolean = e match {
    case Suma(_, _) | Resta(_, _) => true
    case _                        => false
  }

  private def conParentesis(e: Expr): String =
    if (esSumaOResta(e)) "(" + mostrar(e) + ")" else mostrar(e)

  def mostrar(e: Expr): String = e match {
    case Numero(v)   => v.toString
    case Suma(a, b)  => mostrar(a) + " + " + mostrar(b)
    case Resta(a, b) => mostrar(a) + " - " + conParentesis(b) match {
      case s => s
    }
    case Prod(a, b)  => conParentesis(a) + " * " + conParentesis(b)
  }

  def simplificar(e: Expr): Expr = e match {
    case Numero(_) => e

    case Suma(a, b) => (simplificar(a), simplificar(b)) match {
      case (Numero(0), y) => y
      case (x, Numero(0)) => x
      case (x, y)         => Suma(x, y)
    }

    case Resta(a, b) => (simplificar(a), simplificar(b)) match {
      case (x, Numero(0))       => x
      case (x, y) if x == y     => Numero(0)
      case (x, y)               => Resta(x, y)
    }

    case Prod(a, b) => (simplificar(a), simplificar(b)) match {
      case (Numero(0), _) => Numero(0)
      case (_, Numero(0)) => Numero(0)
      case (Numero(1), y) => y
      case (x, Numero(1)) => x
      case (x, y)         => Prod(x, y)
    }
  }
}