package taller

class Ejercicio() {

  // Punto 1. La suma de las áreas de todas las figuras de la lista.
  // Tal como está devuelve 0.0 y las pruebas quedan en rojo.
  def areaTotal(figuras: List[Figura]): Double = {
    0.0 // Completar
  }

  // Punto 2. Los elementos de l sin repetidos y en orden ascendente.
  // La lista puede llegar desordenada.
  def sinRepetidos(l: List[Int]): List[Int] = {
    List() // Completar
  }

  // Punto 3. Los elementos que están en las dos listas, sin repetidos
  // y en orden ascendente.
  def comunes(l1: List[Int], l2: List[Int]): List[Int] = {
    List() // Completar
  }

  // Punto 4. El valor entero de la expresión.
  def evaluar(e: Expr): Int = {
    0 // Completar
  }

  // La expresión en una línea, con paréntesis solo donde hacen falta.
  def mostrar(e: Expr): String = {
    "" // Completar
  }

  // La expresión sin sumas de cero, productos por uno, productos por cero
  // ni restas de una expresión consigo misma. No hace aritmética.
  def simplificar(e: Expr): Expr = {
    Numero(0) // Completar
  }
}
