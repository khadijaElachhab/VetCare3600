package view;

import dao.ProprietaireDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger les données depuis le fichier au démarrage
        ProprietaireDAO.init();

        // Charger le fichier FXML principal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/accueil.fxml"));
        BorderPane root = loader.load();

        // Créer la scène avec les dimensions nécessaires
        Scene scene = new Scene(root, 900, 600);

        // Ajouter la feuille de style CSS (vous pouvez utiliser celle-ci pour styliser les autres pages)
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        // Configurer et afficher la fenêtre principale
        primaryStage.setTitle("PAWLY - Puppy Adventures Indoors");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}