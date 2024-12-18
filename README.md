# DevOps_Project
✅ Introduction
    Ce projet consiste en la mise en place d'une solution DevOps pour une application de gestion de foyer. L'objectif principal est   
    d'accélérer les cycles de déploiement, d'améliorer la couverture des tests et de mettre en place des outils de surveillance pour 
    garantir la fiabilité et la transparence du système.

✅ Outils et Technologies Utilisés
      Jenkins : Pour l'intégration continue et le déploiement continu (CI/CD).
      Git : Pour la gestion de version du code source.
      Docker : Pour la conteneurisation de l'application.
      SonarQube : Pour l'analyse de la qualité du code.
      Nexus : Pour la gestion des artefacts et le stockage des fichiers JAR.
      JUnit et Mockito : Pour les tests automatisés.
      Prometheus et Grafana : Pour la surveillance et la visualisation des métriques.
✅ Résultats
      Accélération des cycles de déploiement : Les cycles de déploiement ont été réduits de 40 % grâce à l'automatisation des processus.
      Amélioration de la couverture des tests : La couverture des tests a été portée à 95 % grâce à l'intégration de tests automatisés.
      Réduction des incidents non détectés : Les outils de surveillance ont permis de réduire les incidents non détectés de 20 %.
      Amélioration de la visibilité et de la réactivité : L'utilisation de Prometheus et Grafana a permis de surveiller en temps réel 
      les performances de l'application, facilitant ainsi l'identification et la résolution rapide des problèmes.
✅ Configuration de l'Environnement
🕖 Fichiers de Configuration
Les fichiers de configuration nécessaires pour le déploiement de l'application sont inclus dans le projet :

      docker-compose.yml : Ce fichier définit les services nécessaires pour l'application, y compris la base de données MySQL et le 
      backend de l'application.
      Dockerfile : Ce fichier définit l'image Docker pour le backend de l'application, incluant les étapes pour télécharger le fichier 
      JAR depuis Nexus.

🕖 CI/CD avec Jenkins
Le pipeline Jenkins a été configuré pour automatiser le processus de build, de test et de déploiement de l'application. Le script de pipeline inclut les étapes suivantes :

      Cloner le dépôt Git.
      Construire l'image Docker.
      Exécuter les tests automatisés.
      Déployer l'application sur le serveur.

🕖 Vue de la Table dans Jenkins
Pour visualiser la configuration et l'état des builds dans Jenkins, vous pouvez consulter l'image suivante :
Vue de la Table Jenkins
    lien vers dossier des resultats : https://drive.google.com/drive/folders/17doV5UKjp69657xKT8b4IX8ps6BQPvF8?usp=drive_link
✅ Conclusion
      Ce projet a permis de mettre en place une solution DevOps efficace pour l'application de gestion de foyer, en intégrant des outils 
      modernes pour améliorer la qualité du code, la couverture des tests et la surveillance du système. Grâce à cette expérience, j'ai 
      acquis des compétences précieuses en matière de déploiement et de gestion de projets DevOps.

