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

#### Première étape

Commencez par créer les classes d'acteurs gérant les différentes notions ci-dessus. Faites tourner votre application avec un client, un serveur et un cuisinier. Nous partirons du principe qu'un client mange instantanément le plat qu'il reçoit. En revanche, le cuisinier passe du temps à préparer chaque repas, vous devons donc simuler ce temps grâce à `Thread.sleep(tempsEnMillisecondes)`.

N'oubliez pas qu'un acteur peut avoir un état, c'est le seul endroit où vous serez autorisé à utiliser des `var`.

De plus, au lieu d'utiliser des `println` dans vos acteurs, utilisez le logging fournit par `akka.actor.ActorLogging`, par exemple :

```scala

import akka.actor.{ Actor, ActorLogging }

class TestActor extends Actor with ActorLogging {
  def receive = {
    case s: String => log.info(s)
  }
}


```

Voici un résumé du déroulement auquel au peut s'attendre :

- Le client commande X repas au hasard
- Le serveur prend la commande et la passe au chef
- Quand le chef a fini de préparer le plat, il informe le serveur
- Le serveur donne le plat à manger au client

- Lorsque le client a fini de manger tous les plats qu'il avait commandé, il demande la facture au serveur
- Le serveur prépare la facture et la transmet au client
- Le client paye et s'en va (vous fermez alors le système d'acteurs)

Pour les actions du type `manger`, `payer`, etc, un simple `println($"Je mange le plat $meal")` suffira.

#### Deuxième étape

Essayez de créer le nombre de serveurs, de cuisiniers et de clients demandé et faites marcher le tout. Vous devriez vous rendre compte que sélectionner quel serveur et/ou quel cuisinier doit recevoir le message n'est pas hyper pratique.

Pour palier à ce problème, nous allons utiliser la notion de [Routing avec akka](http://doc.akka.io/docs/akka/2.3.9/scala/routing.html). Nous allons créer 2 chefs, 3 serveurs et 5 clients.

Pour l'instant, laissez le restaurant ouvert tout le temps, fermez-le « à la main » en faisant Control-C.
