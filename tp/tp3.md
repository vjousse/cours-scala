# Programmation concurrente

## Future

Reprenez le code du tp2 et placez les fonctions qui accèdent au disque (lecture / écriture) dans des Future. N'hésitez pas à utiliser map / flatMap si besoin.

## Acteurs

Pour cette partie, nous allons simuler la gestion d'un restaurant. Votre restaurant disposera des éléments suivants :

- 4 plats. Chaque plat a une description, un temps de réalisation et un prix.
- 3 serveurs. Les serveurs sont capables de prendre les commandes (une liste de plats), d'éditer l'addition et d'encaisser l'argent.
- 2 cuisiniers. Les cuisiniers sont capables de réaliser les plats.
- 5 clients. Les clients passent les commandes (liste de plats) aux serveurs.

### Préparation pour sbt

Ici, nous allons utiliser [sbt](http://www.scala-sbt.org/) pour gérer notre projet (notamment pour pouvoir utiliser Akka et le système d'acteurs).

Créez un fichier `build.sbt` avec le contenu suivant :


```scala
resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.9"
```

Créez le répertoire `src/main/scala` : c'est ici que vous placerez votre code.

Testez sbt :

    sbt compile
    sbt run

### Méthodologie

Tout d'abord, n'hésitez pas à lire ce [tutorial](http://danielwestheide.com/blog/2013/02/27/the-neophytes-guide-to-scala-part-14-the-actor-approach-to-concurrency.html) pour vous re-mémorer comment fonctionnent les acteurs.

Commencez par créer les classes d'acteurs gérant les différentes notions ci-dessus. Faites tourner votre application avec un client, un serveur et un cuisinier.

Essayez de créer le nombre de serveurs, de cuisiniers et de clients demandé et faites marcher le tout. Vous devriez vous rendre compte que sélectionner quel serveur et/ou quel cuisinier doit recevoir le message n'est pas hyper pratique.

Pour palier à ce problème, utilisez le LoadBalancer dont vous trouverez la documentation [sur le site d'akka](http://doc.akka.io/docs/akka/1.3.1/scala/routing.html).
