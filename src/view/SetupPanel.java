package view;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class SetupPanel extends JPanel {

    public static JTextField sAnimals;
    public static JTextField sEnergy;
    public static JTextField sMapWidth;
    public static JTextField sMapHeight;
    public static JTextField eLose;
    public static JTextField gEnergy;
    public static JTextField jRatio;

    public final JCheckBox secondMap;



    public SetupPanel(){
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;

        try{
            Reader reader = new FileReader("parametry.json");
            jsonObject = (JSONObject) parser.parse(reader);
        } catch (IOException | ParseException e){
            e.printStackTrace();
        }

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        secondMap = new JCheckBox("Second Map");
        secondMap.setSelected((Boolean) jsonObject.get("secondMap"));

        JLabel startAnimals =  new JLabel("Start Animals");
        startAnimals.setFont(new Font("Times New Roman", Font.PLAIN,15));
        startAnimals.setAlignmentX(CENTER_ALIGNMENT);
        this.add(startAnimals);

        sAnimals = new JTextField(jsonObject.get("startAnimals").toString());
        sAnimals.setFont(new Font("Tahoma", Font.PLAIN,12));
        this.add(sAnimals);

        JLabel startEnergy =  new JLabel("Start Energy");
        startEnergy.setFont(new Font("Times New Roman", Font.PLAIN,15));
        startEnergy.setAlignmentX(CENTER_ALIGNMENT);
        this.add(startEnergy);

        sEnergy = new JTextField(jsonObject.get("startEnergy").toString());
        sEnergy.setFont(new Font("Tahoma", Font.PLAIN,12));
        this.add(sEnergy);

        JLabel mapWidth =  new JLabel("Map Width");
        mapWidth.setFont(new Font("Times New Roman", Font.PLAIN,15));
        mapWidth.setAlignmentX(CENTER_ALIGNMENT);
        this.add(mapWidth);

        sMapWidth = new JTextField(jsonObject.get("mapWidth").toString());
        sMapWidth.setFont(new Font("Tahoma", Font.PLAIN,12));
        this.add(sMapWidth);

        JLabel mapHight =  new JLabel("Map Hight");
        mapHight.setFont(new Font("Times New Roman", Font.PLAIN,15));
        mapHight.setAlignmentX(CENTER_ALIGNMENT);
        this.add(mapHight);

        sMapHeight = new JTextField(jsonObject.get("mapHight").toString());
        sMapHeight.setFont(new Font("Tahoma", Font.PLAIN,12));
        this.add(sMapHeight);

        JLabel energyLose =  new JLabel("Energy Lose");
        energyLose.setFont(new Font("Times New Roman", Font.PLAIN,15));
        energyLose.setAlignmentX(CENTER_ALIGNMENT);
        this.add(energyLose);

        eLose = new JTextField(jsonObject.get("energyLose").toString());
        eLose.setFont(new Font("Tahoma", Font.PLAIN,12));
        this.add(eLose);

        JLabel grassEnergy =  new JLabel("Grass Energy");
        grassEnergy.setFont(new Font("Times New Roman", Font.PLAIN,15));
        grassEnergy.setAlignmentX(CENTER_ALIGNMENT);
        this.add(grassEnergy);

        gEnergy = new JTextField(jsonObject.get("grassEnergy").toString());
        gEnergy.setFont(new Font("Tahoma", Font.PLAIN,12));
        this.add(gEnergy);

        JLabel jungleRatio =  new JLabel("Jungle Ratio");
        jungleRatio.setFont(new Font("Times New Roman", Font.PLAIN,15));
        jungleRatio.setAlignmentX(CENTER_ALIGNMENT);
        this.add(jungleRatio);

        jRatio = new JTextField(jsonObject.get("jungleRatio").toString());
        jRatio.setFont(new Font("Tahoma", Font.PLAIN,12));
        this.add(jRatio);

        this.add(secondMap);
    }

}
