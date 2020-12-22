package view;

import data.Vector2d;
import map.IWorldMap;
import objects.Animal;
import objects.IMapElement;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

public class MapFrame extends JPanel implements MouseListener {
    IWorldMap map = null;
    SymulationPanel symulationPanel;
    int TileWidth;

    Color jungleColor = new Color(0, 147, 3);
    Color stepColor = new Color(182, 239, 3);


    public void setMap(IWorldMap map) {
        this.map = map;
    }

    public MapFrame(SymulationPanel sym) {
        this.symulationPanel = sym;
        this.addMouseListener(this);
        super.repaint();
        setPreferredSize(new Dimension(450, 450));
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        if (map == null)
            return;

        Vector2d lowerLeft = new Vector2d(0, 0);
        Vector2d upperRight = new Vector2d(map.getMapSize().x - 1, map.getMapSize().y - 1);

        int width = Math.abs(lowerLeft.x - upperRight.x) + 1;
        double tileWidth = this.getSize().width / width;
        int roundTileWidth = (int) tileWidth;
        this.TileWidth = roundTileWidth;
        if (roundTileWidth == 0) return;
        for (int x = lowerLeft.x; x <= upperRight.x; x++) {
            for (int y = lowerLeft.y; y <= upperRight.y; y++) {

                double xTile = (x - lowerLeft.x) * tileWidth;
                double yTile = 15 + (y - lowerLeft.y) * tileWidth;
                int xRoundTile = (int) xTile;
                int yRoundTile = (int) yTile;
                g.setColor(map.isInJungle(new Vector2d(x,y)) ? jungleColor : stepColor);
                g.fillRect(xRoundTile, yRoundTile, roundTileWidth, roundTileWidth);
                g.setColor(Color.BLACK);
                g.drawRect(xRoundTile, yRoundTile, roundTileWidth, roundTileWidth);
                g.setColor(Color.GREEN);


                if (map.isOccupiedByAnimal(new Vector2d(x, y)) || map.isOccupiedByGrass(new Vector2d(x, y))) {

                    IMapElement element = map.objectsAt(new Vector2d(x, y))[0];


                    if (element instanceof Animal) {
                        if(((Animal) element).getEnergy() < 10){
                            ImageIcon ii = new ImageIcon("Animal3.png");
                            g.drawImage(ii.getImage(), xRoundTile + (roundTileWidth) / 6, yRoundTile + (roundTileWidth) / 6, (int) (roundTileWidth * 0.8), (int) (roundTileWidth * 0.8), this);
                        }
                        if(((Animal) element).getEnergy() >= 10 && ((Animal) element).getEnergy() < 20){
                            ImageIcon ii = new ImageIcon("Animal1.png");
                            g.drawImage(ii.getImage(), xRoundTile + (roundTileWidth) / 6, yRoundTile + (roundTileWidth) / 6, (int) (roundTileWidth * 0.8), (int) (roundTileWidth * 0.8), this);
                        }
                        if(((Animal) element).getEnergy() >= 20 ){
                            ImageIcon ii = new ImageIcon("Animal2.png");
                            g.drawImage(ii.getImage(), xRoundTile + (roundTileWidth) / 6, yRoundTile + (roundTileWidth) / 6, (int) (roundTileWidth * 0.8), (int) (roundTileWidth * 0.8), this);
                        }

                    } else {
                        ImageIcon jj = new ImageIcon("Grass.png");
                        g.drawImage(jj.getImage(), xRoundTile, yRoundTile, roundTileWidth, roundTileWidth, this);
                    }
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if(this.symulationPanel.isStopped){
            int x = e.getX();
            int y = e.getY();
            x = x/this.TileWidth;
            y = (y-15)/this.TileWidth;
            LinkedList<Animal> animals = map.getHashAnimals().get(new Vector2d(x,y));
            if(animals != null){
                Animal animal = animals.getFirst();
                JOptionPane.showMessageDialog(null,"This animals is on position" + new Vector2d(x,y) +
                        "\nAnimal enerygy: " + animal.getEnergy() +
                        "\nAnimal genotype: " + changeToString(animal.genotype.getGeneArr()) +
                        "\nThis animal has " + animal.kids+" children");
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //null
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //null
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //null
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //null
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
}
