package Sweeper;

import sweeper.Box;

class Flag {
    private Matrix flagMap;
    private int countOfClosedBoxes;
    void start(){
        flagMap = new Matrix(Box.closed);
        countOfClosedBoxes = Ranges.getSize().x * Ranges.getSize().y;
    }
    Box get (Coord coord){
        return  flagMap.get (coord);
    }

    void setOpenedToBox(Coord coord) {
        flagMap.set (coord, Box.opened);
        countOfClosedBoxes --;
    }
    void toggleFlagedToBox (Coord coord){
        switch (flagMap.get(coord)){
            case flaged : setClosedBox (coord); break;
            case closed: setFlagedToBox(coord); break;
        }
    }
    private void setClosedBox(Coord coord){
        flagMap.set (coord, Box.closed);
    }

    private void setFlagedToBox(Coord coord) {
        flagMap.set (coord, Box.flaged);
    }

    int getCountOfClosed() {
        return countOfClosedBoxes;
    }

    void setBombedToBox(Coord coord) {
        flagMap.set(coord, Box.bombed);
    }

    void setOpenedToClosedBombBox(Coord coord) {
        if (flagMap.get (coord) == Box.closed)
            flagMap.set (coord, Box.opened);
    }

    void setNoBombToFlagedSafeBox(Coord coord) {
        if (flagMap.get (coord) == Box.flaged)
            flagMap.set (coord, Box.nobomb);
    }

    int getCountOfFlagedBoxesAround (Coord coord) {
        int count = 0;
        for (Coord around: Ranges.getCoordAround(coord))
            if (flagMap.get (around) == Box.flaged)
                count ++;
        return count;
    }
}
