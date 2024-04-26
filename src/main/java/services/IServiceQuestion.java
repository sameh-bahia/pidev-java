package services;

import models.Question;

import java.util.List;

public interface IServiceQuestion {
    public void ajouterQuestion(Question c);

    public List<Question> afficherQuestion();

    public void modifierQuestion(Question c);

    public void supprimerQuestion(int id);
}
