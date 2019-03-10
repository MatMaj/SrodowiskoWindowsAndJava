package com.mandm;

import java.util.*;


public class Main {

    public static void main(String[] args) {
        Scanner scam = new Scanner(System.in);
        List<Integer> l = new ArrayList();

        boolean dziala = true;
        komunikaty();
        while(dziala){
            int input = scam.nextInt();
            switch (input){
                case 1:
                    l.add(scam.nextInt());
                    //wpisanie liczby
                    break;
                case 2:
                    Set<Integer> lu = new HashSet<Integer>(l);
                    System.out.println(lu.toString());
                    //wypisanie uni
                    break;
                case 3:
                    System.out.println(l.toString());
                    //wypisanie all
                    break;
                case 4:
                    komunikaty();
                    //komuniakty
                    break;
                case 9:
                    dziala = false;
                    break;
            }
        }
    }

    public static void komunikaty(){
        System.out.println("1 - dodaj\n2 - unikalne\n3 - wszystkie\n4 - komunikaty\n9 - elo");
    }
}
