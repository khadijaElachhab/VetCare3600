package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Animal;
import model.Proprietaire;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class InfosProprietaireController implements AnimalModificationCallback, ProprietaireModificationCallback {

    @FXML
    private Label labelNom, labelPrenom, labelEmail, labelTelephone;

    @FXML
    private TableView<Animal> tableAnimaux;

    @FXML
    private TableColumn<Animal, String> colNomAnimal, colAge, colType;

    private Proprietaire proprietaire;

    // Constructeur par défaut
    public InfosProprietaireController() {
        // Constructeur par défaut
    }

    // Méthode appelée depuis la page précédente pour initialiser les données
    public void setProprietaire(Proprietaire p) {
        this.proprietaire = p;
        updateProprietaireDisplay();
    }

    // Méthode pour mettre à jour l'affichage du propriétaire
    private void updateProprietaireDisplay() {
        // Vérification de nullité pour sécuriser l'application
        if (labelNom != null) {
            labelNom.setText(proprietaire.getNom());
        } else {
            System.err.println("labelNom est null");
        }

        if (labelPrenom != null) {
            labelPrenom.setText(proprietaire.getPrenom());
        } else {
            System.err.println("labelPrenom est null");
        }

        if (labelEmail != null) {
            labelEmail.setText(proprietaire.getEmail() != null ? proprietaire.getEmail() : "");
        } else {
            System.err.println("labelEmail est null");
        }

        if (labelTelephone != null) {
            labelTelephone.setText(proprietaire.getTelephone() != null ? proprietaire.getTelephone() : "");
        } else {
            System.err.println("labelTelephone est null");
        }

        if (tableAnimaux != null && proprietaire != null) {
            List<Animal> animaux = proprietaire.getAnimaux();
            tableAnimaux.setItems(FXCollections.observableArrayList(animaux));
        }
    }

    @FXML
    public void initialize() {
        if (colNomAnimal != null) {
            colNomAnimal.setCellValueFactory(cell -> cell.getValue().nomProperty());
        }
        if (colAge != null) {
            colAge.setCellValueFactory(cell -> cell.getValue().ageProperty());
        }
        if (colType != null) {
            colType.setCellValueFactory(cell -> cell.getValue().typeProperty());
        }

        if (tableAnimaux != null) {
            tableAnimaux.setRowFactory(tv -> {
                TableRow<Animal> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && !row.isEmpty()) {
                        Animal animal = row.getItem();
                        ouvrirFenetreModification(animal);
                    }
                });
                return row;
            });
        }
    }

    //...Extrait à modifier dans InfosProprietaireController.java

    private void ouvrirFenetreModification(Animal animal) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/modifier_animal.fxml"));
            Pane root = loader.load();

            ModifierAnimalController controller = loader.getController();

            // S'assurer que controller n'est pas null
            if (controller == null) {
                System.err.println("ERREUR: Le contrôleur ModifierAnimalController n'a pas été initialisé");
                return;
            }

            // S'assurer que tous les champs sont initialisés
            controller.setNomProprietaire(proprietaire.getNom());
            controller.setDialogStage(new Stage());
            controller.setCallback(this);

            // Important: Définir l'animal APRÈS que tous les champs FXML soient chargés
            controller.setAnimal(animal);

            Stage stage = new Stage();
            stage.setTitle("Modifier un animal");
            stage.setScene(new Scene(root, 900, 600));
            stage.showAndWait();

            // Rafraîchir la table après modification
            refreshAnimalTable();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur lors de l'ouverture de la fenêtre de modification: " + e.getMessage());
        }
    }

    @FXML
    public void ajouterAnimal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ajouter_animal.fxml"));
            Pane root = loader.load();

            AjouterAnimalController controller = loader.getController();
            controller.setProprietaire(proprietaire); // passe le propriétaire courant
            controller.setCallback(this); // Définir cette classe comme callback

            Stage stage = new Stage();
            stage.setTitle("Ajouter un animal");
            stage.setScene(new Scene(root, 900, 600));
            stage.showAndWait(); // Utiliser showAndWait au lieu de show

            // Rafraîchir la table immédiatement après la fermeture de la fenêtre
            refreshAnimalTable();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur lors de l'ouverture de la fenêtre d'ajout: " + e.getMessage());
        }
    }

    @FXML
    public void modifierProprietaire() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/modifier_proprietaire.fxml"));
            Pane root = loader.load();

            ModifierProprietaireController controller = loader.getController();
            controller.setProprietaire(proprietaire);

            // Définir ce contrôleur comme le callback pour les modifications de propriétaire
            controller.setCallback(this);

            Stage stage = new Stage();
            stage.setTitle("Modifier propriétaire");
            stage.setScene(new Scene(root, 900, 600));
            stage.showAndWait(); // Utiliser showAndWait
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur lors de l'ouverture de la fenêtre de modification du propriétaire: " + e.getMessage());
        }
    }

    @FXML
    public void goHome() throws IOException {
        Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/accueil.fxml")));
        Stage stage = (Stage) tableAnimaux.getScene().getWindow();
        stage.setScene(new Scene(root, 900, 600));
    }

    @FXML
    public void goVets() throws IOException {
        Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/liste_veto.fxml")));
        Stage stage = (Stage) tableAnimaux.getScene().getWindow();
        stage.setScene(new Scene(root, 900, 600));
    }

    @FXML
    public void ajouterVisite() {
        Animal animal = tableAnimaux.getSelectionModel().getSelectedItem();
        if (animal == null) {
            showAlert("Veuillez sélectionner un animal.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ajouter_visite.fxml"));
            Pane root = loader.load();

            AjouterVisiteController controller = loader.getController();
            controller.setNomAnimal(animal.getNom());

            Stage stage = new Stage();
            stage.setTitle("Nouvelle visite vétérinaire");
            stage.setScene(new Scene(root, 900, 600));
            stage.showAndWait(); // Utiliser showAndWait
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur lors de l'ouverture de la fenêtre d'ajout de visite: " + e.getMessage());
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    public void voirVisites() {
        Animal animal = tableAnimaux.getSelectionModel().getSelectedItem();
        if (animal == null) {
            showAlert("Veuillez sélectionner un animal.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/liste_visites.fxml"));
            Pane root = loader.load();

            ListeVisitesController controller = loader.getController();
            controller.setNomAnimal(animal.getNom());

            Stage stage = new Stage();
            stage.setTitle("Visites de " + animal.getNom());
            stage.setScene(new Scene(root, 900, 600));
            stage.showAndWait(); // Utiliser showAndWait
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur lors de l'ouverture de la fenêtre des visites: " + e.getMessage());
        }
    }

    @FXML
    public void ouvrirFenetreModification() {
        Animal animal = tableAnimaux.getSelectionModel().getSelectedItem();
        if (animal == null) {
            showAlert("Veuillez sélectionner un animal à modifier.");
            return;
        }
        ouvrirFenetreModification(animal);
    }

    @FXML
    public void supprimerAnimal() {
        Animal animal = tableAnimaux.getSelectionModel().getSelectedItem();
        if (animal == null) {
            showAlert("Veuillez sélectionner un animal à supprimer.");
            return;
        }

        // Confirmation
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation");
        confirm.setHeaderText("Voulez-vous vraiment supprimer cet animal ?");
        confirm.setContentText(animal.getNom());

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                proprietaire.getAnimaux().remove(animal);
                tableAnimaux.getItems().remove(animal);
                showAlert("Animal supprimé avec succès.");
            }
        });
    }

    @Override
    public void onAnimalAdded(Animal animal) {
        // Rafraîchir la table avec les animaux actualisés
        refreshAnimalTable();
    }

    @Override
    public void onAnimalModified(Animal oldAnimal, Animal newAnimal) {
        // Implementation de la méthode du callback
        System.out.println("Animal modifié: " + oldAnimal.getNom() + " -> " + newAnimal.getNom());

        // Mettre à jour l'animal dans la liste du propriétaire
        if (proprietaire != null) {
            List<Animal> animaux = proprietaire.getAnimaux();
            int index = animaux.indexOf(oldAnimal);
            if (index != -1) {
                animaux.set(index, newAnimal);
                refreshAnimalTable();
            }
        }
    }

    @Override
    public void onAnimalDeleted(Animal animal) {
        // Si nécessaire à l'avenir
        refreshAnimalTable();
    }

    // Implémenter la méthode de callback pour la mise à jour du propriétaire
    @Override
    public void onProprietaireUpdated(Proprietaire updatedProprietaire) {
        // Mettre à jour le propriétaire local
        this.proprietaire = updatedProprietaire;

        // Mettre à jour l'affichage
        updateProprietaireDisplay();
    }

    // Méthode pour rafraîchir la table
    private void refreshAnimalTable() {
        if (proprietaire != null && tableAnimaux != null) {
            List<Animal> animaux = proprietaire.getAnimaux();
            tableAnimaux.setItems(FXCollections.observableArrayList(animaux));
        }
    }
}