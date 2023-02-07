package com.revature.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;

@Service
@Transactional
public class ProfanityService {
    private HashMap<String,Boolean> profanity;
    private ArrayList<String> profanityList;
    private File profanityConfig;
    public ProfanityService()
    {
        try{
            profanityConfig = new File("src/main/resources/profanityList.txt");
            BufferedReader reader = new BufferedReader(new FileReader(profanityConfig));
            profanity = new HashMap<String,Boolean>();
            profanityList = new ArrayList<>();
            String current = "";
            while (true)
            {
                current = reader.readLine();
                if (current==null)
                    break;
                profanity.put(current,true);
            }
            profanity.forEach((word,value)->
                profanityList.add(word)
            );//value is always true.... so we're using this to map the hashmap to an arrayList for searching for 'lookalikes'.
            reader.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String filterProfanity(String text) {
        String[] words = text.split(" ");
        StringBuilder returnedString= new StringBuilder();
        for (int i = 0; i<words.length; i++)
        {
           if(profanity.get(words[i].toLowerCase())!=null||profanityLikely(words[i]))
               words[i] = asterisks(words[i].length());
           returnedString.append(words[i]);
           if(i+1!=words.length)
               returnedString.append(" ");

        }
        return returnedString.toString();
    }

    public boolean profanityLikely(String s)
    {
        String temp = Normalizer.normalize(s.strip().toLowerCase(),Normalizer.Form.NFD);//strip white space, standardize spelling.
        temp = temp.replaceAll("\\p{M}}", ""); //removes any remaining "funny" characters.
        if(profanity.get(temp)!=null)
            return true;
        temp = temp.replaceAll("[0-9]","");//remove numbers.
        if(temp.length()==0)
            return false;
        if(profanity.get(temp)!=null)//if the phrase without numbers is a match.
            return true;
        if(profanity.get(temp.toLowerCase())!=null) //pretty extensively checked by this point, but the final check sees if there's multiple 'contains'. will likely result in overcropping.
            return true;

        int count = 0;
        for(int i = 0; i<profanityList.size();i++)
        {
            if (temp.contains(profanityList.get(i)))
                count++;
        }
        return count>2;
    }

    private String asterisks(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append("*");
        }
        return sb.toString();
    }
}
