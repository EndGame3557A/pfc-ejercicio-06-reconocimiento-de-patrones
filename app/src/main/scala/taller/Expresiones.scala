package taller

// Una expresión aritmética es un número o una operación entre dos
// expresiones. La jerarquía está sellada: estas cuatro formas son todas.
sealed trait Expr
case class Numero(valor: Int) extends Expr
case class Suma(e1: Expr, e2: Expr) extends Expr
case class Resta(e1: Expr, e2: Expr) extends Expr
case class Prod(e1: Expr, e2: Expr) extends Expr
