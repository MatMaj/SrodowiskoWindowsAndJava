package com.mandm;

import java.lang.reflect.Array;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        int lengthTab = 1;
        Random rand = new Random();

        Scanner sc = new Scanner(System.in);
        System.out.print("Podaj wielkość tablicy(liczba): ");
        try{
            lengthTab = sc.nextInt();
        }
        catch (InputMismatchException e){
            System.out.println("Niepoprawna wartość!");
            Runtime.getRuntime().exit(1);
        }

	    ;

        double[] doublesList = {2,3,3.5,4,4.5,5};


        ArrayList<Double> doubles2 = new ArrayList<Double>(lengthTab);

        for(int i = 0; i < lengthTab; i++){
            doubles2.add(doublesList[rand.nextInt(6)]);
        }
        double avg = avg(doubles2);
        difThenAvg(avg, doubles2);
        min(doubles2);
        max(doubles2);
        stdDev(avg, doubles2);
    }

    public static double avg(ArrayList<Double> doubles){
        double sum = doubles.stream().mapToDouble(Double::doubleValue).sum();
        double avg = sum/Array.getLength(doubles.toArray());
        System.out.println("Srednia: " + avg);
        return avg;
    }

    public static void min(ArrayList<Double> doubles){
        System.out.println("Min: " + doubles.stream().mapToDouble(Double::doubleValue).min().getAsDouble());
    }

    public static void max(ArrayList<Double> doubles){
        System.out.println("Max: " + doubles.stream().mapToDouble(Double::doubleValue).max().getAsDouble());
    }

    public static void difThenAvg(double avg, ArrayList<Double> doubles){
        // Znajdź wartości wyższe / niższe niż średnia
        System.out.println("Wartości inne niż średnia: ");
        doubles.forEach((a) -> {if(a != avg) System.out.println(a);});

    }

    public static void stdDev(double avg,ArrayList<Double> doubles){
        double sum = 0;
        for(double i : doubles){
            sum += Math.pow(i - avg,2);
        }
        System.out.println("Odchylenie standardowe: " + Math.sqrt(sum/Array.getLength(doubles.toArray())));
    }
    
}
