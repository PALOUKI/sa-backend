
# Documentation de l'API de Gestion des Sentiments et Clients

Cette documentation décrit les points d'accès (endpoints) disponibles pour interagir avec l'API de gestion des sentiments et des clients.

## Base URL

La base URL de l'API est généralement `http://localhost:8080/` si vous exécutez l'application localement avec le port par défaut de Spring Boot. 

-----

## 1\. API des Sentiments (`/sentiment`)

Gère la création, la lecture, la recherche, la mise à jour et la suppression des sentiments.

### 1.1. Créer un nouveau Sentiment

Crée un nouveau sentiment. Le type de sentiment (POSITIF/NEGATIF) est automatiquement déterminé en fonction du texte fourni.

  * **Endpoint:** `POST /sentiment`
  * **Content-Type:** `application/json`

#### Corps de la requête (Request Body):

```json
{
  "client": {
    "email": "example@email.com",
    "telephone": "12345678"
  },
  "texte": "Ceci est un bon commentaire."
}
```

  * `client`: L'objet client auquel le sentiment est associé. Si le client n'existe pas, il sera créé.
  * `texte`: Le contenu textuel du sentiment.

#### Réponse (Response):

  * **Statut:** `201 Created` (Aucun corps de réponse pour cet endpoint).

-----

### 1.2. Rechercher des Sentiments

Récupère une liste de sentiments en fonction de filtres optionnels.

  * **Endpoint:** `GET /sentiment`
  * **Produces:** `application/json`

#### Paramètres de Requête (Query Parameters):

  * `typeValue` (Optionnel): Filtre les sentiments par type.
      * `0`: Pour les sentiments `POSITIF`
      * `1`: Pour les sentiments `NEGATIF`
      * Si omis, tous les types sont inclus.
  * `clientId` (Optionnel): Filtre les sentiments par l'ID du client associé.
      * Par défaut à `-1` si non fourni, ce qui signifie qu'il n'y a pas de filtre par client ID.

#### Exemples de Requêtes:

  * **Récupérer tous les sentiments :**
    ```
    GET http://localhost:8080/sentiment
    ```
  * **Récupérer tous les sentiments positifs :**
    ```
    GET http://localhost:8080/sentiment?typeValue=0
    ```
  * **Récupérer tous les sentiments négatifs :**
    ```
    GET http://localhost:8080/sentiment?typeValue=1
    ```
  * **Récupérer tous les sentiments d'un client avec l'ID 1 :**
    ```
    GET http://localhost:8080/sentiment?clientId=1
    ```
  * **Récupérer tous les sentiments positifs d'un client avec l'ID 1 :**
    ```
    GET http://localhost:8080/sentiment?typeValue=0&clientId=1
    ```

#### Réponse (Response):

  * **Statut:** `200 OK`

#### Corps de la réponse (Response Body):

Une liste d'objets `Sentiment`.

```json
[
  {
    "id": 1,
    "texte": "Ce produit est excellent.",
    "type": "POSITIF",
    "client": {
      "id": 101,
      "email": "client1@example.com",
      "telephone": "11223344"
    }
  },
  {
    "id": 2,
    "texte": "Le service était vraiment mauvais.",
    "type": "NEGATIF",
    "client": {
      "id": 102,
      "email": "client2@example.com",
      "telephone": "55667788"
    }
  }
]
```

  * **Statut:** `400 Bad Request` si `typeValue` est une valeur invalide (ex: `2`).

-----

### 1.3. Supprimer un Sentiment

Supprime un sentiment par son ID.

  * **Endpoint:** `DELETE /sentiment/{id}`

#### Path Variable:

  * `id` (int): L'ID du sentiment à supprimer.

#### Réponse (Response):

  * **Statut:** `204 No Content`
  * **Statut:** `404 Not Found` si le sentiment n'existe pas (selon la gestion des erreurs).

-----

### 1.4. Mettre à jour un Sentiment

Met à jour un sentiment existant par son ID. Le texte et le type de sentiment peuvent être modifiés. Le type est déterminé par le texte fourni.

  * **Endpoint:** `PUT /sentiment/{id}`
  * **Content-Type:** `application/json`

#### Path Variable:

  * `id` (int): L'ID du sentiment à mettre à jour.

#### Corps de la requête (Request Body):

```json
{
  "texte": "Mon opinion a changé, c'est désormais bon."
  // Le champ "type" n'est pas nécessaire ici car il est dérivé du texte
  // Le champ "client" n'est pas nécessaire ici pour la mise à jour du sentiment,
}
```

  * `texte`: Le nouveau contenu textuel du sentiment. Le type (`POSITIF`/`NEGATIF`) sera mis à jour en fonction de ce nouveau texte.

#### Réponse (Response):

  * **Statut:** `204 No Content`
  * **Statut:** `404 Not Found` si le sentiment n'existe pas.

-----

## 2\. API des Clients (`/client`)

Gère la création, la lecture et la mise à jour des informations client.

### 2.1. Créer un nouveau Client

Crée un nouveau client. Si un client avec le même email existe déjà, il ne sera pas créé à nouveau.

  * **Endpoint:** `POST /client`
  * **Content-Type:** `application/json`

#### Corps de la requête (Request Body):

```json
{
  "email": "new.client@example.com",
  "telephone": "98765432"
}
```

#### Réponse (Response):

  * **Statut:** `201 Created` (Aucun corps de réponse pour cet endpoint).

-----

### 2.2. Récupérer tous les Clients

Récupère une liste de tous les clients enregistrés.

  * **Endpoint:** `GET /client`
  * **Produces:** `application/json`

#### Réponse (Response):

  * **Statut:** `200 OK`

#### Corps de la réponse (Response Body):

Une liste d'objets `Client`.

```json
[
  {
    "id": 1,
    "email": "clientA@example.com",
    "telephone": "11112222"
  },
  {
    "id": 2,
    "email": "clientB@example.com",
    "telephone": "33334444"
  }
]
```

-----

### 2.3. Récupérer un Client par ID

Récupère les détails d'un client spécifique par son ID.

  * **Endpoint:** `GET /client/{id}`
  * **Produces:** `application/json`

#### Path Variable:

  * `id` (int): L'ID du client à récupérer.

#### Réponse (Response):

  * **Statut:** `200 OK`

#### Corps de la réponse (Response Body):

Un objet `Client`.

```json
{
  "id": 1,
  "email": "clientA@example.com",
  "telephone": "11112222"
}
```

  * **Statut:** `404 Not Found` si le client n'existe pas.

-----

### 2.4. Mettre à jour un Client

Met à jour les informations (email et téléphone) d'un client existant par son ID.

  * **Endpoint:** `PUT /client/{id}`
  * **Content-Type:** `application/json`

#### Path Variable:

  * `id` (int): L'ID du client à mettre à jour.

#### Corps de la requête (Request Body):

```json
{
  "id": 1, // L'ID dans le corps DOIT correspondre à l'ID du chemin
  "email": "updated.client@example.com",
  "telephone": "99887766"
}
```

  * `id`: L'ID du client (doit correspondre à l'ID dans le chemin).
  * `email`: Le nouvel email du client.
  * `telephone`: Le nouveau numéro de téléphone du client.

#### Réponse (Response):

  * **Statut:** `202 Accepted`
  * **Statut:** `404 Not Found` si le client n'existe pas.

-----
