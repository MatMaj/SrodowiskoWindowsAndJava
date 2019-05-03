package classes;

public class BoardGame {
    public String gameName;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void playGame(){
        System.out.println("Players are playing game named: " + gameName);
    }
}
