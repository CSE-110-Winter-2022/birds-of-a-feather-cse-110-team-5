package com.example.birdsoffeather_team5;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SessionSaver {
    public static void saveEntireSession(Context context, String nameToSaveUnder, List<SharedClasses> session) {
        SharedPreferences sessionPref = context.getSharedPreferences("sessions", context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sessionPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(session);
        edit.putString(nameToSaveUnder, json);
        edit.apply();
    }

    public static void writeToSessionNames(Context context, String sessionName) {
        SharedPreferences pref = context.getSharedPreferences("allSavedNames", context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();


        List<String> allNames = retrieveSessionNames(context);
        if (allNames.contains(sessionName)) {
            return;
        }
        Gson gson = new Gson();
        allNames.add(sessionName);
        String allNamesStr = gson.toJson(allNames);
        edit.putString("names", allNamesStr);
        edit.apply();
    }

    public static List<String> retrieveSessionNames(Context context) {
        SharedPreferences pref = context.getSharedPreferences("allSavedNames", context.MODE_PRIVATE);
        String allNamesStr = pref.getString("names", "");
        if(allNamesStr.equals("")) {
            return new ArrayList<String>();
        }

        Gson gson = new Gson();
        Type stringListType = new TypeToken<List<String>>() {}.getType();
        List<String> allNames = gson.fromJson(allNamesStr, stringListType);
        return allNames;
    }

    public static void updateCurrentSession(Context context, List<SharedClasses> currSession) {
        saveEntireSession(context, "currentSession", currSession);
    }

    public static void addFavoriteStudent(Context context, SharedClasses favorite) {
        List<SharedClasses> favorites = getFavoriteStudents(context);
        favorites.add(favorite);
        saveEntireSession(context, "favorites", favorites);
    }

    public static List<SharedClasses> getFavoriteStudents(Context context) {
        return retrieveSession(context, "favorites");
    }

    public static List<SharedClasses> retrieveSession(Context context, String sessionName) {
        SharedPreferences sessionPref = context.getSharedPreferences("sessions", context.MODE_PRIVATE);
        String sessionStr = sessionPref.getString(sessionName, "");

        if(sessionStr.equals("")) {
            return new ArrayList<SharedClasses>();
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ClassData.class, new Deserializers.ClassDataDeserializer())
                .registerTypeAdapter(Student.class, new Deserializers.StudentDeserializer())
                .registerTypeAdapter(SharedClasses.class, new Deserializers.SharedClassesDeserializer())
                .create();
        Type sharedClassesListType = new TypeToken<ArrayList<SharedClasses>>() {}.getType();
        List<SharedClasses> session = gson.fromJson(sessionStr, sharedClassesListType);
        return session;
    }

    public static List<SharedClasses> retrieveCurrentSession(Context context) {
        return retrieveSession(context, "currentSession");
    }
}
