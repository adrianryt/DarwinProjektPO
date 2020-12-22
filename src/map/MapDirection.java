package map;

import data.Vector2d;

public enum MapDirection {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    private static final String [] names = {"Północ", "Północny wschód", "Wschód", "Południowy wschód", "Południe",
            "Południowy zachód", "Zachód", "Północny zachód"};

    private static final Vector2d[] vectors = {new Vector2d(0, 1), new Vector2d(1, 1),
            new Vector2d(1, 0),new Vector2d(1, -1), new Vector2d(0, -1),
            new Vector2d(-1, -1), new Vector2d(-1, 0), new Vector2d(-1, 1)};

    @Override
    public String toString() {
        return names[this.ordinal()];
    }

    public Vector2d toUnitVector(){
        return vectors[this.ordinal()];
    }

    public MapDirection rotation(int n){
        return MapDirection.values()[((this).ordinal() + n) % (MapDirection.values().length)];
    }

}
