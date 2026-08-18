# Ejercicio reconocimiento de patrones

Usando reconocimiento de patrones resuelva los siguientes Ejercicio

## Ejercicio 1

Dada una lista de números debe:

1. Eliminar repetidos
2. Ordenada de menor a mayor

Ejemplo:

```scala
test("Prueba 2 Ejercicio 1") {
val lista = List(1,1,1,1,1,1,1,1,1,2,2,2,2,2,3,3,3,3,3,3,3,2,2,2,2)
val result = objEjercicio.ejercicio1(lista)
assert(result == List(1,2,3))
}
```

## Ejercicio 2

Dada dos listas de números debe retornar los elementos que se repiten en ambas listas ordenados de menor a mayor

Ejemplo

```scala
  test("Prueba 1 Ejercicio 2") {
    val l1 = List(10,9,8,8,7,6,6)
    val l2 = List(2,4,6,8,8,10,10,10,10)
    val result = objEjercicio.ejercicio2(l1,l2)
    assert(result == List(6,8))
  }
```

Note que 6 y 8 se repiten en ambas listas

## Importante

Debe usar reconocimiento de patrones para resolver los ejercicios, si usa un método diferente no se le dará puntaje al ejercicio así las pruebas de software pasen.
