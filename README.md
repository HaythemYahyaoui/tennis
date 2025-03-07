# Tennis
Ce projet reflète ma vision de l'architecture logicielle, du développement et des bonnes pratiques de clean code.

## Architecture
Le projet est structuré en plusieurs modules afin d'assurer une séparation claire des responsabilités.

###  1. Business
   Le module Business contient les classes métier et les cas d'utilisation en suivant l'architecture hexagonale. Il est conçu pour être indépendant de toute technologie et utilise un minimum de bibliothèques : seulement Lombok, MapStruct et Hibernate Validation (via Spring Validation).

### Classes métier
Elles encapsulent les données et la logique métier avec des getters, mais sans setters (préférer des méthodes comme updateId(String id)). L'objectif est d'assurer une stricte encapsulation des données.

Exemple : Game.class

Cette classe ne représente pas directement une entité persistée.
La persistance est gérée dans le module Infrastructure, garantissant ainsi que l'objet métier reste indépendant des détails techniques.

### Ports
Ports d'entrée : Interfaces permettant d'interagir avec le domaine métier, généralement sous forme de cas d'utilisation.

Ports de sortie : Interfaces que les composants externes doivent implémenter pour que le domaine fonctionne correctement.

Exemples :

GameRepository : Interface pour la persistance des objets Game.

GameEvent : Permet à Game d'envoyer des événements à d'autres composants du système.

### Cas d'utilisation (Use Cases)
Ils définissent les besoins métier et orchestrent les interactions entre les entités et services du domaine.

### Principes

- Validation des entrées.
- Gestion des autorisations.
- Couverture de 100 % des tests unitaires sur ce module (moins exigeant sur Application et Infrastructure car 99 % des problèmes fonctionnels sont liés au Business).
- Chaque Use Case doit respecter les règles suivantes :
  - Une interface dédiée.
  - Une implémentation associée.
  - Une seule responsabilité (une seule fonction).
  - La fonction prend en paramètre un DTO unique (ex. ...Action), qui ne sera jamais utilisé ailleurs.
  - La fonction retourne une réponse unique, qui ne sera jamais utilisée ailleurs.
  - Minimiser la réutilisation d’un Use Case dans un autre, sauf si cela est explicitement spécifié dans un ticket JIRA (dans mon exemple, CommandGameUseCase réutilise trois autres Use Cases).
- Les objets métier doivent être strictement encapsulés.
- Object non persistable

### 2. Infrastructure
   Le module Infrastructure regroupe toutes les implémentations des ports de sortie. Il gère la communication entre l'application et les services externes (base de données, Kafka, etc.).

Il contient également le fichier docker-compose.yml, qui définit les services tiers nécessaires pour l’environnement de développement.


### 3. Application
   Le module Application regroupe les points d’entrée permettant aux clients ou aux systèmes externes d’interagir avec le domaine métier.

Exemples de points d’entrée :

API REST pour exposer des endpoints aux clients.
Kafka Consumer pour traiter les messages entrants.
Autres possibilités : WebSockets, RMI, etc.

#### Principes :

- Validation des entrées.
- Respect strict des bonnes pratiques RESTful.
- Utilisation des règles OpenAPI3 pour exposer l’API REST.
- Génération des clients (Java, Angular, React) à partir d'OpenAPI Generator.
- Sécurisation avec OAuth2 via un serveur externe comme Keycloak, sans l'implémenter directement dans l’application.

### 4. Kafka
Un seul topic est utilisé pour tous les événements afin de préserver l'ordre des messages.
Toutefois, l’ordre n’est pas garanti à cause des partitions.

Une clé de partitionnement est ajoutée lors de l’envoi des messages pour limiter ce problème.

## Conclusion

Ce projet met en avant les principes de l’architecture hexagonale, en garantissant une séparation claire entre le domaine métier, l’infrastructure et les interfaces utilisateur. L’objectif est d’obtenir un code maintenable, évolutif et indépendant des technologies sous-jacentes.

How to start : 
- got to infrastructure/src/main/resources/docker
- run command : docker compose up -d
- clean install on "tennis" module
- spring-boot:run on "application" module

Usage : 
We have 4 endpoints that can be tested on swagger : http://localhost:8684/swagger-ui/index.html

Exercice :
{context}/api/game/run/{command}
we can try our exercice with command equal to ABABAA

Unit Test :
We can check coverage with surfire report : tennis/business/target/site/jacoco/index.html


