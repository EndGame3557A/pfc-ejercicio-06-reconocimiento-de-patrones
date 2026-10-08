package taller

abstract class Figura {
  def nombre: String
  def area: Double
  def perimetro: Double
  def esMayorQue(otra: Figura): Boolean = area > otra.area
}

trait Escalable {
  def escalar(k: Double): Figura
}

class Circulo(val radio: Double) extends Figura with Escalable {
  def nombre: String = "círculo"
  def area: Double = math.Pi * radio * radio
  def perimetro: Double = 2 * math.Pi * radio
  def escalar(k: Double): Figura = new Circulo(radio * k)
}

class Rectangulo(val base: Double, val altura: Double) extends Figura with Escalable {
  def nombre: String = "rectángulo"
  def area: Double = base * altura
  def perimetro: Double = 2 * (base + altura)
  def escalar(k: Double): Figura = new Rectangulo(base * k, altura * k)
}

class Cuadrado(val lado: Double) extends Rectangulo(lado, lado) {
  override def nombre: String = "cuadrado"
  override def escalar(k: Double): Figura = new Cuadrado(lado * k)
}

class Triangulo(val base: Double, val altura: Double) extends Figura with Escalable {
  def nombre: String = "triángulo"
  def area: Double = base * altura / 2
  def perimetro: Double = base + altura + math.sqrt(base * base + altura * altura)
  def escalar(k: Double): Figura = new Triangulo(base * k, altura * k)
}