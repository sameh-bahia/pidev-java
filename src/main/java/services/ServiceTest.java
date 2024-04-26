package services;

import models.Question;
import models.Test;

import java.util.List;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

////

public class ServiceTest implements IServiceTest{
    Connection cnx = DataSource.getInstance().getConnection(); //instance
    ObservableList<Test> obList = FXCollections.observableArrayList();
    @Override
    public void ajouterTest(Test c) {
        String req = "INSERT INTO Test(title, description) VALUES (?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, c.getTitle());
            ps.setString(2, c.getDescription());

            ps.executeUpdate();
            System.out.println("Test ajoutée avec succès!");
        } catch (SQLException ex) {
            Logger.getLogger(ServiceQuestion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public List<Test> afficherTest() {

        List<Test> tests = new ArrayList<>();//creation list vide
        String req = "SELECT * FROM Test"; //afficher tous les test
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) { //connexion excetion
            while (rs.next()) {   //tant que mezl fama des lignes recuperer
                Test E = new Test();
                E.setId(rs.getInt("id"));
                E.setTitle(rs.getString("title"));
                E.setDescription(rs.getString("description"));
                tests.add(E);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceQuestion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tests;
    }

    @Override
    public void modifierTest(Test c) {
        try {
            String req = "UPDATE Test SET title=?, description=? WHERE id=?";
            try (PreparedStatement st = cnx.prepareStatement(req)) {
                st.setString(1, c.getTitle());
                st.setString(2, c.getDescription());
                st.setInt(3, c.getId());
                int rowsAffected = st.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Test modifiée avec succès");
                } else {
                    System.out.println("Aucune modification effectuée pour la Test avec l'ID " + c.getId());
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void supprimerTest(int id) {
        try {
            String req = "DELETE FROM Test WHERE id=?";
            try (PreparedStatement st = cnx.prepareStatement(req)) {
                st.setInt(1, id);
                st.executeUpdate();
                System.out.println("Test supprimée avec succès");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public ObservableList<Test> afficherTest2() {
        String sql = "SELECT * FROM Test";
        try {
            Statement statement = cnx.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt(1);
                String title = result.getString(2);
                String description = result.getString(3);
                Test c = new Test(id,title,description);
                obList.add(c);
            }
            result.close();
            statement.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return obList;
    }

    public int get_testIdByTitle(String title) {
        int id = -1;
        String sql = "SELECT id FROM Test WHERE title = ?";

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setString(1, title);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    id = result.getInt("id");
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return id;
    }
    public Test getTestById(int idTest) {
        Test test = null;
        String sql = "SELECT * FROM Test WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setInt(1, idTest);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String title = result.getString("title");
                    String description = result.getString("description");
                    test = new Test(id,title,description);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return test;
    }
    }

