# Clase 6 — Reconocimiento de patrones

Fundamentos de Programación Funcional y Concurrente
Escuela de Ingeniería de Sistemas y Computación, Universidad del Valle
Carlos Andrés Delgado Saavedra

Dos funciones sobre listas que se resuelven mirando la forma del dato en vez
de preguntando por sus partes una por una.

## Reconocer la forma de una lista

Una lista es una de dos cosas: la lista vacía, o un elemento seguido de otra
lista. Esa definición por casos es la que guía el `match`:

```scala
def longitud(l: List[Int]): Int = l match {
  case Nil     => 0
  case _ :: xs => 1 + longitud(xs)
}
```

El patrón `x :: xs` hace dos cosas de una vez: comprueba que la lista tenga al
menos un elemento y liga `x` a la cabeza y `xs` a la cola. De ahí sale la
recursión: se resuelve el caso vacío y se combina la cabeza con el resultado
de la llamada sobre la cola.

Los patrones se pueden anidar y combinar con condiciones:

```scala
l match {
  case Nil                        => "vacía"
  case x :: Nil                   => "un solo elemento: " + x
  case x :: y :: _ if x == y      => "empieza con dos iguales"
  case x :: xs                    => "empieza con " + x
}
```

## Lo que hay que resolver

Todo va en `app/src/main/scala/ejercicio/Ejercicio.scala`. Las dos funciones
se escriben con recursión y `match`. **No use `distinct`, `intersect`,
`sorted`, `toSet` ni ningún otro método de la biblioteca que resuelva el
punto**: el ejercicio es escribirlos.

### Punto 1: quitar los repetidos

```scala
def ejercicio1(l: List[Int]): List[Int]
```

Devuelve los elementos de `l` sin repetidos y en orden ascendente. La lista
de entrada puede venir desordenada.

| Entrada | Resultado |
|---|---|
| `List(1,2,1,1,1,2,2,3,3,3,4,4,4,4,4,5,5,5,3)` | `List(1,2,3,4,5)` |
| `List(1,1,1,1,1,1,1,1,1,2,2,2,2,2,3,3,3,3,3,3,3,2,2,2,2)` | `List(1,2,3)` |
| `List(10,10,8,8,1,9,9,9,2,3,4,5,6,7,8,9,10,10)` | `List(1,2,3,4,5,6,7,8,9,10)` |
| `List()` | `List()` |

Fíjese en el tercer caso: el 2 vuelve a aparecer después del 3, y el 10 está
al principio y al final. El resultado no depende del orden de llegada.

### Punto 2: los elementos comunes

```scala
def ejercicio2(l1: List[Int], l2: List[Int]): List[Int]
```

Devuelve los elementos que están en las dos listas, sin repetidos y en orden
ascendente.

| `l1` | `l2` | Resultado |
|---|---|---|
| `List(10,9,8,8,7,6,6)` | `List(2,4,6,8,8,10,10,10,10)` | `List(6,8,10)` |
| `List(2,4,6,2,4,4,8,10,12,12,10,11)` | `List(1,1,1,3,4,5,6,6)` | `List(4,6)` |
| `List(1,1,2,2,2,3,3,4,4,4,5,5,6,8,8,6,7,1)` | `List(2,4,6,1,3,10,1,5,8,5)` | `List(1,2,3,4,5,6,8)` |
| `List(1,3,5)` | `List(2,4,6)` | `List()` |

Que un elemento aparezca varias veces en una lista o en las dos no cambia
nada: sale una sola vez en el resultado.

## Una sugerencia de descomposición

El punto 2 sale más limpio si se apoya en funciones auxiliares pequeñas: una
que diga si un elemento pertenece a una lista, y otra que inserte un elemento
en una lista ordenada sin repetirlo. Con esas dos, el recorrido principal
queda en cuatro líneas. El punto 1 puede usar la misma auxiliar de inserción.

## Cómo está organizado el proyecto

```
app/src/main/scala/ejercicio/
    App.scala          programa de arranque
    Ejercicio.scala    aquí van los dos puntos

app/src/test/scala/ejercicio/
    AppSuite.scala        comprueba que el entorno quedó bien
    EjercicioTest.scala   los casos de arriba
```

Su código va en `main`. Las pruebas viven aparte y no se tocan.

## Cómo se ejecuta

```bash
./gradlew test    # corre las pruebas
./gradlew run     # corre el programa
```

Dos pruebas arrancan en verde porque comprueban casos vacíos y el esqueleto
ya devuelve la lista vacía. Las demás están en rojo y su trabajo es ponerlas
en verde. El informe completo queda en
`app/build/reports/tests/test/index.html`.

## Cómo se entrega

1. Haga fork de este repositorio.
2. En su fork, abra la pestaña **Actions** y habilítelas. GitHub las deja
   desactivadas en las copias hasta que el dueño lo confirme.
3. Clone, resuelva, haga commit y suba a `main`.
4. En la tarea de la sesión en el campus, pegue la dirección de su fork y el
   hash del último commit, que sale con `git rev-parse HEAD`.

La calificación se hace sobre el commit reportado, no sobre lo que esté en el
fork el día de la revisión.

## Restricciones

Este curso trabaja sin estado mutable. Usar `var`, `while`, `return` o
cualquier variable que cambie deja la nota en 3.0 como máximo, aunque todas
las pruebas pasen.
