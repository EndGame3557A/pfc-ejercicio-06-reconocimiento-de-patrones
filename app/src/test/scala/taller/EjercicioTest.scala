package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class EjercicioTest extends AnyFunSuite {
  val objEjer = new Ejercicio()

  // Dos reales se consideran iguales si difieren en menos de una millonésima.
  def cerca(a: Double, b: Double): Boolean = math.abs(a - b) < 1e-6

  // Punto 1: figuras

  test("Rectángulo y cuadrado: área, perímetro y nombre") {
    val r = new Rectangulo(3.0, 4.0)
    assert(r.area == 12.0)
    assert(r.perimetro == 14.0)
    assert(r.nombre == "rectángulo")
    val c = new Cuadrado(2.0)
    assert(c.area == 4.0)
    assert(c.perimetro == 8.0)
  }

  test("El cuadrado se llama cuadrado aunque herede del rectángulo") {
    val c = new Cuadrado(2.0)
    assert(c.nombre == "cuadrado")
    val f: Figura = c
    assert(f.nombre == "cuadrado")
  }

  test("Círculo: área y perímetro con Pi") {
    val c = new Circulo(1.0)
    assert(c.nombre == "círculo")
    assert(cerca(c.area, math.Pi))
    assert(cerca(c.perimetro, 2 * math.Pi))
    assert(cerca(new Circulo(2.0).area, 4 * math.Pi))
  }

  test("Triángulo rectángulo: el tercer lado sale de los catetos") {
    val t = new Triangulo(3.0, 4.0)
    assert(t.nombre == "triángulo")
    assert(t.area == 6.0)
    assert(cerca(t.perimetro, 12.0))
    assert(cerca(new Triangulo(6.0, 8.0).perimetro, 24.0))
  }

  test("Escalar multiplica las medidas y el área crece con el cuadrado de k") {
    val r = new Rectangulo(3.0, 4.0).escalar(2.0)
    assert(r.area == 48.0)
    assert(r.perimetro == 28.0)
    assert(cerca(new Circulo(1.0).escalar(3.0).area, 9 * math.Pi))
    assert(cerca(new Triangulo(3.0, 4.0).escalar(2.0).perimetro, 24.0))
  }

  test("Escalar un cuadrado da un cuadrado") {
    val c = new Cuadrado(2.0).escalar(3.0)
    assert(c.nombre == "cuadrado")
    assert(c.area == 36.0)
  }

  test("esMayorQue compara áreas sin importar la figura") {
    assert(new Circulo(1.0).esMayorQue(new Cuadrado(1.0)))
    assert(!new Cuadrado(1.0).esMayorQue(new Circulo(1.0)))
    assert(!new Rectangulo(2.0, 8.0).esMayorQue(new Cuadrado(4.0)))
  }

  test("areaTotal suma las áreas de una lista de figuras") {
    val figuras: List[Figura] =
      List(new Rectangulo(3.0, 4.0), new Cuadrado(2.0), new Triangulo(3.0, 4.0))
    assert(objEjer.areaTotal(figuras) == 22.0)
    assert(objEjer.areaTotal(List()) == 0.0)
    assert(cerca(objEjer.areaTotal(List(new Circulo(1.0), new Cuadrado(1.0))), math.Pi + 1))
  }

  // Punto 2: sinRepetidos

  test("Sin repetidos: lista con varios valores repetidos") {
    val lista = List(1, 2, 1, 1, 1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 3)
    assert(objEjer.sinRepetidos(lista) == List(1, 2, 3, 4, 5))
  }

  test("Sin repetidos: un valor vuelve a aparecer al final") {
    val lista = List(1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2,
                     3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2)
    assert(objEjer.sinRepetidos(lista) == List(1, 2, 3))
  }

  test("Sin repetidos: la lista llega desordenada") {
    val lista = List(10, 10, 8, 8, 1, 9, 9, 9, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10)
    assert(objEjer.sinRepetidos(lista) == List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
  }

  test("Sin repetidos: la lista vacía y la de un elemento") {
    assert(objEjer.sinRepetidos(List()) == List())
    assert(objEjer.sinRepetidos(List(5)) == List(5))
  }

  test("Sin repetidos: sin repetidos pero al revés, y con negativos") {
    assert(objEjer.sinRepetidos(List(3, 2, 1)) == List(1, 2, 3))
    assert(objEjer.sinRepetidos(List(-3, 7, -3, 0, 7)) == List(-3, 0, 7))
  }

  // Punto 3: comunes

  test("Comunes: dos listas con tres valores en común") {
    val l1 = List(10, 9, 8, 8, 7, 6, 6)
    val l2 = List(2, 4, 6, 8, 8, 10, 10, 10, 10)
    assert(objEjer.comunes(l1, l2) == List(6, 8, 10))
  }

  test("Comunes: dos listas con dos valores en común") {
    val l1 = List(2, 4, 6, 2, 4, 4, 8, 10, 12, 12, 10, 11)
    val l2 = List(1, 1, 1, 3, 4, 5, 6, 6)
    assert(objEjer.comunes(l1, l2) == List(4, 6))
  }

  test("Comunes: casi todos los valores coinciden") {
    val l1 = List(1, 1, 2, 2, 2, 3, 3, 4, 4, 4, 5, 5, 6, 8, 8, 6, 7, 1)
    val l2 = List(2, 4, 6, 1, 3, 10, 1, 5, 8, 5)
    assert(objEjer.comunes(l1, l2) == List(1, 2, 3, 4, 5, 6, 8))
  }

  test("Comunes: sin nada en común y con una lista vacía") {
    assert(objEjer.comunes(List(1, 3, 5), List(2, 4, 6)) == List())
    assert(objEjer.comunes(List(), List(1, 2)) == List())
    assert(objEjer.comunes(List(1, 2), List()) == List())
  }

  test("Comunes: el resultado sale ordenado y sin repetir aunque las entradas no") {
    assert(objEjer.comunes(List(5, 1, 3), List(3, 5)) == List(3, 5))
    assert(objEjer.comunes(List(2, 2, 3), List(2, 3, 3)) == List(2, 3))
  }

  // Punto 4: expresiones

  val tresMasDosPorCinco = Suma(Numero(3), Prod(Numero(2), Numero(5)))
  val unoMasDosPorCuatro = Prod(Suma(Numero(1), Numero(2)), Numero(4))

  test("evaluar respeta la estructura del árbol, no la precedencia del texto") {
    assert(objEjer.evaluar(Numero(7)) == 7)
    assert(objEjer.evaluar(tresMasDosPorCinco) == 13)
    assert(objEjer.evaluar(unoMasDosPorCuatro) == 12)
  }

  test("evaluar con restas, negativos y anidamiento") {
    assert(objEjer.evaluar(Resta(Numero(6), Numero(3))) == 3)
    assert(objEjer.evaluar(Resta(Numero(2), Numero(5))) == -3)
    val e = Prod(Resta(Numero(10), Numero(4)), Suma(Numero(1), Prod(Numero(2), Numero(3))))
    assert(objEjer.evaluar(e) == 42)
  }

  test("mostrar no pone paréntesis donde la precedencia ya ordena") {
    assert(objEjer.mostrar(Numero(7)) == "7")
    assert(objEjer.mostrar(tresMasDosPorCinco) == "3 + 2 * 5")
    assert(objEjer.mostrar(Suma(Suma(Numero(1), Numero(2)), Numero(3))) == "1 + 2 + 3")
    assert(objEjer.mostrar(Prod(Prod(Numero(2), Numero(3)), Numero(4))) == "2 * 3 * 4")
  }

  test("mostrar encierra una suma o una resta que es operando de un producto") {
    assert(objEjer.mostrar(unoMasDosPorCuatro) == "(1 + 2) * 4")
    assert(objEjer.mostrar(Prod(Numero(2), Suma(Numero(3), Numero(4)))) == "2 * (3 + 4)")
    assert(objEjer.mostrar(Prod(Resta(Numero(5), Numero(1)), Resta(Numero(4), Numero(2)))) == "(5 - 1) * (4 - 2)")
  }

  test("mostrar encierra el operando derecho de una resta cuando hace falta") {
    assert(objEjer.mostrar(Resta(Numero(5), Suma(Numero(1), Numero(2)))) == "5 - (1 + 2)")
    assert(objEjer.mostrar(Resta(Resta(Numero(5), Numero(3)), Numero(1))) == "5 - 3 - 1")
    assert(objEjer.mostrar(Resta(Numero(5), Resta(Numero(3), Numero(1)))) == "5 - (3 - 1)")
    assert(objEjer.mostrar(Resta(Numero(5), Prod(Numero(2), Numero(3)))) == "5 - 2 * 3")
  }

  test("simplificar quita los ceros de la suma y los unos del producto") {
    assert(objEjer.simplificar(Suma(Numero(0), Numero(7))) == Numero(7))
    assert(objEjer.simplificar(Suma(Numero(7), Numero(0))) == Numero(7))
    assert(objEjer.simplificar(Prod(Numero(1), Suma(Numero(3), Numero(4)))) == Suma(Numero(3), Numero(4)))
    assert(objEjer.simplificar(Prod(Suma(Numero(3), Numero(4)), Numero(1))) == Suma(Numero(3), Numero(4)))
  }

  test("simplificar anula un producto por cero y una resta de algo consigo mismo") {
    assert(objEjer.simplificar(Prod(Numero(0), Numero(99))) == Numero(0))
    assert(objEjer.simplificar(Prod(Suma(Numero(3), Numero(4)), Numero(0))) == Numero(0))
    assert(objEjer.simplificar(Resta(Numero(7), Numero(0))) == Numero(7))
    val x = Suma(Numero(3), Numero(4))
    assert(objEjer.simplificar(Resta(x, x)) == Numero(0))
  }

  test("simplificar no hace aritmética") {
    assert(objEjer.simplificar(Numero(7)) == Numero(7))
    assert(objEjer.simplificar(Suma(Numero(2), Numero(3))) == Suma(Numero(2), Numero(3)))
    assert(objEjer.simplificar(tresMasDosPorCinco) == tresMasDosPorCinco)
  }

  test("simplificar aplica las reglas a lo que resulta de simplificar las partes") {
    assert(objEjer.simplificar(Suma(Prod(Numero(0), Numero(99)), Numero(5))) == Numero(5))
    assert(objEjer.simplificar(Prod(Suma(Numero(1), Numero(0)), Suma(Numero(3), Numero(4)))) == Suma(Numero(3), Numero(4)))
    assert(objEjer.simplificar(Resta(Suma(Numero(2), Numero(0)), Numero(2))) == Numero(0))
  }

  test("simplificar conserva el valor de la expresión") {
    val e1 = Suma(Prod(Numero(1), Suma(Numero(3), Numero(0))), Prod(Numero(0), Numero(9)))
    val e2 = Prod(Resta(Numero(10), Numero(4)), Suma(Numero(1), Prod(Numero(2), Numero(3))))
    assert(objEjer.evaluar(objEjer.simplificar(e1)) == objEjer.evaluar(e1))
    assert(objEjer.simplificar(e1) == Numero(3))
    assert(objEjer.evaluar(objEjer.simplificar(e2)) == objEjer.evaluar(e2))
    assert(objEjer.simplificar(e2) == e2)
  }
}
