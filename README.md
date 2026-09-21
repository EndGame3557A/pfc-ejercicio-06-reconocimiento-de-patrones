# Ejercicio 6 — Reconocimiento de patrones

Fundamentos de Programación Funcional y Concurrente
Escuela de Ingeniería de Sistemas y Computación, Universidad del Valle
Carlos Andrés Delgado Saavedra

Cuatro puntos sobre una misma pregunta: cómo se define un tipo que tiene
varias formas y cómo se opera sobre él. Primero con herencia, donde cada
subclase completa lo que la clase abstracta deja pendiente; después mirando
la forma del dato con `match`, sobre listas y sobre una jerarquía sellada de
expresiones aritméticas.

## Clases abstractas y traits

Una clase abstracta declara métodos sin cuerpo y deja que cada subclase los
complete. Un método concreto de la clase abstracta puede apoyarse en los
abstractos: no sabe cuánto vale `alto` ni `ancho`, pero sabe que el área es
el producto de los dos.

```scala
trait Plano {
  def alto: Int
  def ancho: Int
  def area = alto * ancho
}
```

Una clase hereda de una sola superclase con `extends` y mezcla los traits que
necesite con `with`: `class Rectangulo(...) extends Figura with Escalable`.
Cuando una subclase redefine un método que ya tenía cuerpo, lo marca con
`override`. Y cuando se llama un método sobre un valor cuyo tipo declarado es
la superclase, corre la versión de la clase concreta del objeto.

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

## Jerarquías selladas y `case class`

Con `sealed` todas las subclases viven en el mismo archivo, así que el
compilador conoce todos los casos y avisa cuando a un `match` le falta uno.
Con `case class` se construye sin `new`, dos valores con los mismos campos
son iguales, y el `match` puede abrir el valor y ligar sus campos:

```scala
sealed abstract class ConjEnt
case class Vacio() extends ConjEnt
case class NoVacio(elem: Int, izq: ConjEnt, der: ConjEnt) extends ConjEnt

def listaEnteros(conj: ConjEnt): List[Int] = conj match {
  case Vacio()                => List()
  case NoVacio(elm, izq, der) => listaEnteros(izq) ++ List(elm) ++ listaEnteros(der)
}
```

Los patrones también admiten constantes: `case NoVacio(0, _, _)` reconoce un
nodo cuyo elemento es cero, sin importar sus subárboles.

## Lo que hay que resolver

Todo va en `app/src/main/scala/taller/`. Los tipos del punto 1 están en
`Figuras.scala`, la jerarquía del punto 4 ya está completa en
`Expresiones.scala`, y las funciones de los cuatro puntos van en
`Ejercicio.scala`.

### Punto 1: figuras con una clase abstracta y un trait

```scala
abstract class Figura {
  def nombre: String
  def area: Double
  def perimetro: Double
  def esMayorQue(otra: Figura): Boolean
}

trait Escalable {
  def escalar(k: Double): Figura
}

class Circulo(val radio: Double) extends Figura with Escalable
class Rectangulo(val base: Double, val altura: Double) extends Figura with Escalable
class Cuadrado(val lado: Double) extends Rectangulo(lado, lado)
class Triangulo(val base: Double, val altura: Double) extends Figura with Escalable

def areaTotal(figuras: List[Figura]): Double
```

`Figura` declara lo que toda figura sabe responder: su nombre, su área y su
perímetro. `esMayorQue` es el único método con cuerpo en la clase abstracta y
responde si esta figura tiene más área que la otra; con áreas iguales es
`false`. `Escalable` aporta `escalar(k)`, que devuelve una figura nueva con
todas las medidas multiplicadas por `k`.

| Figura | Nombre | Área | Perímetro |
|---|---|---|---|
| `Circulo(radio)` | `círculo` | π · radio² | 2 · π · radio |
| `Rectangulo(base, altura)` | `rectángulo` | base · altura | 2 · (base + altura) |
| `Cuadrado(lado)` | `cuadrado` | la del rectángulo | el del rectángulo |
| `Triangulo(base, altura)` | `triángulo` | base · altura / 2 | base + altura + hipotenusa |

`Triangulo` es un triángulo rectángulo: la base y la altura son los catetos
y el tercer lado es la hipotenusa. `Cuadrado` hereda de `Rectangulo` y solo
cambia en dos cosas: su nombre es `cuadrado`, y escalarlo da otro cuadrado,
no un rectángulo. En el esqueleto su cuerpo está vacío; lo que herede sin
tocar sale con los valores del rectángulo.

