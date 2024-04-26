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

public class ModifierTest {

    @FXML
    private TextArea fx_description;

    @FXML
    private TextField fx_title;

    @FXML
    private AnchorPane nh;
    private int id; // Nouvelle variable pour stocker l'ID

    // Nouvelle méthode pour initialiser l'ID
    public void initData(int id, String title, String description) {
        this.id = id;
        fx_title.setText(title);
        fx_description.setText(description);

    }
    @FXML
    void ReturnToAfficherTest(ActionEvent event) {

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

    @FXML
    void modifierTest(ActionEvent event) {
        ServiceTest inter = new ServiceTest();
        String title = fx_title.getText();
        String description = fx_description.getText();

        if (title.isEmpty()) {
            showAlert("Erreur : Veuillez donner un titre de test.");
        } else if (description.isEmpty()) {
            showAlert("Erreur : Veuillez donner une description de test.");
        } else {
            Test test = new Test(id, title, description);
            inter.modifierTest(test);
            showAlert("Test modifié avec succès !");

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
}
