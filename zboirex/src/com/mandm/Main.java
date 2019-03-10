package com.mandm;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Set<String> A = new HashSet<String>();
        Set<String> B = new HashSet<String>();
        Scanner sc = new Scanner(System.in);
        String wpis;

        System.out.println("Wpisz zbiór A:");
        while(true){
            wpis = sc.next();
            if(wpis.equals("°")){
                break;
            }
            A.add(wpis);
        }
        System.out.println(A.toString());

        System.out.println("Wpisz zbiór B:");
        while(true){
            wpis = sc.next();
            if(wpis.equals("°")){
                break;
            }
            B.add(wpis);
        }
        System.out.println(B.toString());

        //suma(A,B);
        roznica(A,B);
        iloczyn(suma(A,B),rozsym(A,B));
        //rozsym(A,B);
    }

    public static Set<String> suma(Set<String> A, Set<String> B){
        Set<String> C = new HashSet<String>();
        C.addAll(A);
        C.addAll(B);

        System.out.println("Suma: " + C.toString());

        return C;


    }

    public static void roznica(Set<String> A, Set<String> B){
        Set<String> C = new HashSet<String>();
        C.addAll(A);
        C.removeAll(B);

        System.out.println("Roznica: " + C.toString());

    }
    public static void iloczyn(Set<String> A, Set<String> B){
        Set<String> C = new HashSet<String>();

        C.addAll(A);
        C.removeAll(B);

        System.out.println("Iloczyn: " + C.toString());

    }
    public static Set<String> rozsym(Set<String> A, Set<String> B){
        Set<String> C = new HashSet<String>();
        Set<String> D = new HashSet<String>();
        Set<String> E = new HashSet<String>();

        C.addAll(A);
        C.removeAll(B);

        D.addAll(B);
        D.removeAll(A);

        E.addAll(C);
        E.addAll(D);

        System.out.println("Roznica symetryczna: " + E.toString());
        return E;
    }
}
