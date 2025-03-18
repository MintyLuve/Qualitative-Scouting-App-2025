package com.fro.qualitativescoutingapp2025;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Values {
    // Current year
    public static final int year = 2025;

    // public variables for the developer to set which box they are using
    public static int left = 0;
    public static int middle = 1;
    public static int right = 2;
    public static int vertical_level_1 = 3;
    public static int vertical_level_2 = 4;
    public static int vertical_level_3 = 5;
    public static int vertical_level_4 = 6;
    public static int vertical_level_5 = 7;
    public static int inputType_text = 8;
    public static int inputType_number = 9;

    // Data
    public static LinkedHashMap<String, Object> data = new LinkedHashMap<>();

    public static String getData(){
        String map ="[";
        for(String key: data.keySet()){
            map = (map + data.get(key) + ", ");
        }
        map += "]";
        return map;
    }

    public static void sortData() {
        // Creates a list of just the keys (names), and sorts them
        ArrayList<String> sortedKeys = new ArrayList<>(data.keySet());
        Collections.sort(sortedKeys);

        // Goes through sorted keys and transfers data into new hashmap
        LinkedHashMap<String, Object> sortedData = new LinkedHashMap<>();
        for (String key : sortedKeys){
            sortedData.put(key, data.get(key));
        }

        // Clears the list so the new list can be in the right order
        data.clear();
        // Replaces list with sorted list
        data.putAll(sortedData);
    }

    public static void clearData(){
        // clears all data so a new match can be started
        data.clear();
    }

    public static String checkData(){
        StringBuilder missingVals = new StringBuilder();
        for(String key: data.keySet()){
            // If the data is a default value, then the user didn't change it so it's added to missing values
            if (data.get(key).equals(0) || data.get(key).equals("")){
                String name = key.replace('_', ' ');
                missingVals.append(name).append("\n");
            }
        }

        // Returns whether the data was inputted correctly or not
        if (missingVals.toString().isEmpty()){return "All data has been successfully inputted.";}
        else {return "The input for these values were not changed:\n" + missingVals.toString();}
    }

    public static void exportData(Context c){
        // Finds the folder that the file will get saved to
        String pathToExternalStorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File path = new File(pathToExternalStorage + "/" + "Qualitative-" + year);
        JSONObject jsonObject = new JSONObject();

        //start page
        for(String key: data.keySet()) {
            try {
                // Any value that is an integer as a string gets turned into an integer (ex "1" --> 1)
                int val;
                try {val = Integer.parseInt(data.get(key).toString());}
                catch (Exception e) {val = -100;}
                if (val != -100){ jsonObject.put(key, val); }
                // Any value that is not entered (aka 'Dropdown') becomes empty
                else if (data.get(key).equals("Dropdown")){ jsonObject.put(key, ""); }
                // Exports value saved in hashmap
                else { jsonObject.put(key, data.get(key)); }
            }
            catch (JSONException e) {throw new RuntimeException(e);}
        }

        try {
            // Saves the data to the file path
            Calendar calendar = Calendar.getInstance();
            FileOutputStream writer = new FileOutputStream(new File(path, year+"_QUALITATIVE_SCOUTING_DATA_" + calendar.getTimeInMillis() + ".json"));
            writer.write(jsonObject.toString().getBytes());
            writer.close();
            Toast.makeText(c, "Saved data!", Toast.LENGTH_SHORT).show();
        }
        catch (IOException e) { e.printStackTrace(); }

    }
}