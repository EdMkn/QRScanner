# QR Scanner - Application Android

Une application Android moderne pour scanner et générer des codes QR avec une interface propre et intuitive.

## 📱 Fonctionnalités

### 🔍 **Scanner de Codes QR**
- Scan en temps réel des codes QR avec CameraX et ML Kit
- Détection et traitement automatiques des codes QR
- Notifications toast lors de la détection de codes QR
- Gestion des permissions d'accès à la caméra

### 🎨 **Générateur de Codes QR**
- Génération de codes QR à partir de texte
- Sortie de haute qualité (512x512 pixels)
- Génération instantanée avec la bibliothèque ZXing
- Interface utilisateur claire et conviviale

### 📚 **Historique des Scans**
- Stockage local de tous les codes QR scannés avec Room
- Liste chronologique des scans avec horodatage
- Stockage de données persistant
- RecyclerView avec adaptateur personnalisé

### 🧭 **Navigation**
- Navigation inférieure Material Design
- Trois sections principales : Scanner, Générateur, Historique
- Transitions fluides entre les activités
- Support du bouton retour pour tous les écrans

## 🛠️ Stack Technique

### **Technologies Principales**
- **Langage** : Kotlin
- **SDK Minimum** : 24 (Android 7.0)
- **SDK Cible** : 36 (Android 14)
- **Architecture** : MVVM avec pattern Repository

### **Bibliothèques Clés**
- **Caméra** : CameraX (Core, Camera2, Lifecycle, View)
- **Scan QR** : ML Kit Barcode Scanning
- **Génération QR** : ZXing Core
- **Base de données** : Room (Runtime, Compiler)
- **Interface** : Material Design Components
- **Navigation** : BottomNavigationView
- **RecyclerView** : AndroidX RecyclerView

### **Dépendances**
```kotlin
// Caméra et Scan QR
implementation(libs.androidx.camera.core)
implementation(libs.androidx.camera.camera2)
implementation(libs.androidx.camera.lifecycle)
implementation(libs.androidx.camera.view.v130)
implementation(libs.barcode.scanning)

// Base de données
implementation(libs.androidx.room.runtime)
kapt(libs.androidx.room.compiler)

// Composants UI
implementation(libs.androidx.material)
implementation(libs.androidx.recyclerview)

// Génération QR
implementation(libs.core) // ZXing Core
```

## 📁 Structure du Projet

```
app/src/main/java/com/myprojects/qrscanner/
├── data/
│   ├── dao/
│   │   └── ScanDao.kt              # Objet d'accès aux données
│   ├── model/
│   │   └── Scan.kt                 # Modèle de données pour les scans
│   └── AppDatabase.kt              # Configuration de la base Room
├── ui/
│   ├── adapter/
│   │   └── ScanAdapter.kt          # Adaptateur RecyclerView pour l'historique
│   ├── viewmodel/
│   │   └── ScanViewModel.kt        # ViewModel pour la gestion des données
│   ├── GenerateActivity.kt         # Écran de génération de codes QR
│   ├── HistoryActivity.kt          # Écran d'historique des scans
│   └── MainActivity.kt             # Activité principale avec navigation
└── utils/
    └── QRUtils.kt                  # Fonctions utilitaires (placeholder)
```

## 🚀 Démarrage Rapide

### **Prérequis**
- Android Studio Arctic Fox ou plus récent
- Android SDK 24+
- Kotlin 2.0.21+
- Gradle 8.13+

### **Installation**

1. **Cloner le repository**
   ```bash
   git clone <url-du-repository>
   cd QRScanner
   ```

2. **Ouvrir dans Android Studio**
   - Ouvrir Android Studio
   - Sélectionner "Ouvrir un projet Android Studio existant"
   - Naviguer vers le dossier QRScanner et l'ouvrir

3. **Synchroniser Gradle**
   - Attendre que la synchronisation Gradle se termine
   - Résoudre les problèmes de dépendances si demandé

4. **Compiler et Exécuter**
   - Connecter un appareil Android ou démarrer un émulateur
   - Cliquer sur "Exécuter" (▶️) dans Android Studio
   - Accorder les permissions caméra quand demandé