`areaTotal` suma las áreas de todas las figuras de la lista, con recursión y
`match` sobre la lista; con la lista vacía es `0.0`. Cada figura calcula su
propia área aunque la lista sea de tipo `List[Figura]`.

| Llamada | Resultado |
|---|---|
| `new Rectangulo(3.0, 4.0).area` | 12.0 |
| `new Rectangulo(3.0, 4.0).perimetro` | 14.0 |
| `new Cuadrado(2.0).area` | 4.0 |
| `new Cuadrado(2.0).perimetro` | 8.0 |
| `new Cuadrado(2.0).nombre` | `cuadrado` |
| `new Circulo(1.0).area` | 3.141592653589793 |
| `new Circulo(1.0).perimetro` | 6.283185307179586 |
| `new Circulo(2.0).area` | 12.566370614359172 |
| `new Triangulo(3.0, 4.0).area` | 6.0 |
| `new Triangulo(3.0, 4.0).perimetro` | 12.0 |
| `new Triangulo(6.0, 8.0).perimetro` | 24.0 |
| `new Rectangulo(3.0, 4.0).escalar(2.0).area` | 48.0 |
| `new Rectangulo(3.0, 4.0).escalar(2.0).perimetro` | 28.0 |
| `new Circulo(1.0).escalar(3.0).area` | 28.274333882308138 |
| `new Triangulo(3.0, 4.0).escalar(2.0).perimetro` | 24.0 |
| `new Cuadrado(2.0).escalar(3.0).nombre` | `cuadrado` |
| `new Cuadrado(2.0).escalar(3.0).area` | 36.0 |
| `new Circulo(1.0).esMayorQue(new Cuadrado(1.0))` | `true` |
| `new Cuadrado(1.0).esMayorQue(new Circulo(1.0))` | `false` |
| `new Rectangulo(2.0, 8.0).esMayorQue(new Cuadrado(4.0))` | `false` |
| `areaTotal(List(new Rectangulo(3.0, 4.0), new Cuadrado(2.0), new Triangulo(3.0, 4.0)))` | 22.0 |
| `areaTotal(List())` | 0.0 |
| `areaTotal(List(new Circulo(1.0), new Cuadrado(1.0)))` | 4.141592653589793 |

Las pruebas comparan los valores que llevan π o una raíz con una tolerancia
de una millonésima; los demás se comparan exactos.

### Punto 2: quitar los repetidos

```scala
def sinRepetidos(l: List[Int]): List[Int]
```

Devuelve los elementos de `l` sin repetidos y en orden ascendente. La lista
de entrada puede venir desordenada. Se escribe con recursión y `match`.
**No use `distinct`, `sorted`, `toSet` ni ningún otro método de la
biblioteca que resuelva el punto**: el ejercicio es escribirlo.

| Llamada | Resultado |
|---|---|
| `sinRepetidos(List(1, 2, 1, 1, 1, 2, 2, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 3))` | `List(1, 2, 3, 4, 5)` |
| `sinRepetidos(List(1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2))` | `List(1, 2, 3)` |
| `sinRepetidos(List(10, 10, 8, 8, 1, 9, 9, 9, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10))` | `List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)` |
| `sinRepetidos(List())` | `List()` |
| `sinRepetidos(List(5))` | `List(5)` |
| `sinRepetidos(List(3, 2, 1))` | `List(1, 2, 3)` |
| `sinRepetidos(List(-3, 7, -3, 0, 7))` | `List(-3, 0, 7)` |

Fíjese en el tercer caso: el 2 vuelve a aparecer después del 3, y el 10 está
al principio y al final. El resultado no depende del orden de llegada. El
penúltimo caso separa dos tareas que van juntas: `List(3, 2, 1)` no tiene
repetidos y aun así cambia.

### Punto 3: los elementos comunes

```scala
def comunes(l1: List[Int], l2: List[Int]): List[Int]
```

Devuelve los elementos que están en las dos listas, sin repetidos y en orden
ascendente. Vale la misma restricción del punto anterior, y tampoco
`intersect` ni `contains`.

| Llamada | Resultado |
|---|---|
| `comunes(List(10, 9, 8, 8, 7, 6, 6), List(2, 4, 6, 8, 8, 10, 10, 10, 10))` | `List(6, 8, 10)` |
| `comunes(List(2, 4, 6, 2, 4, 4, 8, 10, 12, 12, 10, 11), List(1, 1, 1, 3, 4, 5, 6, 6))` | `List(4, 6)` |
| `comunes(List(1, 1, 2, 2, 2, 3, 3, 4, 4, 4, 5, 5, 6, 8, 8, 6, 7, 1), List(2, 4, 6, 1, 3, 10, 1, 5, 8, 5))` | `List(1, 2, 3, 4, 5, 6, 8)` |
| `comunes(List(1, 3, 5), List(2, 4, 6))` | `List()` |
| `comunes(List(), List(1, 2))` | `List()` |
| `comunes(List(1, 2), List())` | `List()` |
| `comunes(List(5, 1, 3), List(3, 5))` | `List(3, 5)` |
| `comunes(List(2, 2, 3), List(2, 3, 3))` | `List(2, 3)` |

