package Sweeper;

import sweeper.Box;

public class Game {
    private Bomb bomb;
    private Flag flag;
    private GameState state;

    public GameState getState() {
        return state;
    }

    public Game (int cols, int rows, int bombs){
        Ranges.setSize(new Coord (cols, rows));
        bomb = new Bomb (bombs);
        flag = new Flag();
    }
    public void start (){
        bomb.start();
        flag.start();
        state = GameState.PLAYED;
    }
    public sweeper.Box getBox (Coord coord){
        if (flag.get(coord) == Box.opened)
            return bomb.get(coord);
        else
            return flag.get(coord);
    }

    public void pressLeftButton(Coord coord) {
        if (gameOver()) return;
        openBox (coord);
        checkWinner();
    }

    private boolean gameOver() {
        if (state == GameState.PLAYED)
            return false;
        start();
        return true;
    }

    private void checkWinner(){
        if (state == GameState.PLAYED)
            if (flag.getCountOfClosed() == bomb.getTotalBombs())
                state = GameState.WINNER;
    }

    private void openBox (Coord coord) {
        switch (flag.get(coord)){
            case opened: setOpenedToClosedBoxesAroundNumber (coord); return;
            case flaged: return;
            case closed:
                switch (bomb.get(coord)){
                    case zero: openBoxesAround(coord); return;
                    case BOMB: openBombs(coord); return;
                    default : flag.setOpenedToBox(coord); break;
                }
        }
    }
    private void setOpenedToClosedBoxesAroundNumber (Coord coord){
        if (bomb.get (coord) != Box.BOMB)
            if (flag.getCountOfFlagedBoxesAround(coord) == bomb.get(coord).getNumber())
                for (Coord around: Ranges.getCoordAround(coord))
                    if (flag.get (around) == Box.closed)
                        openBox (around);
    }

    private void openBombs(Coord bombed) {
        state = GameState.BOMBED;
        flag.setBombedToBox(bombed);
        for (Coord coord: Ranges.getAllCoords())
            if (bomb.get (coord) == Box.BOMB)
                flag.setOpenedToClosedBombBox (coord);
        else
            flag.setNoBombToFlagedSafeBox(coord);
    }

    private void openBoxesAround(Coord coord) {
        flag.setOpenedToBox(coord);
        for (Coord around: Ranges.getCoordAround(coord))
            openBox(around);
    }

    public void pressRightButton(Coord coord) {
        flag.toggleFlagedToBox (coord);
    }
}
