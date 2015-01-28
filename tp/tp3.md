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

De plus, au lieu d'utiliser des `println` dans vos acteurs, utilisez le logging fournit par `akka.actor.ActorLogging`, ça vous aidera à débugger plus tard. Par exemple :

```scala

import akka.actor.{ Actor, ActorLogging }

class TestActor extends Actor with ActorLogging {
  def receive = {
    case s: String => log.info(s)
  }
}


```

Voici un résumé du déroulement auquel on peut s'attendre :

- Le client commande X repas au hasard
- Le serveur prend la commande et la passe au chef
- Quand le chef a fini de préparer le plat, il informe le serveur
- Le serveur donne le plat à manger au client

- Lorsque le client a fini de manger tous les plats qu'il avait commandé, il demande la facture au serveur
- Le serveur prépare la facture et la transmet au client
- Le client paye et s'en va (vous fermez alors le système d'acteurs)

Pour les actions du type `manger`, `payer`, etc, un simple `println($"Je mange le plat $meal")` suffira. Vous allez surement avoir besoin de passer des acteurs en paramètre d'autres acteurs.

#### Deuxième étape

Essayez de créer le nombre de serveurs, de cuisiniers et de clients demandé et faites marcher le tout. Vous devriez vous rendre compte que sélectionner quel serveur et/ou quel cuisinier doit recevoir le message n'est pas hyper pratique.

Pour palier à ce problème, nous allons utiliser la notion de [Routing avec akka](http://doc.akka.io/docs/akka/2.3.9/scala/routing.html). Nous allons créer 2 chefs, 3 serveurs et 5 clients.

Voici comment créer un `pool` de 5 chefs :

```scala
import akka.routing.RoundRobinPool

...

val chefRouter: ActorRef = context.system.actorOf(Props[Chef].withRouter(RoundRobinPool(5)), "ChefRouter")
```

Ce `chefRouter` va créer un pool de 5 acteurs de type `Chef` et leur enverra les messages chacun leur tour car nous utilisons le `RoundRobinPool`. On envoie un message à un pool d'acteurs de la même façon qu'à un acteur classique, le routeur se débrouillera pour acheminer le message au prochain acteur.

Un acteur peut créer d'autres acteurs lui-même, ça ne pose aucun souci. Vous pouvez accéder à son « système d'acteurs » par `context.system.actorOf(...)`.

À noter que le client est peu regardant pour l'instant, s'il reçoit ses plats dans le désordre, il les mangera quand même ;)


#### Améliorations possibles

- Faites en sorte que les clients recoivent leurs plats dans l'ordre de la commande.
- Calculez le temps d'attente de chaque client et le temps d'attente moyen du restaurant.
- Implémentez le fait qu'un client met du temps à manger et qu'on ne devrait pas lui apporter ses plats suivants s'il n'a pas fini ceux d'avant.
