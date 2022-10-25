package com.example.myapplication.logic;

import com.example.myapplication.model.Meal;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SpinnerLogic extends Thread {
    private final List<String> proteinList = Arrays.asList("Fish", "Minced meat");
    private final List<String> carbList = Arrays.asList("Pasta", "Rice", "Potatoes");
    private final List<String> greenList = Arrays.asList("Cucumber", "Paprika", "Green peas");

    public Meal returnRandomMeal() {
        Random rand = new Random();
        String protein = proteinList.get(rand.nextInt(proteinList.size()));
        String carb = carbList.get(rand.nextInt(carbList.size()));
        String green = greenList.get(rand.nextInt(greenList.size()));

        return new Meal(protein, carb, green);
    }

    public interface WheelListener {
        void newString(String s);
    }

    private WheelListener wheelListener;
    public int currentIndex;
    private long frameDuration;
    private long startIn;
    private boolean isStarted;
    private List<String> list;

    public SpinnerLogic(WheelListener wheelListener, long frameDuration, long startIn, List<String> list) {
        this.wheelListener = wheelListener;
        this.frameDuration = frameDuration;
        this.startIn = startIn;
        this.list = list;
        currentIndex = 0;
        isStarted = true;
    }

    public void next(List<String> list) {
        currentIndex++;

        if (currentIndex == list.size()) {
            currentIndex = 0;
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(startIn);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while(isStarted) {
            try {
                Thread.sleep(frameDuration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            next(list);

            if (wheelListener != null) {
                wheelListener.newString(list.get(currentIndex));
            }
        }
    }

    public void stopWheel() {
        isStarted = false;
    }
}

