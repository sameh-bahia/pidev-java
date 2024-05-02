package models;

import java.util.Arrays;
import java.util.List;

public class Question {

        private int id;
    private String content;
    private String choice1;
    private String choice2;
    private String choice3;
    private String correctchoice;
    private int test_id;

    public Question() {
    }

    public Question( int test_id,String content, String choice1, String choice2, String choice3, String correctchoice) {
        this.content = content;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.correctchoice = correctchoice;
        this.test_id = test_id;
    }

    public Question(int id,  int test_id,String content, String choice1, String choice2, String choice3, String correctchoice) {
        this.id = id;
        this.content = content;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.correctchoice = correctchoice;
        this.test_id = test_id;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getChoice1() {
        return choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public String getCorrectchoice() {
        return correctchoice;
    }

    public int getTest_id() {
        return test_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public void setCorrectchoice(String correctchoice) {
        this.correctchoice = correctchoice;
    }

    public void setTest_id(int test_id) {
        this.test_id = test_id;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", choice1='" + choice1 + '\'' +
                ", choice2='" + choice2 + '\'' +
                ", choice3='" + choice3 + '\'' +
                ", correctchoice='" + correctchoice + '\'' +
                ", test_id=" + test_id +
                '}';
    }

    public List<String> getChoices() {
        // Créer une liste contenant tous les choix
        return Arrays.asList(choice1, choice2, choice3);
    }

}