Que un elemento aparezca varias veces en una lista o en las dos no cambia
nada: sale una sola vez en el resultado. Los dos últimos casos atrapan la
solución que se queda con los elementos de `l1` que están en `l2` y nada
más: el orden y los repetidos de `l1` se le cuelan.

Los puntos 2 y 3 salen más limpios con funciones auxiliares pequeñas: una que
diga si un elemento pertenece a una lista, y otra que inserte un elemento en
una lista ordenada sin repetirlo. Con esas dos, cada recorrido principal
queda en tres o cuatro líneas, y la de inserción sirve en los dos puntos.

### Punto 4: expresiones aritméticas

La jerarquía ya está escrita en `Expresiones.scala` y no se toca:

```scala
sealed trait Expr
case class Numero(valor: Int) extends Expr
case class Suma(e1: Expr, e2: Expr) extends Expr
case class Resta(e1: Expr, e2: Expr) extends Expr
case class Prod(e1: Expr, e2: Expr) extends Expr
```

Sobre ella van tres funciones, las tres con `match`:

```scala
def evaluar(e: Expr): Int
def mostrar(e: Expr): String
def simplificar(e: Expr): Expr
```

`evaluar` calcula el valor entero de la expresión. El árbol ya dice qué se
opera con qué: `Suma(Numero(3), Prod(Numero(2), Numero(5)))` es 3 + (2 · 5),
y `Prod(Suma(Numero(1), Numero(2)), Numero(4))` es (1 + 2) · 4.

| Llamada | Resultado |
|---|---|
| `evaluar(Numero(7))` | 7 |
| `evaluar(Suma(Numero(3), Prod(Numero(2), Numero(5))))` | 13 |
| `evaluar(Prod(Suma(Numero(1), Numero(2)), Numero(4)))` | 12 |
| `evaluar(Resta(Numero(6), Numero(3)))` | 3 |
| `evaluar(Resta(Numero(2), Numero(5)))` | -3 |
| `evaluar(Prod(Resta(Numero(10), Numero(4)), Suma(Numero(1), Prod(Numero(2), Numero(3)))))` | 42 |

`mostrar` escribe la expresión en una línea, con un espacio a cada lado de
cada operador, y pone paréntesis solo donde hacen falta para que la cadena
se lea igual que el árbol. Hacen falta en dos situaciones: una suma o una
resta que sea operando de un producto, y una suma o una resta que sea el
operando derecho de una resta. En cualquier otro lugar no van.

| Llamada | Resultado |
|---|---|
| `mostrar(Numero(7))` | `7` |
| `mostrar(Suma(Numero(3), Prod(Numero(2), Numero(5))))` | `3 + 2 * 5` |
| `mostrar(Suma(Suma(Numero(1), Numero(2)), Numero(3)))` | `1 + 2 + 3` |
| `mostrar(Prod(Prod(Numero(2), Numero(3)), Numero(4)))` | `2 * 3 * 4` |
| `mostrar(Prod(Suma(Numero(1), Numero(2)), Numero(4)))` | `(1 + 2) * 4` |
| `mostrar(Prod(Numero(2), Suma(Numero(3), Numero(4))))` | `2 * (3 + 4)` |
| `mostrar(Prod(Resta(Numero(5), Numero(1)), Resta(Numero(4), Numero(2))))` | `(5 - 1) * (4 - 2)` |
| `mostrar(Resta(Numero(5), Suma(Numero(1), Numero(2))))` | `5 - (1 + 2)` |
| `mostrar(Resta(Resta(Numero(5), Numero(3)), Numero(1)))` | `5 - 3 - 1` |
| `mostrar(Resta(Numero(5), Resta(Numero(3), Numero(1))))` | `5 - (3 - 1)` |
| `mostrar(Resta(Numero(5), Prod(Numero(2), Numero(3))))` | `5 - 2 * 3` |

