package map;

import data.Vector2d;
import objects.Animal;
import objects.Grass;
import objects.IMapElement;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap {
    protected Map<Vector2d, LinkedList<Animal>> hashAnimals= new HashMap<>();
    protected Map<Vector2d, Grass> hashGrass= new HashMap<>();
    protected Vector2d mapSize;
    protected double averageAnimalsEnergy;
    protected double numberOfAnimals;
    protected double averageKidsNumber;

    public double getNumberOfAnimals() {
        return numberOfAnimals;
    }

    public double getAverageAnimalsEnergy() {
        return averageAnimalsEnergy;
    }

    public Vector2d getMapSize() {
        return mapSize;
    }

    @Override
    public Map<Vector2d, LinkedList<Animal>> getHashAnimals() {
        return hashAnimals;
    }

    public Map<Vector2d, Grass> getHashGrass() {
        return hashGrass;
    }

    @Override
    public String toString(){
        return "";
    }

    @Override
    public boolean canMoveTo(Vector2d pos){
        if(pos.x >= 0 && pos.y >=0 && pos.x <= mapSize.x && pos.y <= mapSize.y){
            return true;
        }
        return false;
    }

    public double getAverageKidsNumber() {
        return averageKidsNumber;
    }

    public String getMostCommonGenotype(){
        int[] a = {0};
        int[] result = {0,0,0,0,0,0,0,0};
        Map<int[], int[]> genotypes= new HashMap<>();
        for(LinkedList<Animal> animals: hashAnimals.values()){
            for(Animal animal: animals){
                if (!genotypes.containsKey(animal.genotype.getGeneArr())) {
                    genotypes.put(animal.genotype.getGeneArr(), a);
                }
                genotypes.get(animal.genotype.getGeneArr())[0]++;
            }
        }
        int maks = 0;
        for(int [] genes: genotypes.keySet()){
            if(genotypes.get(genes)[0] > maks){
                maks = genotypes.get(genes)[0];
                result = genes;
            }
        }
        return changeToString(result);
    }

    private String changeToString(int[] A){
        String result = "[";
        for(int j=0; j<A.length; j++){
            result += (A[j] + ",");
        }
        result = result.substring(0,result.length()-1);
        result +="]";
        return result;
    }

    public void countAverageAnimalsEnergy() {
        int a = 0;
        double b = 0;
        int kids = 0;
        for (LinkedList<Animal> animals : hashAnimals.values()) {
            for (Animal animal : animals) {
                a += animal.getEnergy();
                b++;
                kids += animal.kids;
            }
        }
        this.numberOfAnimals = b;
        if(b!=0){
            this.averageAnimalsEnergy = a / b;
            this.averageKidsNumber = kids / b;
        }
        else{
            this.averageAnimalsEnergy = 0;
            this.averageKidsNumber = 0;
        }

    }

    @Override
    public void place(Animal animal) {
        if(canMoveTo(animal.getPosition())){
            if (!this.hashAnimals.containsKey(animal.getPosition())) {
                this.hashAnimals.put(animal.getPosition(), new LinkedList<>());
            }
            this.hashAnimals.get(animal.getPosition()).add(animal);
        }
        else {
            System.out.println("Cant place animal at" + animal.getPosition());
        }
    }

    @Override
    public void removeAnimal(Animal animal){
        if(!hashAnimals.containsKey(animal.getPosition()))
            return;
        hashAnimals.get(animal.getPosition()).remove(animal);
        if(hashAnimals.get(animal.getPosition()).size() == 0)
            hashAnimals.remove(animal.getPosition());
    }

    @Override
    public boolean isOccupiedByAnimal(Vector2d position) {
        if(hashAnimals.containsKey(position)){
            return true;
        }
        return false;
    }

    @Override
    public boolean isOccupiedByGrass(Vector2d position) {
        if(hashGrass.containsKey(position)){
            return true;
        }
        return false;
    }

    @Override
    public IMapElement[] objectsAt(Vector2d position) {
        LinkedList<IMapElement> result = new LinkedList<>();
        if(!isOccupiedByGrass(position) && !isOccupiedByAnimal(position) )
            return null;
        if (hashAnimals.containsKey(position))
            result.addAll(hashAnimals.get(position));
        if(hashGrass.containsKey(position))
            result.add(hashGrass.get(position));

        return  result.toArray(new IMapElement[0]);
    }

    @Override
    public IMapElement grassAt(Vector2d position) {
        if (!hashGrass.containsKey(position))
            return null;
        return hashGrass.get(position);
    }


    public LinkedList<Animal[]> getAnimalsToBreed(int losingEnergy){
        LinkedList<Animal[]> result = new LinkedList<>();
        for(Vector2d pos: hashAnimals.keySet()){
            if(hashAnimals.get(pos).size() > 1){
                LinkedList<Animal> animals = hashAnimals.get(pos);
                Collections.sort(animals, (a, b) -> a.getEnergy() < b.getEnergy() ? -1 : a.getEnergy() == b.getEnergy() ? 0 : 1);
                if((animals.getLast().getEnergy()* 3)/4 > losingEnergy  && (animals.get(animals.size()-2).getEnergy()* 3)/4 > losingEnergy  ){
                    result.add(new Animal[]{animals.getLast(), animals.get(animals.size()-2)});
                }
            }
        }
        return result;
    }


}
