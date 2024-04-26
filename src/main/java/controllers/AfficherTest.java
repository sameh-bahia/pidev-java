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
import models.Test;
import services.ServiceTest;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AfficherTest implements Initializable {

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

      /*  Label counterLabel = new Label("Test " + counter + ": ");
        counterLabel.setLayoutX(10.0);
        counterLabel.setLayoutY(30.0);
        counterLabel.setFont(new Font(14.0));
*/
        Label contentLabel = new Label(test.getTitle());
        contentLabel.setLayoutX(10.0);
        contentLabel.setLayoutY(60.0);
        contentLabel.setFont(new Font(14.0));

        Label descriptionLabel = new Label("Description : " + test.getDescription());
        descriptionLabel.setLayoutX(10.0);
        descriptionLabel.setLayoutY(90.0);
        descriptionLabel.setFont(new Font(14.0));

        HBox buttonPane = createButtonPane(test);
        buttonPane.setLayoutX(10.0);
        buttonPane.setLayoutY(120.0); // Positionner les boutons en dessous du contenu du test
        card.getChildren().addAll(contentLabel, descriptionLabel, buttonPane);

        return card;
    }

    private HBox createButtonPane(Test test) {
        HBox buttonPane = new HBox();

        ImageView deleteImageView = new ImageView(new Image(getClass().getResourceAsStream("/Trash.png")));
        deleteImageView.setFitHeight(20.0);
        deleteImageView.setFitWidth(20.0);
        deleteImageView.setPickOnBounds(true);
        deleteImageView.setPreserveRatio(true);
        deleteImageView.setOnMouseClicked((MouseEvent event) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Are you sure you want to delete this test?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Trouver le parent de buttonPane qui est la carte de test
                Pane card = (Pane) buttonPane.getParent();

                // Supprimer la carte de la VBox
                vbox1.getChildren().remove(card);

                // Supprimer le test de la base de données
                serviceTest.supprimerTest(test.getId());
                afficherTests(); // Rafraîchir l'affichage
            }
        });

        ImageView editImageView = new ImageView(new Image(getClass().getResourceAsStream("/Edit.png")));
        editImageView.setFitHeight(20.0);
        editImageView.setFitWidth(20.0);
        editImageView.setPickOnBounds(true);
        editImageView.setPreserveRatio(true);
        editImageView.setOnMouseClicked((MouseEvent event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierTest.fxml"));
                Pane root = loader.load();
                ModifierTest modifierTest = loader.getController();
                modifierTest.initData(test.getId(), test.getTitle(), test.getDescription());
                nh.getChildren().setAll(root);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        buttonPane.getChildren().addAll(deleteImageView, editImageView);

        return buttonPane;
    }

    @FXML
    private void goToAjouterTest(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterTest.fxml"));
            Pane root = loader.load();
            nh.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println("Erreur lors du chargement de la vue : " + ex.getMessage());
        }
    }

    @FXML
    private void goToMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu.fxml"));
            Pane root = loader.load();
            nh.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println("Erreur lors du chargement de la vue : " + ex.getMessage());
        }
    }

    @FXML
    private void goToAjouter(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterTest.fxml"));
            Pane root = loader.load();
            nh.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println("Erreur lors du chargement de la vue : " + ex.getMessage());
        }
    }


}
