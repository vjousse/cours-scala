__Rappel__ : Essayez d'avoir un maximum de fonctions pures, laissez les `println` au placard, ou en tout cas dans la fonction _main_.

# Échauffement

Dans le cours, nous avons vu qu'un Super Héros pouvait avoir des pouvoirs phénoménaux. Implémentez la version du cours et répondez aux questions suivantes :

- Quels sont les __super-héros__ qui sont __invulnérables__ ?
- Quels sont les __civils__ qui gagnent __moins de 10000 euros__ et dont l'__alter-égo__ est __invulnérable__ ? 
- Quels sont les __super-héros__ qui ont au moins 2 super-pouvoirs ?
- Quel super-héros n'a pas d'alter-égo ?

Utilisez des structures de données pour stocker vos différents personnages, puis :

- Calculez la fortune de tous vos personnages réunis.
- Listez tous les super-pouvoirs possibles.
- Listez tous les noms (super-héros, civils, ennemis).

# Exercice

Dans cet exercice, nous allons essayer de structurer un « vrai programme » Scala en se basant sur l'exercice précédent.

## Lecture / écriture

Tout d'abord, écrivez le code qui va permettre de lire et d'écrire la liste des super-héros/civils/ennemis dans des fichiers. Choisissez le format de stockage que vous souhaitez, l'important, c'est que vous puissiez le relire ensuite (un format texte type CSV peut être une bonne idée).

Attention, les codes qui lisent et écrivent dans des fichiers sont des codes « dangereux » : ils lancent souvent des exceptions ou peuvent renvoyer des `null`. Essayez de gérer cela de manière fonctionnelle.

## Organisation du code

Séparez le code qui accède aux fichiers : placez-le dans des fichiers séparés. Ré-organisez votre code en conséquence. Documentez-vous sur les packages en scala (en gros, c'est comme en java) et placez ces fichiers dans un `package` que l'on nommera `api`.

## Interaction

Rendez votre programme interactif : permettez à l'utilisateur de choisir quelques-unes des questions de l'échauffement dans une liste, et permettez-lui de rajouter/supprimer des super-héros/civils/ennemis.