Compare `5 - 3 - 1` con `5 - (3 - 1)`: valen 1 y 3, y los dos árboles se
distinguen solo por dónde está el paréntesis. Una versión que ponga
paréntesis en toda suma y toda resta escribe `(3 + 2 * 5)` en el segundo
caso, y una que no ponga ninguno escribe `1 + 2 * 4` en el quinto; las
pruebas separan las dos.

`simplificar` devuelve una expresión equivalente sin ninguna de estas formas:

| Forma | Queda |
|---|---|
| `e + 0`, `0 + e` | `e` |
| `e - 0` | `e` |
| `e - e` | `0` |
| `e * 1`, `1 * e` | `e` |
| `e * 0`, `0 * e` | `0` |

Dos precisiones. La primera: `simplificar` no hace aritmética, así que
`Suma(Numero(2), Numero(3))` queda como está. La segunda: el resultado no
puede contener ninguna de las formas de la tabla, tampoco las que aparecen
después de simplificar las partes; `Suma(Prod(Numero(0), Numero(99)),
Numero(5))` es `Numero(5)`, porque el producto se vuelve cero y entonces la
suma tiene un cero. Para `e - e`, dos expresiones son la misma cuando son
iguales como valores, y eso lo dan las `case class`.

| Llamada | Resultado |
|---|---|
| `simplificar(Suma(Numero(0), Numero(7)))` | `Numero(7)` |
| `simplificar(Suma(Numero(7), Numero(0)))` | `Numero(7)` |
| `simplificar(Prod(Numero(1), Suma(Numero(3), Numero(4))))` | `Suma(Numero(3), Numero(4))` |
| `simplificar(Prod(Suma(Numero(3), Numero(4)), Numero(1)))` | `Suma(Numero(3), Numero(4))` |
| `simplificar(Prod(Numero(0), Numero(99)))` | `Numero(0)` |
| `simplificar(Prod(Suma(Numero(3), Numero(4)), Numero(0)))` | `Numero(0)` |
| `simplificar(Resta(Numero(7), Numero(0)))` | `Numero(7)` |
| `simplificar(Resta(Suma(Numero(3), Numero(4)), Suma(Numero(3), Numero(4))))` | `Numero(0)` |
| `simplificar(Numero(7))` | `Numero(7)` |
| `simplificar(Suma(Numero(2), Numero(3)))` | `Suma(Numero(2), Numero(3))` |
| `simplificar(Suma(Numero(3), Prod(Numero(2), Numero(5))))` | `Suma(Numero(3), Prod(Numero(2), Numero(5)))` |
| `simplificar(Suma(Prod(Numero(0), Numero(99)), Numero(5)))` | `Numero(5)` |
| `simplificar(Prod(Suma(Numero(1), Numero(0)), Suma(Numero(3), Numero(4))))` | `Suma(Numero(3), Numero(4))` |
| `simplificar(Resta(Suma(Numero(2), Numero(0)), Numero(2)))` | `Numero(0)` |
| `simplificar(Suma(Prod(Numero(1), Suma(Numero(3), Numero(0))), Prod(Numero(0), Numero(9))))` | `Numero(3)` |

Una prueba más comprueba que `evaluar(simplificar(e))` y `evaluar(e)`
coinciden: simplificar cambia la forma, no el valor.

## Cómo está organizado el proyecto

```
app/src/main/scala/taller/
    App.scala            programa de arranque
    Figuras.scala        la clase abstracta, el trait y las cuatro figuras del punto 1
    Expresiones.scala    la jerarquía sellada del punto 4, ya completa
    Ejercicio.scala      las funciones de los cuatro puntos

app/src/test/scala/taller/
    AppSuite.scala        comprueba que el entorno quedó bien
    EjercicioTest.scala   los casos de las tablas
```

Todo está en el paquete `taller`, por eso las pruebas usan `Figura`, `Expr`
y `Ejercicio` sin importar nada. Su código va en `main`. Las pruebas viven
aparte y no se tocan.

## Cómo se ejecuta

```bash
./gradlew test    # corre las pruebas
```

Las pruebas arrancan en rojo y el trabajo es ponerlas en verde. El informe
completo queda en `app/build/reports/tests/test/index.html`.

## Cómo se trabaja

1. Haga fork de este repositorio.
2. En su fork, abra la pestaña **Actions** y habilítelas. GitHub las deja
   desactivadas en las copias hasta que el dueño lo confirme.
3. Clone, resuelva, haga commit y suba a `main`.
4. Verifique en **Actions** que la última ejecución quedó en verde.

## Restricciones

Este curso trabaja sin estado mutable: nada de `var`, `while`, `return` ni
variables que cambien. El resultado correcto por el camino equivocado no
cuenta como resultado correcto.
