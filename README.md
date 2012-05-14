# PROJET TER
## CONSCIENCE ARTIFICIELLE

Dans le cadre de notre formation (Première année de Master Informatique à l'Université de Montpellier 2) nous avons réalisé un projet TER (Travail d'Étude et de Recherche) encadré par [Violaine Prince](http://www2.lirmm.fr/~prince/) et Guillaume Tisserant, respectivement Professeur et Doctorant au [Laboratoire d'Informatique, de Robotique et de Microélectronique de Montpellier](http://www.lirmm.fr/).

Le but de ce TER fut d'implémenter un modèle d'intelligence artificielle basé sur une approche cognitive. L'agent développé devait être capable d'acquérir de nouveaux concepts sémantiques à partir de son environnent courant et de ses expériences passées.

L'intelligence artificielle moderne se basant principalement sur une vision opérationnelle, il nous semblait intéressant de porter une démarche différente avec un modèle proche de la cognition humaine.

Pour ce faire, nous avons débuté notre étude par l'analyse d'un travail théorique proposant une conceptualisation du fonctionnement du cerveau humain, étude qui a été réalisée en 2010 par Guillaume Tisserant, Guillaume Maurin, Ndongo Wade et Anthony Willemot dans le cadre de l'unité d'enseignement "Cognition Individuelle et Collective" proposée par l'offre du Master Informatique de l'université Montpellier 2.

Ce projet a été réalisé par l'équipe COGITO composée de quatre étudiants :

* [William Dyce](http://wilbefast.com/)
* [Thibaut Marmin](http://www.thibautmarmin.fr)
* Namrata Patel
* [Clément Sipieter](http://clement-sipieter.fr)

## CONTENU

* **Rapport du projet** : rapport_final_ac.pdf
* **Transparents de la soutenance** : ac_beamer.pdf
* **Sources de COGITO** : App/cogito/src/
* **Sources de game_logic** : App/game_logic/src/
* **Sources de game_service** : App/game_service/src/
* **Jars (cogito, randomIA et game_service)** : Jars/

## USAGE

### INSTALLATION DU SERVEUR DE JEU

* Installer Tomcat,
* Placer le fichier game_service.war dans le dossier webapp de votre installation Tomcat,
* Démarrer ou redémarrer Tomcat,
* Ouvrer la page web http://localhost:8080/game_service/

### LANCEMENT DE COGITO

* Exécuter le fichier cogito.jar (java -jar ./cogito.jar)
* Le répertoire neo4j doit se trouver dans le répertoire d'où est exécuté COGITO

## LICENCE CC BY-SA 3.0

Le projet COGITO réalisé par l'équipe [cogitoTeam](https://github.com/cogitoTeam/artificial_consciousness) est mis à disposition selon les termes de la [licence Creative Commons Paternité - Partage à l'Identique 3.0 France](http://creativecommons.org/licenses/by-sa/3.0/fr/).
