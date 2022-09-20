package com.example.sop_week5;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class WordPublisher {
    protected Word words = new Word();
    @Autowired
    private RabbitTemplate rabbitTemplate;
//    @RequestMapping(value = "/addBad/{word}", method = RequestMethod.GET)
    @RequestMapping(value = "/addBad/{word}", method = RequestMethod.POST)
    public ArrayList<String> addBadWord(@PathVariable("word") String s){
        System.out.println("addBad : " + s );
        words.badWords.add(s);
        return words.badWords;
    }

//    @RequestMapping(value = "/delBad/{word}", method = RequestMethod.GET)
    @RequestMapping(value = "/delBad/{word}", method = RequestMethod.POST)
    public ArrayList<String>  deleteBadWord(@PathVariable("word") String s){
        System.out.println("delBad : " + s );
        words.badWords.remove(s);
        return words.badWords;
    }

//    @RequestMapping(value = "/addGood/{word}", method = RequestMethod.GET)
    @RequestMapping(value = "/addGood/{word}", method = RequestMethod.POST)
    public ArrayList<String>  addGoodWord(@PathVariable("word") String s){
        System.out.println("addGood : " + s );
        words.goodWords.add(s);
        return words.goodWords;
    }

//    @RequestMapping(value = "/delGood/{word}", method = RequestMethod.GET)
    @RequestMapping(value = "/delGood/{word}", method = RequestMethod.POST)
    public ArrayList<String>  deleteGoodWord(@PathVariable("word") String s){
        System.out.println("delGood : " + s );
        words.goodWords.remove(s);
        return words.goodWords;
    }

//    @RequestMapping(value = "/proof/{sen}", method = RequestMethod.GET)
    @RequestMapping(value = "/proofSentence/{sen}", method = RequestMethod.POST)
    public String  proofSentence(@PathVariable("sen") String s){
        System.out.println("Publisher In : " + s );
        boolean haveBadword = false;
        boolean haveGoodword = false;
        for(int i = 0; i<words.badWords.size();i++){
            if(s.indexOf(words.badWords.get(i)) > -1){
                haveBadword = true;
                break;
            }
        }
        for(int i = 0; i<words.goodWords.size();i++){
            if(s.indexOf(words.goodWords.get(i)) > -1){
                haveGoodword = true;
                break;
            }
        }
        System.out.println(haveBadword);
        System.out.println(haveGoodword);
        if(haveGoodword && haveBadword){
            rabbitTemplate.convertAndSend("Fanout","",s);
            return "Found Bad Word & Found Good Word";
        } else if (haveGoodword) {
            rabbitTemplate.convertAndSend("Direct","good",s );
            return "Found Good Word";
        } else if (haveBadword) {
            rabbitTemplate.convertAndSend("Direct","bad",s );
            return "Found Bad Word";
        }
        return "No word";
    }
    @RequestMapping(value = "/getSentence", method = RequestMethod.GET)
    public Sentence  getSentence(){
        Object result = rabbitTemplate.convertSendAndReceive("Direct", "", "");
        System.out.println("result sentence" + ((Sentence) result));
        return (Sentence) result;
    }
}
