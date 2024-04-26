package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import services.ServiceQuestion;
import services.ServiceTest;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AfficherQuestionFront implements Initializable {

    @FXML
    private AnchorPane nh;

    @FXML
    private VBox vbox1;

    ServiceQuestion serviceQuestion = new ServiceQuestion();
    ServiceTest serviceTest = new ServiceTest();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        afficherQuestions();
    }

    private void afficherQuestions() {
        vbox1.getChildren().clear(); // Effacer le contenu actuel de la VBox
        List<Question> questionList = serviceQuestion.afficherQuestion();

        vbox1.setSpacing(50.0); // Ajoute un espacement vertical entre chaque carte
        vbox1.setPadding(new Insets(30.0)); // Ajoute un espacement horizontal et vertical autour de la VBox

        HBox currentRow = new HBox(); // Créer une nouvelle rangée
        currentRow.setSpacing(20.0); // Espacement horizontal entre les cartes

        int questionsAdded = 0; // Nombre de questions ajoutées dans la rangée actuelle

        int counter = 1; // Compteur pour chaque question
        for (Question question : questionList) {
            Pane card = createQuestionCard(question, counter);
            currentRow.getChildren().add(card); // Ajouter la carte à la rangée actuelle
            questionsAdded++;

            // Si trois questions ont été ajoutées, ajouter la rangée à la VBox et créer une nouvelle rangée
            if (questionsAdded == 3) {
                vbox1.getChildren().add(currentRow); // Ajouter la rangée à la VBox
                currentRow = new HBox(); // Créer une nouvelle rangée
                currentRow.setSpacing(20.0); // Espacement horizontal entre les cartes
                questionsAdded = 0; // Réinitialiser le compteur de questions ajoutées
            }
            counter++; // Incrémenter le compteur de question
        }

        // Ajouter un espace vertical entre les lignes de cartes
        vbox1.getChildren().add(new Pane());

        // Ajouter la rangée restante si elle n'est pas complète
        if (questionsAdded > 0) {
            vbox1.getChildren().add(currentRow);
        }
    }

    private Pane createQuestionCard(Question question, int counter) {
        Pane card = new Pane();
        card.setPrefHeight(120.0);
        card.setMinHeight(120.0);
        card.setPrefWidth(250.0);

        Label counterLabel = new Label("Question " + counter + ": ");
        counterLabel.setLayoutX(10.0);
        counterLabel.setLayoutY(30.0);
        counterLabel.setFont(new Font(14.0));

        Label contentLabel = new Label(question.getContent());
        contentLabel.setLayoutX(10.0);
        contentLabel.setLayoutY(60.0);
        contentLabel.setFont(new Font(14.0));

        Label choice1Label = new Label("Choice 1: " + question.getChoice1());
        choice1Label.setLayoutX(10.0);
        choice1Label.setLayoutY(90.0);
        choice1Label.setFont(new Font(14.0));

        Label choice2Label = new Label("Choice 2: " + question.getChoice2());
        choice2Label.setLayoutX(10.0);
        choice2Label.setLayoutY(120.0);
        choice2Label.setFont(new Font(14.0));

        Label choice3Label = new Label("Choice 3: " + question.getChoice3());
        choice3Label.setLayoutX(10.0);
        choice3Label.setLayoutY(150.0);
        choice3Label.setFont(new Font(14.0));

        Label correctChoiceLabel = new Label("Correct Choice: " + question.getCorrectchoice());
        correctChoiceLabel.setLayoutX(10.0);
        correctChoiceLabel.setLayoutY(180.0);
        correctChoiceLabel.setFont(new Font(14.0));

        Test test = serviceTest.getTestById(question.getTest_id());
        Label testLabel = new Label("Test: " + test.getTitle());
        testLabel.setLayoutX(10.0);
        testLabel.setLayoutY(210.0);
        testLabel.setFont(new Font(14.0));

        // Ajouter tous les éléments à la carte
        card.getChildren().addAll(counterLabel, contentLabel, choice1Label, choice2Label, choice3Label, correctChoiceLabel, testLabel);

        return card;
    }





}
