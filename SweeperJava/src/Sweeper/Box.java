package sweeper;

public enum Box {
    zero,
    num1,
    num2,
    num3,
    num4,
    num5,
    num6,
    num7,
    num8,
    BOMB,
    opened,
    closed,
    flaged,
    bombed,
    nobomb;

    public Object image;

    public Box getNextNumberBox(){
        return sweeper.Box.values()[this.ordinal() + 1];
    }
    public int getNumber(){
        return this.ordinal();
    }

}