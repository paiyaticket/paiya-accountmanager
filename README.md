# PAIYA-ACCOUNTMANAGER 
## Description
Cette API permet de gérer les comptes utilisateur de la plateforme Paiya.event
et toutes leurs information personelles.
## Régles de gestion et fonctionnalités
**Compte utilisateur (User)**
* Un compte utilisateur appartient a un et un seul utilisateur (personne physique ou morale). Le compte 
utilisateur est le point de départ pour faire quoique ce soit dans l'application. 
Il n'est donc pas possible de faire des actions dans le systeme sous annonymat excepté
consulter la liste des evènements avenirs.
* Un utilisateur peut appartenir à un groupe d'organisateur d'evenement (EventOrganizer);
* ID d'un compte utilisateur est un ID fournit par AWS cognito.
* Un compte utilisateur est soit de type Promoteur, soit de type Participant.

**Compte financier (FinancialAccount)**
* Un utilisateur peut avoir un ou plusieurs comptes financier (FinancialAccount);
* Un compte utilisateur a un seul compte finanicer par defaut;

**Groupe d'utilisateur (EventOrganizer)**
* Un EventOrganizer est un groupe utilisateur. Il permet de représenter un simple 
groupe de compte utilisateur organisant un evènement (un staff) ou une entreprise 
organisatrice.
* Chaque utilisateur appartenent a un EventOrganizer est appelé **OrganizationMember** 
* dans le systeme.