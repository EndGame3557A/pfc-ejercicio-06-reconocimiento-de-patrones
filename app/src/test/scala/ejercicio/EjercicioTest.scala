package ejercicio

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class EjercicioTest extends AnyFunSuite {

  val objEjercicio = new Ejercicio()

  test("Sin repetidos: lista con varios valores repetidos") {
    val lista = List(1, 2, 1, 1, 1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 3)
    assert(objEjercicio.ejercicio1(lista) == List(1, 2, 3, 4, 5))
  }

  test("Sin repetidos: un valor vuelve a aparecer al final") {
    val lista = List(1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2,
                     3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2)
    assert(objEjercicio.ejercicio1(lista) == List(1, 2, 3))
  }

  test("Sin repetidos: la lista llega desordenada") {
    val lista = List(10, 10, 8, 8, 1, 9, 9, 9, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10)
    assert(objEjercicio.ejercicio1(lista) == List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
  }

  test("Sin repetidos: la lista vacía") {
    assert(objEjercicio.ejercicio1(List()) == List())
  }

  test("Comunes: dos listas con tres valores en común") {
    val l1 = List(10, 9, 8, 8, 7, 6, 6)
    val l2 = List(2, 4, 6, 8, 8, 10, 10, 10, 10)
    assert(objEjercicio.ejercicio2(l1, l2) == List(6, 8, 10))
  }

  test("Comunes: dos listas con dos valores en común") {
    val l1 = List(2, 4, 6, 2, 4, 4, 8, 10, 12, 12, 10, 11)
    val l2 = List(1, 1, 1, 3, 4, 5, 6, 6)
    assert(objEjercicio.ejercicio2(l1, l2) == List(4, 6))
  }

  test("Comunes: casi todos los valores coinciden") {
    val l1 = List(1, 1, 2, 2, 2, 3, 3, 4, 4, 4, 5, 5, 6, 8, 8, 6, 7, 1)
    val l2 = List(2, 4, 6, 1, 3, 10, 1, 5, 8, 5)
    assert(objEjercicio.ejercicio2(l1, l2) == List(1, 2, 3, 4, 5, 6, 8))
  }

  test("Comunes: sin nada en común") {
    assert(objEjercicio.ejercicio2(List(1, 3, 5), List(2, 4, 6)) == List())
  }
}
