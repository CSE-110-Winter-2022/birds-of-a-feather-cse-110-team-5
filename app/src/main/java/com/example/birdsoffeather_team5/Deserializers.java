package com.example.birdsoffeather_team5;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class Deserializers {
    public Object StudentDeserializer;

    public static class ClassDataDeserializer<T> implements JsonDeserializer<T> {
        @Override
        public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return context.deserialize(json, BOFClassData.class);
        }
    }

    public static class StudentDeserializer<T> implements JsonDeserializer<T> {
        @Override
        public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return context.deserialize(json, BOFStudent.class);
        }
    }

    public static class SharedClassesDeserializer<T> implements JsonDeserializer<T> {
        @Override
        public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return context.deserialize(json, BOFSharedClasses.class);
        }
    }
}
