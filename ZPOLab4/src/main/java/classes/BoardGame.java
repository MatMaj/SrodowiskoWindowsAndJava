package classes;

public class BoardGame {
    public String gameName="none";
    public int cube=1;
    private int rollcounter=0;
    private String oldgame=gameName;
    private boolean gameStarted=false;
    public double doubleCheck=0;
    public int intCheck=0;
    public BoardGame(){

    }

    public int getIntCheck() {
        return intCheck;
    }

    public void setIntCheck(int intCheck) {
        this.intCheck = intCheck;
    }

    public double getDoubleCheck() {
        return doubleCheck;
    }

    public void setDoubleCheck(double doubleCheck) {
        this.doubleCheck = doubleCheck;
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
        oldgame = gameName;
        if(gameName.equals("none")){
            return "There is no game to be played! Pleas set a game name!";
        }
        gameStarted=true;
        return "Players playing game named: " + gameName;
    }
    public String rollTheDice(){
        if(gameStarted==false){
            return "You dont have any game title to play!";
        }
        int random = (int )(Math.random() * 6 + 1);
        cube=random;
        rollcounter++;
        return "You rolled: "+random;
    }
    private String movePawn(){
        if(gameStarted==false){
            return "You dont have any game title to play!";
        }else{
            if(rollcounter==0){
                return "Roll the dice firts!";
            }
            rollcounter=0;
            return "Player moved his pawn by "+ cube +" fields";
        }
    }
    private String startOtherGame(){
        if(oldgame.equals(gameName)){
            return "Pleas change or select new game title!";
        }
        return "Starting a new game!";
    }
    private int endGame(){
        gameStarted=false;
        return 1;
    }
}
