package services;

import models.Question;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ServiceQuestion implements IServiceQuestion {
    Connection cnx = DataSource.getInstance().getConnection();  //patron de conception (singleton)
    ObservableList<Question> obList = FXCollections.observableArrayList();

    @Override
    public void ajouterQuestion(Question c) {
        String req = "INSERT INTO question(test_id, content, choice1, choice2, choice3, correctchoice) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, c.getTest_id());
            ps.setString(2, c.getContent());
            ps.setString(3, c.getChoice1());
            ps.setString(4, c.getChoice2());
            ps.setString(5, c.getChoice3());
            ps.setString(6, c.getCorrectchoice());

            ps.executeUpdate();
            System.out.println("Question ajoutée avec succès!");
            //notf
        } catch (SQLException ex) {
            Logger.getLogger(ServiceQuestion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Question> afficherQuestion() {
        List<Question> questions = new ArrayList<>();
        String req = "SELECT * FROM question";
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Question E = new Question();
                E.setId(rs.getInt("id"));
                E.setTest_id(rs.getInt("test_id"));
                E.setContent(rs.getString("content"));
                E.setChoice1(rs.getString("choice1"));
                E.setChoice2(rs.getString("choice2"));
                E.setChoice3(rs.getString("choice3"));
                E.setCorrectchoice(rs.getString("correctchoice"));

                questions.add(E);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceQuestion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return questions;
    }



    @Override
    public void modifierQuestion(Question c) {
        try {
            String req = "UPDATE question SET test_id=?, content=?, choice1=?, choice2=?, choice3=?, correctchoice=? WHERE id=?";
            try (PreparedStatement st = cnx.prepareStatement(req)) {
                st.setInt(1, c.getTest_id());
                st.setString(2, c.getContent());
                st.setString(3, c.getChoice1());
                st.setString(4, c.getChoice2());
                st.setString(5, c.getChoice3());
                st.setString(6, c.getCorrectchoice());
                st.setInt(7, c.getId());
                int rowsAffected = st.executeUpdate();

                // Vérifier si la requête a été exécutée avec succès
                if (rowsAffected > 0) {
                    System.out.println("Question modifiée avec succès");
                } else {
                    System.out.println("Aucune modification effectuée pour la question avec l'ID " + c.getId());
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void supprimerQuestion(int id) {
        try {
            String req = "DELETE FROM question WHERE id=?";
            try (PreparedStatement st = cnx.prepareStatement(req)) {
                st.setInt(1, id);
                st.executeUpdate();
                System.out.println("Question supprimée avec succès");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
