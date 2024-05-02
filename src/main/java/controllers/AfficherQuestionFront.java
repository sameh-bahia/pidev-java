package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import models.Question;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherQuestionFront implements Initializable {

    @FXML
    private AnchorPane nh;

    @FXML
    private VBox vbox1;

    @FXML
    private Button submitButton;

    private List<Question> questions;
    private List<Pane> questionCards = new ArrayList<>();
    private ToggleGroup toggleGroup;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialiser l'affichage des questions
        afficherQuestions();
    }

    // Méthode pour afficher les questions correspondant au test sélectionné
    private void afficherQuestions() {
        vbox1.getChildren().clear(); // Effacer le contenu actuel de la VBox

        // Initialiser le groupe de boutons
        toggleGroup = new ToggleGroup();

        if (questions != null && !questions.isEmpty()) {
            vbox1.setSpacing(50.0); // Ajouter un espacement vertical entre chaque carte
            vbox1.setPadding(new Insets(30.0)); // Ajouter un espacement horizontal et vertical autour de la VBox

            int counter = 1; // Compteur pour chaque question
            for (Question question : questions) {
                Pane card = createQuestionCard(question, counter);
                questionCards.add(card); // Ajouter la carte à la liste
                vbox1.getChildren().add(card); // Ajouter la carte à la VBox
                counter++; // Incrémenter le compteur de question
            }

            // Créer et configurer le bouton Soumettre
            submitButton = new Button("Soumettre");
            submitButton.setOnAction(event -> handleSubmit());
            vbox1.getChildren().add(submitButton); // Ajouter le bouton à la VBox
        }
    }

    // Méthode pour créer une carte de question
    private Pane createQuestionCard(Question question, int counter) {
        Pane card = new Pane();
        card.setPrefHeight(200.0); // Augmenter la hauteur pour accommoder les boutons radio
        card.setMinHeight(200.0);
        card.setPrefWidth(250.0);
        card.setStyle("-fx-border-color: black; -fx-border-width: 2px;"); // Ajouter une bordure noire de 2 pixels

        Label counterLabel = new Label("Question " + counter + ": ");
        counterLabel.setLayoutX(10.0);
        counterLabel.setLayoutY(30.0);
        counterLabel.setFont(new Font(14.0));

        Label contentLabel = new Label(question.getContent());
        contentLabel.setLayoutX(10.0);
        contentLabel.setLayoutY(60.0);
        contentLabel.setFont(new Font(14.0));

        RadioButton choice1Radio = new RadioButton(question.getChoice1());
        choice1Radio.setLayoutX(10.0);
        choice1Radio.setLayoutY(90.0);
        choice1Radio.setFont(new Font(14.0));

        RadioButton choice2Radio = new RadioButton(question.getChoice2());
        choice2Radio.setLayoutX(10.0);
        choice2Radio.setLayoutY(120.0);
        choice2Radio.setFont(new Font(14.0));

        RadioButton choice3Radio = new RadioButton(question.getChoice3());
        choice3Radio.setLayoutX(10.0);
        choice3Radio.setLayoutY(150.0);
        choice3Radio.setFont(new Font(14.0));

        // Ajouter les boutons radio au groupe de boutons
        toggleGroup = new ToggleGroup();
        choice1Radio.setToggleGroup(toggleGroup);
        choice2Radio.setToggleGroup(toggleGroup);
        choice3Radio.setToggleGroup(toggleGroup);

        // Ajouter tous les éléments à la carte
        card.getChildren().addAll(counterLabel, contentLabel, choice1Radio, choice2Radio, choice3Radio);

        return card;
    }

    // Méthode appelée lors du clic sur le bouton Soumettre
    // Méthode appelée lors du clic sur le bouton Soumettre
    @FXML
    private void handleSubmit() {
        int totalQuestions = questions.size();
        int totalCorrectAnswers = 0; // Nombre total de réponses correctes

        // Parcourir toutes les questions
        for (int i = 0; i < totalQuestions; i++) {
            Question question = questions.get(i);

            // Récupérer la carte associée à la question
            Pane card = questionCards.get(i);

            // Récupérer le bouton sélectionné du groupe de boutons de la question actuelle
            RadioButton selectedRadio = (RadioButton) toggleGroup.getSelectedToggle();

            // Si aucun bouton n'est sélectionné, passer à la question suivante
            if (selectedRadio == null) {
                continue;
            }

            String selectedAnswer = selectedRadio.getText();
            String correctAnswer = question.getCorrectchoice();

            // Mettre en évidence la réponse correcte en vert et la réponse incorrecte en rouge
            if (selectedAnswer.equals(correctAnswer)) {
                highlightAnswer(card, selectedRadio, true); // Mettre en évidence la réponse correcte en vert
                totalCorrectAnswers++; // Incrémenter le nombre total de réponses correctes
            } else {
                // Trouver le bouton radio correspondant à la réponse correcte
                RadioButton correctRadio = findRadioButton(card, correctAnswer);
                highlightAnswer(card, selectedRadio, false); // Mettre en évidence la réponse incorrecte en rouge
                highlightAnswer(card, correctRadio, true); // Mettre en évidence la réponse correcte en vert
            }
        }

        // Calculer le pourcentage de réponses correctes pour l'ensemble du quiz
        double percentageCorrect = ((double) totalCorrectAnswers / totalQuestions) * 100;
        String formattedPercentage = String.format("%.2f", percentageCorrect); // Formater le pourcentage avec deux décimales

        // Afficher le pourcentage dans l'interface
        Label percentageLabel = new Label("Pourcentage de réponses correctes : " + formattedPercentage + "%");
        vbox1.getChildren().add(percentageLabel);
    }


    // Méthode pour mettre en évidence la réponse correcte ou incorrecte
    private void highlightAnswer(Pane card, RadioButton radioButton, boolean isCorrect) {
        Color color;
        if (isCorrect) {
            color = Color.GREEN; // Couleur verte pour la réponse correcte
        } else {
            color = Color.RED; // Couleur rouge pour la réponse incorrecte
        }
        radioButton.setTextFill(color);
    }

    // Méthode pour trouver le bouton radio correspondant à une réponse donnée dans une carte
    private RadioButton findRadioButton(Pane card, String answer) {
        for (javafx.scene.Node node : card.getChildren()) {
            if (node instanceof RadioButton && ((RadioButton) node).getText().equals(answer)) {
                return (RadioButton) node;
            }
        }
        return null;
    }

    // Méthode pour définir les questions
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
        afficherQuestions(); // Mettre à jour l'affichage des questions
    }
}
