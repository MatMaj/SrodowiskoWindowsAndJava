package classes;

public class BoardGame {
    public String gameName;

    public BoardGame(){

    }

    public BoardGame(String gameName){
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String playGame(){
        return "Players are playing game named: " + gameName;
    }
    public String rollTheDice(){
        int random = (int )(Math.random() * 6 + 1);
        return "You rolled: "+random;
    }
}
