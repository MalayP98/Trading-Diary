package com.trading.diary.menu;

import com.trading.diary.utils.Helper;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;
import java.util.function.Function;

public abstract class AbstractMenu<T> {

    protected final Scanner SCANNER = new Scanner(System.in);

    private final static int retires = 3;

    public abstract T showMenu();

    protected String nextString(){
        return SCANNER.nextLine();
    }

    protected String nextLine(){
        String input = nextString();
        return sanitize(input);
    }

    private String sanitize(String input){
        if(StringUtils.isEmpty(input)){
            print("Invalid input please try again!");
            return nextLine();
        }
        return input;
    }

    protected int nextInt(){
        return Integer.parseInt(nextLine());
    }

    protected long nextLong(){
        return Long.parseLong(nextLine());
    }

    protected float nextFloat(){
        return Float.parseFloat(nextLine());
    }

    protected String nextSanitzedString(){
        return nextLine();
    }

    protected boolean nextBoolean(){
        return Helper.booleanInputConverter(nextSanitzedString());
    }

    protected void print(String output){
        System.out.println(output);
    }
}
