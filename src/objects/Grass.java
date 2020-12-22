package objects;

import data.Vector2d;

public class Grass implements IMapElement{
    private final Vector2d position;
    private final int grassEnergy;

    public Grass(Vector2d initialPosition, int energy){
        this.position = initialPosition;
        this.grassEnergy = energy;
    }
    @Override
    public Vector2d getPosition(){
        return position;
    }
    public int getGrassEnergy(){
        return grassEnergy;
    }

}
