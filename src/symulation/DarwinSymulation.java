package symulation;

import data.Vector2d;
import map.IWorldMap;
import map.MapDirection;
import objects.Animal;
import objects.Genotype;

import java.util.LinkedList;
import java.util.Random;

public class DarwinSymulation {

    private final int losingEnergy;
    private final int startingEnergy;
    private final IWorldMap map;
    private final int startAnimals;
    private Random generator;
    private int nDay = 0;
    private int lifeSum = 0;
    private double deadAnimals = 0;

    public double countAverageLifeDays(){
        if(deadAnimals == 0){
            return 0;
        }
        else{
            return lifeSum / deadAnimals;
        }
    }

    public DarwinSymulation(IWorldMap map, int losingEnergy, int startingEnergy, int startAnimals){
        this.losingEnergy=losingEnergy;
        this.startingEnergy=startingEnergy;
        this.map=map;
        this.startAnimals=startAnimals;
        this.generator =new Random();

        for(int i = 0; i < startAnimals; i++){
            int x = generator.nextInt(map.getMapSize().x);
            int y = generator.nextInt(map.getMapSize().y);
            Vector2d animalPos = map.positionMapped(new Vector2d(x, y));
            while(map.isOccupiedByAnimal(animalPos)){
                x += 1;
                if(x >= this.map.getMapSize().x){
                    x = 0;
                    y += 1;
                }
                if(y >= this.map.getMapSize().y){
                    y = 0;
                }
                animalPos = new Vector2d(x, y);
            }
            Animal animal = new Animal(map, new Vector2d(x,y));
            animal.setEnergy(startingEnergy);
        }
    }

    public void oneDay(){
        this.nDay++;
        //take energy from animals
        for(LinkedList<Animal> animals: map.getHashAnimals().values()){
            for(Animal animal: animals){
                animal.setEnergy(animal.getEnergy() - losingEnergy);
            }
        }
        //make animals die

        LinkedList<Animal> animalsToDie = new LinkedList<>();
        for(LinkedList<Animal> animals: map.getHashAnimals().values()) {
            for (Animal animal : animals) {
                if(animal.getEnergy() <=0) {
                    animalsToDie.add(animal);
                }
            }
        }

        for(Animal animal: animalsToDie){
            lifeSum += (nDay - animal.birthDay);
            deadAnimals += 1;
            map.removeAnimal(animal);
        }
        map.countAverageAnimalsEnergy();


        LinkedList<Animal> animalsToMove = new LinkedList<>();
        for(LinkedList<Animal> animals: map.getHashAnimals().values()) {
            for (Animal animal : animals) {
                animalsToMove.add(animal);
            }
        }
        for(Animal animal: animalsToMove){
            map.removeAnimal(animal);
            animal.move(animal.genotype.getNextMove());
            map.place(animal);
        }

        //animals eating
        map.makeAnimalsEat();

        //animals breeding
        LinkedList<Animal[]> pairsToBreed = map.getAnimalsToBreed(losingEnergy);
        for(Animal [] pair : pairsToBreed){
            Animal ani1 = pair[0] , ani2 = pair[1];
            MapDirection dir = MapDirection.NORTH;
            dir.rotation(generator.nextInt(8));
            Vector2d firstPossibleBirthPlace =  null;
            for(int i=0; i < 8; i++) {
                Vector2d check = ani1.getPosition().add(dir.toUnitVector());
                check = new Vector2d(check.x % map.getMapSize().x, check.y % map.getMapSize().y);
                if (!map.isOccupiedByAnimal(check)) {
                    firstPossibleBirthPlace = check;
                    break;
                }
                dir.rotation(1);
            }
            if(firstPossibleBirthPlace != null){
                Genotype genotype = ani1.genotype.getGenotypeForChild(ani2.genotype);
                Animal child = new Animal(map, firstPossibleBirthPlace, genotype);
                child.birthDay = this.nDay;
                ani1.setEnergy((ani1.getEnergy() * 3) / 4);
                ani1.kids++;
                ani2.setEnergy((ani2.getEnergy() * 3) / 4);
                ani2.kids++;
                child.setEnergy((ani1.getEnergy())/4 + (ani2.getEnergy())/4);
            }
        }

        //grass is growing
        map.makeGrassGrow();

    }


}
