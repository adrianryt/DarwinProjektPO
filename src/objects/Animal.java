package objects;

import data.Vector2d;
import map.IWorldMap;
import map.MapDirection;

public class Animal implements IMapElement{

    private MapDirection orientation;
    private Vector2d position;
    private int energy;
    private IWorldMap map;
    public int birthDay = 0;
    public int kids = 0;
    public Genotype genotype;

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }


    public void setOrientation(MapDirection direction){
        this.orientation = direction;
    }


    public Animal(IWorldMap map, Vector2d initialPosition){

        this(map, initialPosition, new Genotype(32));
    }

    public Animal(IWorldMap map, Vector2d initialPosition, Genotype genotype){
        this.position = initialPosition;
        this.map = map;
        this.genotype = genotype;
        setOrientation(MapDirection.NORTH);
        this.energy = 5;
        map.place(this);
    }

    @Override
    public String toString() {
        return this.orientation.toString();
    }

    public void move(MapDirection director) {
       Vector2d newPosition = this.position.add(director.toUnitVector());
       newPosition = map.positionMapped(newPosition);
       this.orientation = director;
       this.position = newPosition;
    }
}
