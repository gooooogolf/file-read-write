package com.kupirng.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.kupirng.file.model.Person;
import com.kupirng.file.model.Persons;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class FileUtils {

    public static void main(String[] args) throws IOException {
        long start, end;

        start = System.currentTimeMillis();
        System.out.println("Start reading : " + start);
        FileUtils.readStreamJava8();
        end = System.currentTimeMillis();
        System.out.println("Finish. Total time: " + (end - start) + "\n");

        start = System.currentTimeMillis();
        System.out.println("Start reading : " + start);
        FileUtils.readStreamGson();
        end = System.currentTimeMillis();
        System.out.println("Finish. Total time: " + (end - start) + "\n");

        start = System.currentTimeMillis();
        System.out.println("Start reading : " + start);
        FileUtils.updateStreamGson();
        end = System.currentTimeMillis();
        System.out.println("Finish. Total time: " + (end - start) + "\n");

    }

    public static void readStreamJava8() throws IOException {
        Path path = Paths.get("/Users/Gooooogolf/temp", "data.json");
        try (Stream<String> lines = Files.lines(path)) {
            Optional<String> has = lines.filter(s -> s.contains("Thomas")).findFirst();
            if (has.isPresent()) {
                System.out.println(has.get());
            }
        }
    }

    public static void readStreamGson() {
        try {
            File file = new File("/Users/Gooooogolf/temp/data.json");
            InputStream in = new FileInputStream(file);

            JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
            Gson gson = new GsonBuilder().create();

            reader.beginArray();
            while (reader.hasNext()) {
                Person person = gson.fromJson(reader, Person.class);
                if (person.getName().contains("Thomas")) {
                    System.out.println(person);
                    break;
                }
            }
            reader.close();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void updateStreamGson() {
        try {
            Persons persons = new Persons();
            File file = new File("/Users/Gooooogolf/temp/data.json");
            InputStream in = new FileInputStream(file);

            OutputStream out = new FileOutputStream("/Users/Gooooogolf/temp/output.json");
            JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));

            JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
            Gson gson = new GsonBuilder().create();

            reader.beginArray();
            writer.beginArray();
            while (reader.hasNext()) {
                Person person = gson.fromJson(reader, Person.class);
                if (person.getName().contains("Thomas")) {
                    person.setName("Gooooogolf");
                }
                gson.toJson(person, Person.class, writer);
            }
            reader.endArray();
            writer.endArray();

            reader.close();
            writer.close();


        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
