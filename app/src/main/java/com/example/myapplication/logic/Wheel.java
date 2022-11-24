package com.example.myapplication.logic;

import android.util.Log;

import com.example.myapplication.model.Meal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Wheel extends Thread {
    /*private final List<String> proteinList = Arrays.asList("Fish", "Minced meat");
    private final List<String> carbList = Arrays.asList("Pasta", "Rice", "Potatoes");
    private final List<String> greenList = Arrays.asList("Cucumber", "Paprika", "Green peas");

    public Meal returnRandomMeal() {
        Random rand = new Random();
        String protein = proteinList.get(rand.nextInt(proteinList.size()));
        String carb = carbList.get(rand.nextInt(carbList.size()));
        String green = greenList.get(rand.nextInt(greenList.size()));

        return new Meal(protein, carb, green);
    } */

    private Random rand = new Random();

    public interface WheelListener {
        void newString(String s);
    }

    private WheelListener wheelListener;
    public int currentIndex;
    private long frameDuration;
    private long startIn;
    private boolean isStarted;
    private List<String> list;
    private List<Double> likelihoodList;

    public Wheel(WheelListener wheelListener, long frameDuration, List<String> list, List<Double> likelihoodList) {
        this.wheelListener = wheelListener;
        this.frameDuration = frameDuration;
        this.list = list;
        this.likelihoodList = likelihoodList;
        currentIndex = 0;
        isStarted = true;
    }

    public void next(List<String> list, List<Double> likelihoodList) {
        ArrayList<Double> cumulativeLikelihood = (ArrayList<Double>) populateCumulativeLikelihood(likelihoodList);

        double number = rand.nextDouble();
        Log.i("Wheel", "random number " + number);
        int index = -1;
        for (int i = 0; i < list.size()-1; i++) {
            if (number <= cumulativeLikelihood.get(1)) index = 0;
            else if ((number > cumulativeLikelihood.get(i)) && (number <= cumulativeLikelihood.get(i+1))) {
                // number is contained between them
                index = i;
            }
            else if (number > cumulativeLikelihood.get(i+1)) index = i+1;
        }
        Log.i("Wheel", "index " + index);
        currentIndex = index;
    }

    private List<Double> populateCumulativeLikelihood(List<Double> likelihoodList) {
        ArrayList<Double> cumulativeLikelihood = new ArrayList<>();
        double value = 0;
        cumulativeLikelihood.add(value);
        for (int i = 1; i < likelihoodList.size(); i++) {
            value = value + likelihoodList.get(i);
            cumulativeLikelihood.add(value);
        }
        return cumulativeLikelihood;
    }

    @Override
    public void run() {

        while(isStarted) {
            try {
                Thread.sleep(frameDuration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            next(list, likelihoodList);

            if (wheelListener != null) {
                wheelListener.newString(list.get(currentIndex));
            }
        }
    }

    public void stopWheel() {
        isStarted = false;
    }
}

