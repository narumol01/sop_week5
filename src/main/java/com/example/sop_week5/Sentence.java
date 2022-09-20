package com.example.sop_week5;

import java.io.Serializable;
import java.util.ArrayList;

public class Sentence implements Serializable {
    public ArrayList<String> badSentences;
    public ArrayList<String> goodSentences;
    public Sentence(){
        badSentences = new ArrayList<String>();
        goodSentences = new ArrayList<String>();
    }
}
