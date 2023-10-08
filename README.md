# PAIYA-ACCOUNTMANAGER 
## Description
Cette API permet de gérer les comptes utilisateur de la plateforme Paiya.event
et toutes leurs information personelles.
## Régles de gestion et fonctionnalités
**Compte utilisateur**
* Un compte utilisateur appartient a un et un seul utilisateur physique;
* Un utilisateur peut appartenir à un groupe d'organisateur d'evenement (EventOrganizer);
* ID d'un compte utilisateur est un ID fournit par AWS cognito;
* Un compte utilisateur est soit de type Promoteur, soit de type Participant;

**Compte financier**
* Un utilisateur peut avoir un ou plusieurs compte financier (FinancialAccount);
* Un compte utilisateur a un seul compte finanicer par defaut

