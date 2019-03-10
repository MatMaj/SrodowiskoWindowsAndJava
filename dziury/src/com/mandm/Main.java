package com.mandm;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String binary;
        binary = sc.next();
        dziurexy(binary);
    }

    public static void dziurexy(String binary){
        int dziurondz = 0;

        char[] lc = new char[binary.length()];
        lc = binary.toCharArray();

        for(int i = 0; i < lc.length ; i++){
            if(lc[i] == '1'){
                boolean czyZero = false;
                for(int j = i+1; j < lc.length;j++){
                    if(lc[j] == '0') czyZero = true;
                    if(czyZero && lc[j] == '1'){
                        i = j - 1;
                        dziurondz++;
                        break;
                    }
                }
            }
        }
        System.out.println(dziurondz);

    }
}
