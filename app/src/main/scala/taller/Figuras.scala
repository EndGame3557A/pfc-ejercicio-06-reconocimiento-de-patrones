package taller

// Una figura tiene nombre, área y perímetro. Qué vale cada uno lo decide
// la figura concreta; compararlas por área es igual para todas.
abstract class Figura {
  def nombre: String
  def area: Double
  def perimetro: Double

  // Responde si esta figura tiene más área que la otra.
  def esMayorQue(otra: Figura): Boolean = false // Completar
}

// Lo que se puede agrandar o encoger multiplicando sus medidas por k.
trait Escalable {
  def escalar(k: Double): Figura
}

// Círculo de radio dado. Tal como está, todas las figuras tienen nombre
// vacío, área y perímetro cero, y escalarlas las deja igual.
class Circulo(val radio: Double) extends Figura with Escalable {
  def nombre: String = "" // Completar
  def area: Double = 0.0 // Completar
  def perimetro: Double = 0.0 // Completar
  def escalar(k: Double): Figura = this // Completar
}

class Rectangulo(val base: Double, val altura: Double)
    extends Figura with Escalable {
  def nombre: String = "" // Completar
  def area: Double = 0.0 // Completar
  def perimetro: Double = 0.0 // Completar
  def escalar(k: Double): Figura = this // Completar
}

// Un cuadrado es un rectángulo con los dos lados iguales. Se llama
// "cuadrado" y al escalarlo sigue siendo un cuadrado.
class Cuadrado(val lado: Double) extends Rectangulo(lado, lado) {
  // Completar
}

// Triángulo rectángulo: la base y la altura son los catetos, y el
// tercer lado sale de ellos.
class Triangulo(val base: Double, val altura: Double)
    extends Figura with Escalable {
  def nombre: String = "" // Completar
  def area: Double = 0.0 // Completar
  def perimetro: Double = 0.0 // Completar
  def escalar(k: Double): Figura = this // Completar
}
