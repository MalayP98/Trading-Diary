package com.trading.diary.utils;

public class Period {

    private final long years;

    private final long months;

    private final long weeks;

    public Period(long years, long months, long weeks) {
        this.years = years;
        this.months = months;
        this.weeks = weeks;
    }

    public static Period getInstance(long year, long months){
        return new Period(year, months, 0);
    }

    public float years(){
        return years + (float) months /12;
    }

    public long months(){
        return 12*years + months;
    }

    private long weeks(){
        return 52*years + 4*months + weeks;
    }


}
