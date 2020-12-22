package Setups;


public class Setup {
    public int startAnimals;
    public int map_width;
    public int map_height;
    public int jungleRatio;
    public int grass_energy;
    public int energy_lose;
    public int start_energy;

    public Setup(int startAnimals, int mapWidth, int mapHeight, int jungleRatio, int grass_energy, int energy_lose, int start_energy) {
        this.startAnimals = startAnimals;
        this.map_width = mapWidth;
        this.map_height = mapHeight;
        this.jungleRatio = jungleRatio;
        this.grass_energy = grass_energy;
        this.energy_lose = energy_lose;
        this.start_energy = start_energy;
    }
}
