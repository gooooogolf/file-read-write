package com.kupirng.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.kupirng.file.model.Person;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {

    public static void main(String[] args) throws IOException {
        long start, end;

        start = System.currentTimeMillis();
        System.out.println("Start createOutputFile : " + start);
        FileUtils.createOutputFile();
        end = System.currentTimeMillis();
        System.out.println("Finish. Total time: " + (end - start) + "\n");

        start = System.currentTimeMillis();
        System.out.println("Start get 8888 : " + start);
        FileUtils.get(8888);
        end = System.currentTimeMillis();
        System.out.println("Finish. Total time: " + (end - start) + "\n");

        start = System.currentTimeMillis();
        System.out.println("Start get 9991 : " + start);
        FileUtils.get(9991);
        end = System.currentTimeMillis();
        System.out.println("Finish. Total time: " + (end - start) + "\n");

        start = System.currentTimeMillis();
        System.out.println("Start get 23 : " + start);
        FileUtils.get(23);
        end = System.currentTimeMillis();
        System.out.println("Finish. Total time: " + (end - start) + "\n");

        start = System.currentTimeMillis();
        System.out.println("Start get 456 : " + start);
        FileUtils.get(456);
        end = System.currentTimeMillis();
        System.out.println("Finish. Total time: " + (end - start) + "\n");


        List<Person> persons = new ArrayList<Person>();
        for (int i = 9000; i < 9999; i++) {
            Person person = new Person();
            person.setId(i);
            person.setName("p_" + i);
            persons.add(person);
        }

        start = System.currentTimeMillis();
        System.out.println("Start updatePersons : " + start);
        FileUtils.updatePersons(persons);
        end = System.currentTimeMillis();
        System.out.println("Finish. Total time: " + (end - start) + "\n");

        start = System.currentTimeMillis();
        System.out.println("Start appendPersons : " + start);
        FileUtils.appendPersons(persons);
        end = System.currentTimeMillis();
        System.out.println("Finish. Total time: " + (end - start) + "\n");

    }

    public static Person get(int id) throws IOException {
        Path path = Paths.get("/Users/Gooooogolf/temp", "output.json");
        try (Stream<String> lines = Files.lines(path)) {
            Gson gson = new GsonBuilder().create();
            Optional<Person> hasPerson = lines.map(person -> gson.fromJson(person, Person.class))
                    .filter(person -> person.getId() == id)
                    .findFirst();

            return hasPerson.isPresent() ? hasPerson.get() : null;
        }
    }

    public static void updatePersons(List<Person> persons) throws IOException {
        Path path = Paths.get("/Users/Gooooogolf/temp", "output.json");
        try (Stream<String> lines = Files.lines(path)) {
            Gson gson = new GsonBuilder().create();
            List<String> replaced = lines.map(line -> {
                Person person = gson.fromJson(line, Person.class);
                Optional<Person> hasUpdate = persons.stream().filter(update -> update.getId() == person.getId()).findFirst();
                return hasUpdate.isPresent() ? gson.toJson(hasUpdate.get()) : line;
            }).collect(Collectors.toList());

            Files.write(path, replaced);
        }
    }

    public static void appendPersons(List<Person> persons) throws IOException {
        Gson gson = new GsonBuilder().create();
        Path path = Paths.get("/Users/Gooooogolf/temp", "output.json");
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            String updated = persons.stream().map(person -> gson.toJson(person)).collect(Collectors.joining("\n"));
            writer.write(updated);
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

    public static void createOutputFileWithObject() {
        try {
            File file = new File("/Users/Gooooogolf/temp/data.json");
            InputStream in = new FileInputStream(file);

            OutputStream out = new FileOutputStream("/Users/Gooooogolf/temp/output.json");
            JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));

            JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            reader.beginArray();
            writer.beginArray();
            while (reader.hasNext()) {
                Person person = gson.fromJson(reader, Person.class);
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

    public static void updateOldFile() {
        try {
            List<Person> persons = new ArrayList<Person>();
            for (int i = 0; i < 100; i++) {
                Person person = new Person();
                person.setId(10000 + i);
                person.setName("p_" + 10000 + i);
                persons.add(person);
            }

            FileWriter writer = new FileWriter("/Users/Gooooogolf/temp/output.json", true);

            Gson gson = new GsonBuilder().create();
            int count = 0;
            while (count != persons.size()) {
                writer.append(",");
                writer.append(gson.toJson(persons.get(count)));
                count ++;
            }
            writer.close();

        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void createOutputFile() {
        try {
            File file = new File("/Users/Gooooogolf/temp/data.json");
            InputStream in = new FileInputStream(file);

            FileWriter writer = new FileWriter("/Users/Gooooogolf/temp/output.json");

            JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
            Gson gson = new GsonBuilder().create();

            reader.beginArray();
            while (reader.hasNext()) {
                Person person = gson.fromJson(reader, Person.class);
                writer.append(gson.toJson(person));
                writer.write(System.getProperty( "line.separator" ));
            }
            reader.endArray();
            reader.close();
            writer.close();

        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