### **Compilation en Ligne de Commande**
```bash
# Compiler l'APK debug
./gradlew assembleDebug

# Compiler l'APK release
./gradlew assembleRelease

# Exécuter les tests
./gradlew test
```

## 📱 Utilisation

### **Scanner des Codes QR**
1. Ouvrir l'application
2. Accorder les permissions caméra quand demandé
3. Pointer la caméra vers un code QR
4. L'application détectera et traitera automatiquement le code QR
5. Une notification toast confirmera le scan
6. Les codes scannés sont automatiquement sauvegardés dans l'historique

### **Générer des Codes QR**
1. Appuyer sur l'onglet "Générateur" dans la navigation inférieure
2. Saisir n'importe quel texte dans le champ de saisie
3. Appuyer sur "Générer QR Code"
4. Le code QR généré apparaîtra en dessous
5. Utiliser le bouton retour pour revenir au scanner

### **Consulter l'Historique des Scans**
1. Appuyer sur l'onglet "Historique" dans la navigation inférieure
2. Voir tous les codes QR précédemment scannés
3. Chaque entrée affiche le contenu du code QR et l'horodatage
4. Utiliser le bouton retour pour revenir au scanner

## 🔧 Configuration

### **Permissions**
L'application nécessite les permissions suivantes :
- `android.permission.CAMERA` - Pour le scan des codes QR

### **Fonctionnalités**
- `android.hardware.camera` - Fonctionnalité caméra requise

### **Base de Données**
- Base de données Room avec génération automatique du schéma
- Nom de la base : `qr_database`
- Table : `scans` avec colonnes : `id`, `content`, `timestamp`

## 🧪 Tests

### **Tests Unitaires**
```bash
./gradlew test
```

### **Tests Instrumentés**
```bash
./gradlew connectedAndroidTest
```

### **Analyse Lint**
```bash
./gradlew lint
```

## 📊 Performance

- **Mémoire** : Optimisé pour une faible utilisation mémoire
- **Batterie** : Utilisation efficace de la caméra avec gestion du cycle de vie
- **Stockage** : Stockage local minimal pour l'historique des scans
- **Réseau** : Aucune dépendance réseau

## 🔒 Sécurité

- Stockage local des données uniquement
- Aucune transmission de données vers des serveurs externes
- Permissions caméra gérées de manière sécurisée
- Aucune collecte de données sensibles

## 🐛 Problèmes Connus

- Gestion des permissions dépréciée (sera mise à jour vers Activity Result API)
- Quelques avertissements d'export de schéma Room (non critiques)
- Avertissements de compatibilité Kapt avec Kotlin 2.0+

## 🚧 Améliorations Futures

- [ ] Gestion moderne des permissions avec Activity Result API
- [ ] Partage des codes QR scannés
- [ ] Copie du contenu des codes QR dans le presse-papiers
- [ ] Support du thème sombre
- [ ] Options de personnalisation des codes QR
- [ ] Export de l'historique des scans
- [ ] Support de multiples formats de codes QR

## 🤝 Contribution

1. Fork le repository
2. Créer une branche de fonctionnalité (`git checkout -b feature/fonctionnalite-geniale`)
3. Commiter vos changements (`git commit -m 'Ajouter une fonctionnalité géniale'`)
4. Pousser vers la branche (`git push origin feature/fonctionnalite-geniale`)
5. Ouvrir une Pull Request

## 📄 Licence

Ce projet est sous licence MIT - voir le fichier [LICENSE](LICENSE) pour plus de détails.

## 👨‍💻 Auteur

**Edouard Makon**
- Apprentissage du développement mobile
- Focus sur le code propre et l'expérience utilisateur

## 🙏 Remerciements

- **Google ML Kit** pour le scan des codes QR
- **ZXing** pour la génération de codes QR
- **AndroidX** pour les composants de développement Android modernes
- **Material Design** pour les composants d'interface

---

**Version** : 1.0  
**Dernière Mise à Jour** : Juillet 2025  
**Compatibilité** : Android 7.0+ (API 24+) 