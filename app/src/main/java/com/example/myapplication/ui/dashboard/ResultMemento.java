package com.example.myapplication.ui.dashboard;

public class ResultMemento {
    private boolean result;
    private int  amount;

    public ResultMemento(boolean result,int amount ) {
        this.result = result;
        this.amount = amount;
    }

    public boolean getSavedResult() {
        return result;
    }
    public int getSavedAmount(){return  amount;}
}
