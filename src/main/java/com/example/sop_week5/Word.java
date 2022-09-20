package com.example.sop_week5;

import java.io.Serializable;
import java.util.ArrayList;

public class Word implements Serializable {
    public ArrayList<String> badWords;
    public ArrayList<String> goodWords;
    public Word(){
        badWords = new ArrayList<String>();
        goodWords = new ArrayList<String>();
        badWords.add("fuck");
        badWords.add("olo");
        goodWords.add("happy");
        goodWords.add("enjoy");
        goodWords.add("life");
    }
}
