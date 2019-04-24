package com.abhisinha.purduetrivia.game.init;

import com.abhisinha.purduetrivia.ignite.GameData;
import org.h2.mvstore.DataUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: class that stores initial questions to be loaded into the cache on startup.
public final class QuestionLoader {

    private static Map<String, List<String>> questions;

    static {
        questions = new HashMap<String, List<String>>() {{
            put("As of 2019, how old is Purdue?", Arrays.asList("150 Years", "100 Years", "50 Years", "200 Years"));
            put("What are Purdue's official colors?", Arrays.asList("Old Gold & Black", "Crimson & Cream", "Blue & Orange", "White & Old Gold"));
            put("In which state is Purdue located?", Arrays.asList("Indiana", "Illinois", "Georgia", "Ohio"));
            put("In what year was Purdue founded?", Arrays.asList("1869", "1868", "1870", "1871"));
            put("Which of the following is Purdue's oldest building?", Arrays.asList("University Hall", "Stanley Coulter", "Stewart Center", "Purdue Memorial Union"));
            put("Who was Purdue's first president?", Arrays.asList("Richard Owen", "Francis Cordova", "Martin Jischke", "Mitch Daniels"));
            put("Which of these words is not in Purdue's motto?", Arrays.asList("Excellence", "Education", "Research", "Service"));
            put("In what year was 'The Exponent' first published?", Arrays.asList("1889", "1917", "1969", "2002"));
            put("How long is The Purdue Grand Prix track?", Arrays.asList("50 miles", "45 miles", "55 miles", "60 miles"));
            put("When did John Purdue die?", Arrays.asList("1876", "1871", "1880", "1873"));
            put("How many Purdue Graduates became NASA astronauts?", Arrays.asList("23", "21", "25", "27"));
            put("How many residence halls are there on campus?", Arrays.asList("15", "12", "17", "10"));
            put("When did the Purdue Memorial Union open?", Arrays.asList("1924", "1917", "1920", "1914"));
            put("How many colleges and schools does Purdue University consist of?", Arrays.asList("10", "9", "8", "11"));
            put("How many students were in Purdue's first class?", Arrays.asList("39", "53", "17", "92"));
            put("How much was the gift John Purdue gave to found Purdue?", Arrays.asList("$150,000", "$100,000", "$200,000", "$250,000"));
            put("How many times does the word 'Hail' appears in the song 'Hail Purdue!'?", Arrays.asList("8", "7", "9", "10"));
            put("The memoriam in the Purdue Memorial Union is dedicated to students who:", Arrays.asList("fought in WWI", "fought in WWII", "fought in Vietnam", "served in the military"));
            put("How many Purdue regional campuses are located throughout the state of Indiana?", Arrays.asList("4", "3", "5", "6"));
            put("What is the capacity of Ross-Ade Stadium?", Arrays.asList("57,236", "56,236", "56,623", "57,623"));
            put("What is the capacity of Mackey Arena?", Arrays.asList("14,240", "13,240", "13,420", "14,420"));
            put("What is the capacity of Elliott Hall of Music?", Arrays.asList("6,000", "4,000", "5,000", "7,000"));
            put("How many Starbucks locations are there on campus?", Arrays.asList("3", "5", "2", "4"));
            put("How many dining courts are there on campus?", Arrays.asList("8", "5", "6", "7"));
            put("Which of the following score lines was the largest victory that awarded Purdue the Old Oaken Bucket?", Arrays.asList("68-0", "60-0", "60-7", "68-7"));
            put("During which period did the Purdue Boilermakers build their 10 game win-streak against the Indiana Hoosiers?", Arrays.asList("1948-1957", "1948-1961", "1952-1961", "1950-1963"));
        }};
    }

    public static void loadQuestions() {
        for (Map.Entry<String, List<String>> q : questions.entrySet()) {
            GameData.addQuestion(q.getKey(), q.getValue().toArray(new String[0]));
        }
    }
}
