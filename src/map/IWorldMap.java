package map;

import data.Vector2d;
import objects.Animal;
import objects.Grass;
import objects.IMapElement;

import java.util.LinkedList;
import java.util.Map;

public interface IWorldMap {
    /**
     * Indicate if any object can move to the given position.
     *
     * @param position
     *            The position checked for the movement possibility.
     * @return True if the object can move to that position.
     */
    boolean canMoveTo(Vector2d position);

    Vector2d getMapSize();
    Map<Vector2d, LinkedList<Animal>> getHashAnimals();
    Map<Vector2d, Grass> getHashGrass();

    Vector2d positionMapped(Vector2d position);

    /**
     * Place a animal on the map.
     *
     *
     *            The animal to place on the map.
     * @return True if the animal was placed. The animal cannot be placed if the map is already occupied.
     */
    void place(Animal animal);

    void removeAnimal(Animal animal);

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position
     *            Position to check.
     * @return True if the position is occupied.
     */

    boolean isOccupiedByAnimal(Vector2d position);

    boolean isOccupiedByGrass(Vector2d position);

    IMapElement[] objectsAt(Vector2d position);

    IMapElement grassAt(Vector2d position);

    void makeAnimalsEat();

    void makeGrassGrow();
    LinkedList<Animal[]> getAnimalsToBreed(int losingEnergy);
    void countAverageAnimalsEnergy();

    double getAverageAnimalsEnergy();
    double getNumberOfAnimals();
    double getAverageKidsNumber();
    String getMostCommonGenotype();

    int getNumberOfGrassInJungle();
    int getNumberOfGrassOutOfJungle();
    boolean isInJungle(Vector2d position);


}
