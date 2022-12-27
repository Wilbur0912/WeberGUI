package com.example.myapplication.ui.dashboard;

public class ResultMemento {
    private boolean result;
    private int  amount;
    private float percentage;

    public ResultMemento(boolean result,int amount,float percentage ) {
        this.result = result;
        this.amount = amount;
        this.percentage = percentage;
    }

    public boolean getSavedResult() {
        return result;
    }
    public int getSavedAmount(){return  amount;}
    public float getPercentage(){return  percentage;}
}
