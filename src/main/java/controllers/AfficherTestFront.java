package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import models.Question;
import models.Test;
import services.ServiceTest;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AfficherTestFront implements Initializable {

    @FXML
    private AnchorPane nh;

    @FXML
    private VBox vbox1;

    ServiceTest serviceTest = new ServiceTest();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        afficherTests();
    }

    private void afficherTests() {
        vbox1.getChildren().clear(); // Effacer le contenu actuel de la VBox
        List<Test> testList = serviceTest.afficherTest();

        vbox1.setSpacing(50.0); // Ajoute un espacement vertical entre chaque carte
        vbox1.setPadding(new javafx.geometry.Insets(30.0)); // Ajoute un espacement horizontal et vertical autour de la VBox

        HBox currentRow = new HBox(); // Créer une nouvelle rangée
        currentRow.setSpacing(20.0); // Espacement horizontal entre les cartes

        int testsAdded = 0; // Nombre de tests ajoutés dans la rangée actuelle

        int counter = 1; // Compteur pour chaque test
        for (Test test : testList) {
            Pane card = createTestCard(test, counter);
            currentRow.getChildren().add(card); // Ajouter la carte à la rangée actuelle
            testsAdded++;

            if (testsAdded == 3) {
                vbox1.getChildren().add(currentRow); // Ajouter la rangée à la VBox
                currentRow = new HBox(); // Créer une nouvelle rangée
                currentRow.setSpacing(20.0); // Espacement horizontal entre les cartes
                testsAdded = 0; // Réinitialiser le compteur de tests ajoutés
            }
            counter++; // Incrémenter le compteur de test
        }

        // Ajouter un espace vertical entre les lignes de cartes
        vbox1.getChildren().add(new Pane());

        // Ajouter la rangée restante si elle n'est pas complète
        if (testsAdded > 0) {
            vbox1.getChildren().add(currentRow);
        }
    }

    private Pane createTestCard(Test test, int counter) {
        Pane card = new Pane();
        card.setPrefHeight(160.0); // Ajuster la hauteur de la carte pour accommoder les boutons
        card.setMinHeight(160.0); // Ajuster la hauteur minimale de la carte
        card.setPrefWidth(250.0);
        card.setStyle("-fx-border-color: black; -fx-border-width: 2px;"); // Ajouter une bordure noire de 2 pixels

        Label counterLabel = new Label("Test " + counter + ": ");
        counterLabel.setLayoutX(10.0);
        counterLabel.setLayoutY(30.0);
        counterLabel.setFont(new Font(14.0));

        Label contentLabel = new Label(test.getTitle());
        contentLabel.setLayoutX(10.0);
        contentLabel.setLayoutY(60.0);
        contentLabel.setFont(new Font(14.0));

        Label descriptionLabel = new Label("Description : " + test.getDescription());
        descriptionLabel.setLayoutX(10.0);
        descriptionLabel.setLayoutY(90.0);
        descriptionLabel.setFont(new Font(14.0));

        card.getChildren().addAll(counterLabel, contentLabel, descriptionLabel);

        // Ajouter un gestionnaire d'événements pour le clic sur la carte
        card.setOnMouseClicked(event -> {
            afficherQuestions(test); // Appel de la méthode pour afficher les questions du test cliqué
        });

        return card;
    }

    private void afficherQuestions(Test test) {
        // Récupérer l'ID du test
        int testId = test.getId();

        // Récupérer les questions du test en utilisant le service
        List<Question> questions = serviceTest.getQuestionsByTestId(testId);

        // Afficher les questions dans une nouvelle vue
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherQuestionFront.fxml"));
            Pane root = loader.load();

            // Obtenir le contrôleur de la vue AfficherQuestions
            AfficherQuestionFront controller = loader.getController();

            // Passer les questions au contrôleur
            controller.setQuestions(questions);

            // Afficher la nouvelle vue
            nh.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println("Erreur lors du chargement de la vue : " + ex.getMessage());
        }
    }


    @FXML
    private void logout(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment vous déconnecter ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
            nh.getChildren().setAll(pane);
        }
    }
}