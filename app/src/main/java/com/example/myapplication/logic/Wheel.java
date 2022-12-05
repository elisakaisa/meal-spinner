package com.example.myapplication.logic;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Wheel extends Thread {

    private final Random rand = new Random();

    public interface WheelListener {
        void newString(String s);
    }

    private final WheelListener wheelListener;
    public int currentIndex;
    private final long frameDuration;
    private boolean isStarted;
    private final List<String> list;
    private final List<Double> likelihoodList;
    private final ArrayList<Double> cumulativeLikelihood;

    public Wheel(WheelListener wheelListener, long frameDuration, List<String> list, List<Double> likelihoodList) {
        this.wheelListener = wheelListener;
        this.frameDuration = frameDuration;
        this.list = list;
        this.likelihoodList = likelihoodList;
        currentIndex = 0;
        isStarted = true;
        cumulativeLikelihood = prepareLikelihoodArray(likelihoodList);
    }

    private ArrayList<Double> prepareLikelihoodArray(List<Double> likelihoodList) {
        Log.i("Wheel", "Likelihoods " + likelihoodList);
        ArrayList<Double> normalizedLikelihood = normalize(likelihoodList);
        Log.i("Wheel", "Normalized Likelihoods " + normalizedLikelihood);
        ArrayList<Double> cumulativeLikelihood = (ArrayList<Double>) populateCumulativeLikelihood(normalizedLikelihood);
        Log.i("Wheel", "Cumulative likelihoods " + cumulativeLikelihood);
        return cumulativeLikelihood;
    }

    public void next(List<String> list) {

        double number = rand.nextDouble();
        //Log.i("Wheel", "random number " + number);
        int index = -1;
        for (int i = 0; i < list.size()-1; i++) {
            if (number <= cumulativeLikelihood.get(1)) index = 0;
            else if ((number > cumulativeLikelihood.get(i)) && (number <= cumulativeLikelihood.get(i+1))) {
                // number is contained between them
                index = i;
            }
            else if (number > cumulativeLikelihood.get(i+1)) index = i+1;
        }
        //Log.i("Wheel", "index " + index);
        currentIndex = index;
    }

    private List<Double> populateCumulativeLikelihood(List<Double> likelihoodList) {
        ArrayList<Double> cumulativeLikelihood = new ArrayList<>();
        double value = 0;
        cumulativeLikelihood.add(value);
        for (int i = 1; i < likelihoodList.size(); i++) {
            value = value + likelihoodList.get(i-1);
            cumulativeLikelihood.add(value);
        }
        return cumulativeLikelihood;
    }

    private ArrayList<Double> normalize(List<Double> list) {
        ArrayList<Double> normalizedList = new ArrayList<>();
        // normalization step
        double sum = likelihoodList.stream().mapToDouble(Double::doubleValue).sum();
        //TODO: figure out a way to have a stream instead
        //cumulativeLikelihood = cumulativeLikelihood.stream().mapToDouble(Double::doubleValue).forEachOrdered(a->a/sum);
        for (int i = 0; i < list.size(); i++) {
            normalizedList.add(list.get(i)/sum);
        }
        return normalizedList;
    }

    @Override
    public void run() {

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

