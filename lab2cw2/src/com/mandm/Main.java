package com.mandm;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Random rand = new Random();
        Scanner sc = new Scanner(System.in);
        int wymiar = 1;

        System.out.print("Podaj wielkość tablicy(a x a, liczba), a: ");
        try{
            wymiar = sc.nextInt();
        }
        catch (InputMismatchException e){
            System.out.println("Niepoprawana wartość!");
            Runtime.getRuntime().exit(1);
        }

	    int[][] matrix1 = new int[wymiar][wymiar];
	    int[][] matrix2 = new int[wymiar][wymiar];

	    for(int i = 0; i < wymiar; i++){
	        for(int j = 0; j < wymiar; j++){
	            matrix1[i][j] = rand.nextInt(21) - 10;
	            matrix2[i][j] = rand.nextInt(21) - 10;
            }
        }

        System.out.println("matrix1: ");
        System.out.println(Arrays.deepToString(matrix1).replace("],","\n"));

        System.out.println("matrix2: ");
        System.out.println(Arrays.deepToString(matrix2).replace("],","\n"));

        addMatrix(matrix1, matrix2, wymiar);
        subMatrix(matrix1, matrix2, wymiar);
        multMatrix(matrix1,matrix2,wymiar,rand.nextInt(21)-10);

    }

    public static void addMatrix(int[][] matrix1, int[][] matrix2, int wymiar){
            int[][] matrixAdd = new int[wymiar][wymiar];
            for(int i = 0; i < wymiar; i++){
                for (int j = 0; j < wymiar; j++){
                    matrixAdd[i][j] = matrix1[i][j] + matrix2[i][j];
                }
            }
        System.out.println("matrixAdd: ");
        System.out.println(Arrays.deepToString(matrixAdd).replace("],","\n"));
    }

    public static void subMatrix(int[][] matrix1, int[][] matrix2, int wymiar){
        int[][] matrixSub = new int[wymiar][wymiar];
        for(int i = 0; i < wymiar; i++){
            for (int j = 0; j < wymiar; j++){
                matrixSub[i][j] = matrix1[i][j] - matrix2[i][j];
            }
        }
        System.out.println("matrixSub: ");
        System.out.println(Arrays.deepToString(matrixSub).replace("],","\n"));
    }

    public static void multMatrix(int[][] matrix1, int[][] matrix2, int wymiar, int skalar){
        int[][] matrixMult = new int[wymiar][wymiar];

        for(int i = 0; i < wymiar; i++){
            for (int j = 0; j < wymiar; j++){
                matrixMult[i][j] = matrix1[i][j] * skalar;
            }
        }
        System.out.println("Skalar: " + skalar);
        System.out.println("matrixMult 1: ");
        System.out.println(Arrays.deepToString(matrixMult).replace("],","\n"));

        for(int i = 0; i < wymiar; i++){
            for (int j = 0; j < wymiar; j++){
                matrixMult[i][j] = matrix2[i][j] * skalar;
            }
        }
        System.out.println("matrixMult 2: ");
        System.out.println(Arrays.deepToString(matrixMult).replace("],","\n"));

    }
}
