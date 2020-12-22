package view;

import Setups.Setup;
import data.Vector2d;
import map.IWorldMap;
import map.TheMap;
import org.json.simple.JSONObject;
import symulation.DarwinSymulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;

public class MainWindow extends JFrame {
    SetupPanel setupPanel;
    SymulationPanel symulationPanel;
    MapFrame mapFrame;
    SymulationPanel secondSymulationPanel;
    MapFrame secondMapFrame;

    JButton applyButton;
    JButton getStatToFileButton;

    DarwinSymulation darwinSymulation = null;
    DarwinSymulation seconddarwinSymulation = null;
    IWorldMap map = null;
    IWorldMap secondMap = null;
    //default setup
    Setup setup = new Setup(1,4,4,50,4,1,5);

    private void statButton(ActionEvent actionEvent) {

        JSONObject statsMapOne = new JSONObject();
        statsMapOne.put("Number Of Animals", map.getNumberOfAnimals());
        statsMapOne.put("Number Of Grass", (map.getNumberOfGrassInJungle() + map.getNumberOfGrassOutOfJungle()));
        statsMapOne.put("Average Energy", (double) Math.round(map.getAverageAnimalsEnergy() * 100) / 100);
        statsMapOne.put("Average Kids", (double) Math.round(map.getAverageKidsNumber() * 100) / 100);
        statsMapOne.put("Average Life Span", (double) Math.round(darwinSymulation.countAverageLifeDays() * 100) / 100);
        statsMapOne.put("Dominant Genotype", map.getMostCommonGenotype());
        try (FileWriter file = new FileWriter("Map1.json")) {

            file.write(statsMapOne.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(this.setupPanel.secondMap.isSelected()){
            JSONObject statsMapTwo = new JSONObject();
            statsMapTwo.put("Number Of Animals", secondMap.getNumberOfAnimals());
            statsMapTwo.put("Number Of Grass", (secondMap.getNumberOfGrassInJungle() + secondMap.getNumberOfGrassOutOfJungle()));
            statsMapTwo.put("Average Energy", (double) Math.round(secondMap.getAverageAnimalsEnergy() * 100) / 100);
            statsMapTwo.put("Average Kids", (double) Math.round(secondMap.getAverageKidsNumber() * 100) / 100);
            statsMapTwo.put("Average Life Span", (double) Math.round(seconddarwinSymulation.countAverageLifeDays() * 100) / 100);
            statsMapTwo.put("Dominant Genotype", secondMap.getMostCommonGenotype());

            try (FileWriter file = new FileWriter("Map2.json")) {

                file.write(statsMapTwo.toJSONString());
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void applyButton(ActionEvent e){
        if(!SetupPanel.sAnimals.getText().equals("")){
            setup.startAnimals = Integer.parseInt(SetupPanel.sAnimals.getText());
        }
        if(!SetupPanel.sEnergy.getText().equals("")){
            setup.start_energy = Integer.parseInt(SetupPanel.sEnergy.getText());
        }
        if(!SetupPanel.sMapWidth.getText().equals("")){
            setup.map_width = Integer.parseInt(SetupPanel.sMapWidth.getText());
        }
        if(!SetupPanel.sMapHeight.getText().equals("")){
            setup.map_height = Integer.parseInt(SetupPanel.sMapHeight.getText());
        }
        if(!SetupPanel.eLose.getText().equals("")){
            setup.energy_lose = Integer.parseInt(SetupPanel.eLose.getText());
        }
        if(!SetupPanel.gEnergy.getText().equals("")){
            setup.grass_energy= Integer.parseInt(SetupPanel.gEnergy.getText());
        }
        if(!SetupPanel.jRatio.getText().equals("")){
            setup.jungleRatio= Integer.parseInt(SetupPanel.jRatio.getText());
        }

        symulationPanel.Stop();
        Vector2d jungleSize = new Vector2d(setup.jungleRatio * setup.map_width / 100, setup.jungleRatio * setup.map_width / 100);

        map = new TheMap(new Vector2d(setup.map_width, setup.map_height), jungleSize, setup.grass_energy);
        darwinSymulation = new DarwinSymulation(map,setup.energy_lose, setup.start_energy, setup.startAnimals);
        mapFrame.setMap(map);
        mapFrame.repaint();
        symulationPanel.setDarwinSymulation(darwinSymulation, mapFrame,map);
        map.countAverageAnimalsEnergy();
        symulationPanel.setStatistic();

        if(this.setupPanel.secondMap.isSelected()){
            secondMap = new TheMap(new Vector2d(setup.map_width, setup.map_height), jungleSize, setup.grass_energy);
            seconddarwinSymulation = new DarwinSymulation(secondMap,setup.energy_lose, setup.start_energy, setup.startAnimals);
            secondMapFrame.setMap(secondMap);
            secondMapFrame.repaint();
            secondSymulationPanel.setDarwinSymulation(seconddarwinSymulation, secondMapFrame,secondMap);
            secondMap.countAverageAnimalsEnergy();
            secondSymulationPanel.setStatistic();
            secondMapFrame.setVisible(true);
            secondSymulationPanel.setVisible(true);
        }
        else {
            secondMapFrame.setVisible(false);
            secondSymulationPanel.setVisible(false);
            secondMap = null;
            seconddarwinSymulation = null;
        }
        getStatToFileButton.setEnabled(true);
    }

    public MainWindow(){
        this.setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setupPanel = new SetupPanel();
        symulationPanel = new SymulationPanel();
        secondSymulationPanel = new SymulationPanel();
        mapFrame = new MapFrame(symulationPanel);
        secondMapFrame = new MapFrame(secondSymulationPanel);



        applyButton = new JButton("Apply!");
        getStatToFileButton = new JButton("GetAvgStat");

        applyButton.addActionListener(this::applyButton);
        getStatToFileButton.addActionListener(this::statButton);

        applyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        getStatToFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        getStatToFileButton.setEnabled(false);

        mapFrame.setSize(new Dimension(500, 500));

        setLayout(new BorderLayout());

        JPanel MenuPanel = new JPanel();
        JPanel MapsPanel = new JPanel();
        MapsPanel.setLayout(new BoxLayout(MapsPanel, BoxLayout.PAGE_AXIS));
        MenuPanel.setLayout(new BoxLayout(MenuPanel, BoxLayout.PAGE_AXIS));

        MenuPanel.add(setupPanel);
        MenuPanel.add(applyButton);
        MenuPanel.add(symulationPanel);
        MenuPanel.add(secondSymulationPanel);
        MenuPanel.add(getStatToFileButton);

        secondSymulationPanel.setVisible(false);
        secondMapFrame.setVisible(false);

        MapsPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        MapsPanel.add(mapFrame);
        MapsPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        MapsPanel.add(secondMapFrame);

        add(MenuPanel, BorderLayout.LINE_START);
        add(MapsPanel, BorderLayout.CENTER);
        pack();
    }

}
