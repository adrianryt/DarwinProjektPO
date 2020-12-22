package map;

import data.Vector2d;
import objects.Animal;
import objects.Grass;
import objects.IMapElement;

import java.util.*;

public class TheMap extends AbstractWorldMap {
    public final Vector2d jungleSize;
    public final Vector2d junglePosition;


    private Random generator = new Random();;
    private int numberOfGrassInJungle = 0;
    private int numberOfGrassOutOfJungle = 0;
    private final int grassEnergy;


    @Override
    public int getNumberOfGrassInJungle() {
        return numberOfGrassInJungle;
    }
    @Override
    public int getNumberOfGrassOutOfJungle() {
        return numberOfGrassOutOfJungle;
    }

    public TheMap(Vector2d mapSize, Vector2d jungleSize, int grassEnergy){
        this.grassEnergy = grassEnergy;
        this.mapSize = mapSize;
        this.jungleSize = jungleSize;
        this.junglePosition = new Vector2d(Math.round((mapSize.x - jungleSize.x)/2),Math.round((mapSize.y - jungleSize.y)/2));
    }

    @Override
    public boolean isInJungle(Vector2d position){
        return position.x >= junglePosition.x && position.y >= junglePosition.y
                && position.x < junglePosition.x + jungleSize.x
                && position.y < junglePosition.y + jungleSize.y;
    }

    private void placeGrassInJungle(int n){
        //count how many animals are in the jungle
        int animalsInJungle = countAnimalsInRectangle(this.junglePosition, this.junglePosition.add(this.jungleSize));
        for(int i = 0; i < n; i++){
            if(this.jungleSize.x * this.jungleSize.y <= this.numberOfGrassInJungle + animalsInJungle){

                return;
            }
            int x = generator.nextInt(jungleSize.x) + junglePosition.x;
            int y = generator.nextInt(jungleSize.y) + junglePosition.y;
            //going through the jungle, first increment x then if we are at the end of the jungle we go one line up
            while(hashAnimals.containsKey(new Vector2d(x,y)) || hashGrass.containsKey(new Vector2d(x,y))){
                x++;
                if(x == this.junglePosition.x + jungleSize.x){
                    x = this.junglePosition.x;
                    y++;
                    if(y == this.junglePosition.y + this.jungleSize.y){
                        y = this.junglePosition.y;
                    }
                }
            }
            Grass grass = new Grass(new Vector2d(x,y), this.grassEnergy);
            hashGrass.put(grass.getPosition(),grass);
            this.numberOfGrassInJungle++;
        }
    }

    private void placeGrassOutOfJungle(int n){
        //count how many animals are out of the jungle
        int animalsOutOfJungle = countAnimalsInRectangle(new Vector2d(0,0), this.mapSize) -
                countAnimalsInRectangle(this.junglePosition, this.junglePosition.add(this.jungleSize));
        for(int i = 0; i < n; i++){
            if(this.mapSize.x * this.mapSize.y - this.jungleSize.x * this.jungleSize.y <=
                    this.numberOfGrassOutOfJungle + animalsOutOfJungle){
                return;
            }
            int x = generator.nextInt(mapSize.x);
            int y = generator.nextInt(mapSize.y);
            while(hashAnimals.containsKey(new Vector2d(x,y)) || hashGrass.containsKey(new Vector2d(x,y)) || isInJungle(new Vector2d(x,y))) {
                x++;
                if(x == this.mapSize.x){
                    x = 0;
                    y++;
                    if(y == this.mapSize.y){
                        y = 0;
                    }
                }
            }
            Grass grass = new Grass(new Vector2d(x,y), this.grassEnergy);
            hashGrass.put(grass.getPosition(),grass);
            this.numberOfGrassOutOfJungle++;
        }
    }

    private int countAnimalsInRectangle(Vector2d leftDownCorner, Vector2d rightUpCorner){
        int result = 0;
        for(int x = leftDownCorner.x; x < rightUpCorner.x; x++){
            for(int y = leftDownCorner.y; y < rightUpCorner.y; y++){
                if(hashAnimals.containsKey(new Vector2d(x,y))){
                    result++;
                }
            }
        }
        return result;
    }

    public void makeGrassGrow(){
        placeGrassInJungle(1);
        placeGrassOutOfJungle(1);
    }

    public void makeAnimalsEat() {
        LinkedList<Grass> grass = new LinkedList<>();
        for(Grass grass1: hashGrass.values()) {
           grass.add(grass1);
        }

        for(IMapElement element: grass){
            Vector2d position = element.getPosition();
            if(hashAnimals.containsKey(position)){
                LinkedList<Animal> animals = hashAnimals.get(position);
                Collections.sort(animals, (a,b) -> a.getEnergy() < b.getEnergy() ? -1 : a.getEnergy() == b.getEnergy() ? 0 : 1);
                Animal[] best = animals.stream().filter(animal -> animal.getEnergy() == animals.getLast().getEnergy()).toArray(Animal[]::new);
                int energyToGive = this.grassEnergy / best.length;
                for(Animal animal : best){
                    animal.setEnergy(animal.getEnergy()+ energyToGive);
                }
                if(isInJungle(position)){
                    this.numberOfGrassInJungle--;
                }
                else {
                    this.numberOfGrassOutOfJungle--;
                }
                hashGrass.remove(position);
            }

        }
    }

    public Vector2d positionMapped(Vector2d position){
        return new Vector2d(Math.floorMod(position.x, mapSize.x), Math.floorMod(position.y, mapSize.y));
    }

}
