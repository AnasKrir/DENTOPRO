# Dentopro


# Application de Gestion de Clinique Dentaire

Ce projet est une application de bureau développée en Java pour gérer une clinique dentaire. L'application utilise Java Swing pour l'interface utilisateur, Maven pour la gestion du projet, Lombok pour réduire le code boilerplate, et une base de données basée sur des fichiers texte (`*.txt`). L'architecture suit le modèle de conception MVC (Model-View-Controller).

## Prérequis

Avant de lancer l'application, assurez-vous d'avoir les éléments suivants installés sur votre machine :

- **Java Development Kit (JDK)** : Version 23.
- **IntelliJ IDEA** : Édition Communautaire (ou tout autre IDE compatible avec Maven).
- **Maven** : Pour la gestion des dépendances et la construction du projet.
- **Lombok** : Plugin installé dans votre IDE pour éviter les erreurs de compilation.

## Configuration du Projet



1. **Ouvrir le projet dans IntelliJ IDEA** :
    - Ouvrez IntelliJ IDEA.
    - Sélectionnez `Open` et choisissez le dossier du projet.

2. **Configurer Maven** :
    - IntelliJ IDEA détectera automatiquement le fichier `pom.xml` et configurera le projet en conséquence.
    - Si ce n'est pas le cas, allez dans `File > Project Structure > Project` et assurez-vous que la version du SDK est correcte.

3. **Installer Lombok** :
    - Allez dans `File > Settings > Plugins`.
    - Recherchez `Lombok` et installez le plugin.
    - Redémarrez IntelliJ IDEA si nécessaire.

4. **Configurer la base de données** :
    - La base de données est basée sur des fichiers texte (`*.txt`). Assurez-vous que les fichiers nécessaires (par exemple, `patients.txt`, `rendezvous.txt`) sont présents dans le dossier `Dentopro/myFileBase`.

## Lancer l'Application

L'application est lancée depuis la classe `DentoproApplication`, qui est le point d'entrée du projet.

1. **Compiler le projet** :
    - Dans IntelliJ IDEA, allez dans `Maven > Lifecycle > clean` puis `install` pour compiler le projet.

2. **Exécuter l'application** :
    - Ouvrez la classe `DentoproApplication` située dans `src/main/java/ma/dentopro/DentoproApplication`.
    - Cliquez avec le bouton droit sur la classe et sélectionnez `Run 'DentoproApplication.main()'`.
    - Vous pouvez également utiliser le raccourci `Shift + F10` pour lancer l'application.

3. **Connexion administrateur** :
    - Lors du lancement, une boîte de dialogue vous demandera le nom d'utilisateur et le mot de passe.
    - Utilisez les identifiants suivants pour vous connecter en tant que Dentiste :
        - **Nom d'utilisateur** : `admin`
        - **Mot de passe** : `admin`
   - Utilisez les identifiants suivants pour vous connecter en tant que Secrétaire :
      - **Nom d'utilisateur** : `ranya`
      - **Mot de passe** : `ranya`
    - Si les identifiants sont corrects, la fenêtre principale de l'application s'affichera. Sinon, un message d'erreur sera affiché.
    - Si vous créez un compte, il sera automatiquement un compte pour secrétaire.



## Fonctionnalités

- Gestion des patients.
- Gestion des Rendez-vous.
- Gestion des Factures.
- Gestion des dossiers médicaux.
- Gestion des consultations.
- Interface conviviale pour les utilisateurs.

