package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.Test;
import services.ServiceTest;

import java.io.IOException;

public class AjouterTest {

    @FXML
    private TextArea fx_description;

    @FXML
    private TextField fx_title;
    @FXML

    private AnchorPane nh;
    @FXML
    void ReturnToAfficherTest(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherTest.fxml"));
        try {
            Parent root = loader.load();
            nh.getChildren().setAll(root);

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    void ajouterTest(ActionEvent event) {
        String description = fx_description.getText();
        String title = fx_title.getText();

        if (title.isEmpty()) {
            showAlert("Erreur : Veuillez donner un titre.");
        } else if (description.isEmpty()) {
            showAlert("Erreur : Veuillez donner une description.");
        } else {
            Test test = new Test(title, description);
            ServiceTest serviceTest = new ServiceTest();
            serviceTest.ajouterTest(test);
            showAlert("Test ajouté avec succès !");

            // Redirection vers l'affichage des tests
           // redirectToAfficherTest();
        }
    }

    // Méthode pour afficher une alerte
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
    // Méthode pour rediriger vers l'affichage des tests
    private void redirectToAfficherTest() {
        try {
            FXMLLoader loader = createFXMLLoader("/AfficherTest.fxml");
            Parent root = loader.load();
            nh.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println("Erreur lors du chargement de la vue : " + ex.getMessage());
        }
    }
    private FXMLLoader createFXMLLoader(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlPath));
        return loader;
    }
}


