package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.Question;
import models.Test;
import services.ServiceQuestion;
import services.ServiceTest;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifierQuestion implements Initializable {

    @FXML
    private ComboBox<String> ListTest;

    @FXML
    private TextField fx_choice1;

    @FXML
    private TextField fx_choice2;

    @FXML
    private TextField fx_choice3;

    @FXML
    private TextField fx_content;

    @FXML
    private TextField fx_correctchoice;

    private int id;


    @FXML
    private AnchorPane nh;

    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> list = FXCollections.observableArrayList();
        ServiceTest sc = new ServiceTest();
        ObservableList<Test> obList = FXCollections.observableArrayList();
        obList = sc.afficherTest2();

        ListTest.getItems().clear();
        for (Test nameCat : obList) {
            list.add(nameCat.getTitle());
        }
        ListTest.setItems(list);
    }
    public void initData(int id, String content, String choice1, String choice2, String choice3, String correctchoice,int test_id) {
        this.id = id;
        fx_content.setText(content);
        fx_choice1.setText(choice1);
        fx_choice2.setText(choice2);
        fx_choice3.setText(choice3);
        fx_correctchoice.setText(correctchoice);
        ListTest.setValue(getTestTitleById(test_id));
    }

    private String getTestTitleById(int test_id) {
        ServiceTest sc = new ServiceTest();
        Test test = sc.getTestById(test_id);
        return (test != null) ? test.getTitle() : null;
    }

    @FXML
    void ModifierQuestion(ActionEvent event) {
        String content = fx_content.getText();
        String choice1 = fx_choice1.getText();
        String choice2 = fx_choice2.getText();
        String choice3 = fx_choice3.getText();
        String correctchoice = fx_correctchoice.getText();

        if (content.isEmpty()) {
            showAlert("Erreur : Veuillez donner un contenu.");
        } else if (choice1.isEmpty()) {
            showAlert("Erreur : Veuillez donner une choice1.");
        } else if (choice2.isEmpty()) {
            showAlert("Erreur : Veuillez donner une choice2.");
        } else if (choice3.isEmpty()) {
            showAlert("Erreur : Veuillez donner une choice3.");
        } else if (correctchoice.isEmpty()) {
            showAlert("Erreur : Veuillez donner une correctchoice.");
        } else {
            ServiceTest sc = new ServiceTest();
            int test_id = sc.get_testIdByTitle(ListTest.getValue());
            if (test_id == -1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Erreur : Test non trouvé");
                alert.show();
                return;
            }

            // Récupérer l'identifiant de la question à partir de l'initialisation
            int questionId = this.id; // Ajoutez cette ligne

            // Transmettre l'identifiant de la question à la méthode modifierQuestion
            Question question = new Question(questionId, test_id, content, choice1, choice2, choice3, correctchoice); // Modifier cette ligne

            ServiceQuestion serviceQuestion = new ServiceQuestion();
            serviceQuestion.modifierQuestion(question);
            showAlert("Question modifiée avec succès !");

            // Redirection vers l'affichage des questions
            redirectToAfficherQuestion();
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
    private void redirectToAfficherQuestion() {
        try {
            FXMLLoader loader = createFXMLLoader("/AfficherQuestion.fxml");
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
    void ReturnToAffiche(ActionEvent event) {
        try {
            FXMLLoader loader = createFXMLLoader("/AfficherQuestion.fxml");
            Parent root = loader.load();
            nh.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println("Erreur lors du chargement de la vue : " + ex.getMessage());
        }
    }
}


