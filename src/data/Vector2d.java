package data;

public class Vector2d {
    final public int x;
    final public int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public String toString(){
        return ("(" + this.x + "," + this.y + ")");
    }

    public boolean equals(Object other){
        if (this == other) { //sprawdzenie czy porównywane obiekty to dokładnie te same obiekty
            return true;
        }
        if (!(other instanceof Vector2d)) { //sprawdzenie czy other jest obiektem Vector2d
            return false;
        }
        Vector2d that = (Vector2d) other;
        if(this.x == that.x && this.y == that.y) // sprawdzenie czy x,y wektorów są sobie równe
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash += this.x * 31;
        hash += this.y * 17;
        return hash;
    }

    public Vector2d add(Vector2d other){
        Vector2d result = new Vector2d(this.x + other.x , this.y + other.y);
        return result;
    }

}
