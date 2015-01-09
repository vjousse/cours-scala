# Découverte de scala

## Hello World

Créez votre premier fichier `HelloWorld.scala` :

```scala
object HelloWorld {
    def main(args: Array[String]) {
        println("Hello, world!")
    }
}
```

Compilez-le :

    scalac HelloWorld.scala

Et exécutez-le :

    scala -cp . HelloWorld

L'argument `-cp .` vous permet d'inclure le répertoire courant (qui doit contenir le fichier bytecode pour la JVM nommé `HelloWorld.class`) au `classpath`. Scala cherchera donc dans ce répertoire les classes correspondantes.

## Échauffement sur les listes

Écrivez les fonctions suivantes :

- Affichage de tous éléments d'une liste d'`Int`.
- Transformation une liste d'`Int` en une liste de leurs valeurs absolues.
- Filtrage d'une liste en ne gardant que les éléments négatifs d'une liste d'`Int`. Écrivez deux versions, une en utilisant `.filter` une sans.
- Calcul du nombre d'éléments d'une liste (sans utiliser `.length`).
- Inversion d'une liste (sans utiliser `.reverse`). Écrivez une version avec `foldLeft`, l'autre avec un `match`.

Il est évident que la [documentation de l'API de Scala](http://www.scala-lang.org/api/current/) pourrait vous être utile, ainsi que le moteur de recherche spécialisé [Scalex](http://scalex.org/).

## Testez les exemples du cours

```scala
object TestFactorial {
    def factorial(n: Int): Int = {

        def go(n: Int, acc: Int): Int =
            if (n <= 0) acc
            else go(n-1, n*acc)

        go(n, 1)
    }

    private def formatFactorial(n: Int) = {
        val msg = "The factorial of %d is %d."
        msg.format(n, factorial(n))
    }

    def main(args: Array[String]): Unit = {
        println(formatFactorial(7))
    }
}
```

```scala
object TestFactorial {
    def abs(n: Int): Int =
        if (n < 0) -n
        else n

    def factorial(n: Int): Int = {
        def go(n: Int, acc: Int): Int =
            if (n <= 0) acc
            else go(n-1, n*acc)

        go(n, 1)
    }

    def formatResult(name: String, n: Int, f: Int => Int) = {
        val msg = "The %s of %d is %d."
        msg.format(name, n, f(n))
    }

    def main(args: Array[String]): Unit = {
        println(formatResult("factorial", 7, factorial))
        println(formatResult("absolute value", -2, abs))
    }
}
```

__Note__ : On dit que la fonction `factorial` est à récursivité terminale (_tail recursive_). En effet, lors de l'appel récursif, elle ne fait rien d'autre que retourner la valeur de cet appel `else go(n-1, n*acc)`. Par exemple, s'il elle avait retourné `1 + go(n-1, n*acc)`, elle n'aurait pas été à récursivité terminale. Cette notion est important car elle permet au compilateur de transformer cet appel récursif en itérations et donc d'économiser de l'espace dans la pile d'exécution.

En scala, il est possible de spécifier que l'on attend que notre fonction soit _tail recursive_ grâce à l'annotation `@annotation.tailrec`. Si ce n'est pas le cas (à cause d'une erreur de notre part), le compilateur nous préviendra.

```scala
    def factorial(n: Int): Int = {

        @annotation.tailrec
        def go(n: Int, acc: Int): Int =
            if (n <= 0) acc
            else go(n-1, n*acc)

        go(n, 1)
    }
```

## Fibonacci

Écrivez une fonction à récusivité terminale qui calcule les `n` premiers nombres de la suite de Fibonacci.

Pour rappel, les deux premiers nombres de la suite sont `0` et `1`. Le nième nombre est toujours la somme des deux précédents. La suite commence donc par `0`, `1`, `1`, `2`, `3`, `5`.

Voici la signature de la fonction attendue :

```scala
def fib(n: Int): Int
```

# Exercice 1

- Écrivez un programme permettant d'afficher les arguments passés en paramètre à la console
- Écrivez un programme permettant d'afficher le contenu d'un fichier
- Couplez les deux programmes précédents pour afficher le contenu d'un fichier dont le nom est passé en paramètre à la console
- Essayez plusieurs améliorations sur votre programme : n'afficher qu'une ligne sur 3, supprimer une chaine de caractère prédéfinie de chacune des lignes, compter le nombre de lignes, le nombre de mots et le nombre de caractères.
