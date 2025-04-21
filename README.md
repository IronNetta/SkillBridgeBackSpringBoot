# SkillBridge 🧠🤝

SkillBridge est une plateforme de mentorat en ligne qui connecte des étudiants avec des professionnels pour des sessions de guidance, conseils de carrière et apprentissage personnalisé.

(en cours de développement)

## 🔗 Fonctionnalités principales

- Inscription & Authentification JWT
- Rôles : Étudiant 👨‍🎓 / Mentor 🧑‍🏫 / Admin 🔐
- Réservation de créneaux de mentorat
- Gestion des disponibilités côté mentor
- Espace personnel pour chaque utilisateur
- Système d’avis et notation
- Interface admin (gestion des comptes & utilisateurs)

## 🛠️ Technologies utilisées

### Backend (Spring Boot)
- Java 23
- Spring Boot (MVC, Security, JPA)
- JWT pour l’authentification
- PostgreSQL
- Maven (multi-module : API / BLL / DAL / DL / IL)


## 📦 Structure du projet

```
├───.idea
├───api
│   ├───.mvn
│   │   └───wrapper
│   └───src
│       ├───main
│       │   ├───java
│       │   │   └───org
│       │   │       └───seba
│       │   │           └───api
│       │   │               ├───controllers
│       │   │               │   ├───advisor
│       │   │               │   └───auth
│       │   │               └───models
│       │   │                   ├───availability
│       │   │                   │   ├───dtos
│       │   │                   │   └───forms
│       │   │                   ├───mentor
│       │   │                   │   ├───dtos
│       │   │                   │   └───forms
│       │   │                   ├───review
│       │   │                   │   ├───dtos
│       │   │                   │   └───forms
│       │   │                   ├───security
│       │   │                   │   ├───dtos
│       │   │                   │   └───forms
│       │   │                   ├───session
│       │   │                   │   ├───dtos
│       │   │                   │   └───forms
│       │   │                   ├───skill
│       │   │                   │   ├───dtos
│       │   │                   │   └───forms
│       │   │                   ├───student
│       │   │                   │   ├───dtos
│       │   │                   │   └───forms
│       │   │                   └───user
│       │   │                       ├───dtos
│       │   │                       └───forms
│       │   └───resources
│       └───test
│           └───java
│               └───org
│                   └───seba
│                       └───api
├───bll
│   ├───.idea
│   └───src
│       └───main
│           └───java
│               └───org
│                   └───seba
│                       ├───exceptions
│                       │   └───user
│                       └───services
│                           ├───availability
│                           │   ├───impl
│                           │   └───model
│                           ├───mentor
│                           │   ├───impl
│                           │   └───model
│                           ├───review
│                           │   ├───impl
│                           │   └───model
│                           ├───security
│                           │   └───impl
│                           ├───session
│                           │   ├───impl
│                           │   └───model
│                           ├───skill
│                           │   └───impl
│                           └───user
│                               └───impl
├───dal
│   ├───.idea
│   └───src
│       └───main
│           └───java
│               └───org
│                   └───seba
│                       └───repositories
│                           └───custom
│                               └───impl
├───dl
│   ├───.idea
│   └───src
│       └───main
│           └───java
│               └───org
│                   └───seba
│                       ├───entities
│                       │   └───base
│                       └───enums
└───il
    ├───.idea
    └───src
        └───main
            └───java
                └───org
                    └───seba
                        ├───configs
                        ├───filters
                        ├───requests
                        ├───specifications
                        └───utils
                            └───jwt

```

## 🚀 Lancer le projet localement

### Prérequis
- Java 17
- Node.js + Angular CLI
- PostgreSQL

### Backend
```bash
cd api
./mvnw clean install
# Vérifie que PostgreSQL tourne et que l'application.properties est correct
./mvnw spring-boot:run
```


## 🔐 Comptes de démonstration

| Rôle     | Email               | Mot de passe |
|----------|---------------------|--------------|
| Admin    | admin@skill.com     | admin123     |
| Étudiant | student@skill.com   | student123   |
| Mentor   | mentor@skill.com    | mentor123    |

## 📅 Planning de développement

Le projet a été réalisé en **14 jours**, selon le plan suivant :
- J1-J7 : Développement backend (Spring Boot)
- J8-J13 : Frontend Angular + Intégration API
- J14 : Déploiement + rédaction documentation

## 🧠 Auteur

Développé par Sebastien De Laet dans le cadre du TFE 2025.

## 📃 Licence

Ce projet est sous licence MIT. Voir le fichier [LICENSE](LICENSE) pour plus d'informations.
