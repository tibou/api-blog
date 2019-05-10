# api-blog
C'est une application web développée avec les technologies java et spring. Elle s'exécute dans un conteneur tomcat au port 8080.

# Requirements
JDK 1.8

Mariadb 10.1

# Deployment
  ## Création de la base de bonnées
  
  L'application communique avec une base de données mysql dont les informations sont les suivantes:
  BDD: api_blog
  utilisateur: hydroquebec
  mot de passe: hydroquebec
  Pour créer l'utilisateur et le mot de passe, il faut exécuter le script /src/main/resources/sql/create-user.sql.
  Le script /src/main/resources/sql/api-blog.sql permet de créer la base de données et ses tables.
  
  ## Lancer l'application
  Pour exécuter l'application, il faut copier le l'archive api-blog.jar sur la machine hôte et le lancer avec la 
  commande suivante:  java -jar api-blog.jar. Pour accéder à la page d'acceuil de l'application, il suffit de taper dans le       navigateur  l'adresse suivante: http://server_ip:8080/api-blog/, il faut remplacer server_ip par l'adresse de la machine       hôte.
  
# Utilisation de l'api
Quatre formes de resources sont offertes à travers les URLs suivantes:

## GET à http://server_ip:8080/api-blog/post/{id}/comments: renvoie la liste des commentaires du post dont l'id est fourni dans l'URL

[
    {
        "id": 1,
        "message": "juste un commentaire",
        "author": "tiburce",
        "createdAt": "2019-05-08T11:28:03",
        "updatedAt": "2019-05-08T11:28:03"
    },
    {
        "id": 2,
        "message": "pour tester la mise à jour",
        "author": "tibor",
        "createdAt": "2019-05-08T15:23:42",
        "updatedAt": "2019-05-08T14:26:59"
    }
]

## POST à http://server_ip:8080/api-blog/post/{id}/comments: création d’un nouveau commentaire pour le post dont l'id est fourni dans l'URL

{

        "message": "Ceci est un commentaire"
}

Il faut fournir le contenu du message. En retour l'api renvoie le commentaire qui vient d'être créer.

{
    "id": 5,
    "message": "Ceci est un commentaire",
    "author": null,
    "createdAt": "2019-05-10T16:38:26.024",
    "updatedAt": "2019-05-10T16:38:26.024"
}

## PUT  à http://server_ip:8080/api-blog/post/{1}/comments – mise à jour d’un commentaire dont l'id est fourni dans l'URL

{

        "message": "Ceci est une mise à jour de commentaire"
}

Tout comme le POST, le commentaire mis à jour est renvoyé.

## DELETE  à http://server_ip:8080/api-blog/post/{id}/comments – supprimer un commentaire dont l'id est fourni dans l'URL

L'api renvoie la chaine suivante:
Comment with id : 5 deleted

# Espace de gestion du blog
L'application offre un espace de gestion des posts. Typiquement, les opérations CRUD y sont implémentées.
L'espace est accessible via l'URL de l'application :  http://server_ip:8080/api-blog/

# Améliorations possibles
Implémentation de la sécurité pour l'authentification et l'autorisation

Implémentation du HATEOAS pour l'api
