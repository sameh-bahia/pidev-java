package services;

import models.Test;

import java.util.List;

public interface IServiceTest {
    public void ajouterTest(Test c);

    public List<Test> afficherTest();

    public void modifierTest(Test c);

    public void supprimerTest(int id);
}
