package view;

import map.IWorldMap;
import symulation.DarwinSymulation;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;


public class SymulationPanel extends JPanel {
    private static int minDelay = 0;
    private static int maxDelay = 3000;
    private Timer timer;
    private final JSlider delay;
    private final JButton startButton;
    private final JButton stopButton;
    private final JButton oneDayButton;
    private IWorldMap map = null;
    private DarwinSymulation darwinSymulation;
    private MapFrame mapFrame;
    public boolean isStopped = true;

    JLabel numberOfAnimals;
    JLabel numberOfGrass;
    JLabel averageLifeTime;
    JLabel averageKids;
    JLabel averageEnergy;
    JLabel mostCommonGenotype;


    public void Stop(){
        if(timer != null){
            timer.stop();
            timer = null;
        }
        stopButton.setEnabled(false);
    }

    private void oneDay(){
        darwinSymulation.oneDay();
        mapFrame.repaint();
        setStatistic();
    }

    public void setStatistic() {
        numberOfAnimals.setText("Number Of Animals: " + map.getNumberOfAnimals());
        numberOfGrass.setText("Number Of Grass: " + (map.getNumberOfGrassInJungle() + map.getNumberOfGrassOutOfJungle()) );
        averageEnergy.setText("avg_energy: " + (double) Math.round(map.getAverageAnimalsEnergy() * 100) / 100);
        averageKids.setText("avg_children: " + (double) Math.round(map.getAverageKidsNumber() * 100) / 100);
        averageLifeTime.setText("avg_lifespan: " + (double) Math.round(darwinSymulation.countAverageLifeDays() * 100) / 100);
        mostCommonGenotype.setText("dominant_genotype: " + map.getMostCommonGenotype());
    }

    private void oneDayButton(ActionEvent e){
        oneDay();
    }

    private void startButton(ActionEvent e){
        this.isStopped = false;
        timer = new Timer((delay.getValue()), this::oneDayButton);
        timer.start();
        oneDayButton.setEnabled(false);
        stopButton.setEnabled(true);
        startButton.setEnabled(false);
    }

    private void stopButton(ActionEvent e){
        Stop();
        this.isStopped = true;
        oneDayButton.setEnabled(true);
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
    }

    private void speedChanged(ChangeEvent e){
        if(timer != null && timer.isRunning()){
            this.timer.setDelay(delay.getValue());
        }
    }

    void setDarwinSymulation(DarwinSymulation darwin, MapFrame map,IWorldMap mapa){
        this.map = mapa;
        this.darwinSymulation = darwin;
        if (map != null) {
            this.mapFrame = map;
            oneDayButton.setEnabled(true);
            startButton.setEnabled(true);
        }
    }

    SymulationPanel(){

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        numberOfAnimals = new JLabel("Number of Animals");
        numberOfAnimals.setFont(new Font("Times New Roman", Font.PLAIN,15));
        numberOfAnimals.setAlignmentX(CENTER_ALIGNMENT);
        numberOfGrass = new JLabel("Number of Grass");
        numberOfGrass.setFont(new Font("Times New Roman", Font.PLAIN,15));
        numberOfGrass.setAlignmentX(CENTER_ALIGNMENT);
        averageEnergy = new JLabel("Average Energy");
        averageEnergy.setFont(new Font("Times New Roman", Font.PLAIN,15));
        averageEnergy.setAlignmentX(CENTER_ALIGNMENT);
        averageKids = new JLabel("Average Kids");
        averageKids.setFont(new Font("Times New Roman", Font.PLAIN,15));
        averageKids.setAlignmentX(CENTER_ALIGNMENT);
        averageLifeTime = new JLabel("Average LifeTime");
        averageLifeTime.setFont(new Font("Times New Roman", Font.PLAIN,15));
        averageLifeTime.setAlignmentX(CENTER_ALIGNMENT);
        mostCommonGenotype = new JLabel("Most Common Genotype");
        mostCommonGenotype.setFont(new Font("Times New Roman", Font.PLAIN,15));
        mostCommonGenotype.setAlignmentX(CENTER_ALIGNMENT);


        delay = new JSlider(JSlider.HORIZONTAL, minDelay, maxDelay, 400);
        delay.setFont(new Font("Times New Roman", Font.PLAIN,15));
        delay.setMajorTickSpacing(500);
        delay.setMinorTickSpacing(250);
        delay.setPaintTicks(true);
        delay.setPaintLabels(true);
        delay.addChangeListener(this::speedChanged);


        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        oneDayButton = new JButton("OneDay");

        oneDayButton.addActionListener(this::oneDayButton);
        startButton.addActionListener(this::startButton);
        stopButton.addActionListener(this::stopButton);

        stopButton.setEnabled(false);
        oneDayButton.setEnabled(false);
        startButton.setEnabled(false);

        add(delay);

        JPanel buttons = new JPanel();
        new BoxLayout(buttons, BoxLayout.LINE_AXIS);
        buttons.add(startButton);
        buttons.add(Box.createRigidArea(new Dimension(25,0)));
        buttons.add(stopButton);
        buttons.add(Box.createRigidArea(new Dimension(25,0)));
        buttons.add(oneDayButton);

        add(buttons);
        add(numberOfAnimals);
        add(numberOfGrass);
        add(averageEnergy);
        add(averageKids);
        add(averageLifeTime);
        add(mostCommonGenotype);
    }

}
