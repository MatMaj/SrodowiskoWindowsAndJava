package classes;

public class BoardGame {
    public String gameName;
    public int cube=1;

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

    public int getCube() {
        return cube;
    }

    public String playGame(){
        if(gameName.equals("")){
            return "There is no game to be played!";
        }else {

            return "Players are playing game named: " + gameName;
        }
    }
    public String rollTheDice(){
        int random = (int )(Math.random() * 6 + 1);
        cube=random;
        return "You rolled: "+random;
    }
}
