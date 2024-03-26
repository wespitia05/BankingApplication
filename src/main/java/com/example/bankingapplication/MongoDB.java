package com.example.bankingapplication;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoDB {
    public static void main(String[] args) {
        /*String uri = "mongodb+srv://admin:admin@mongodb.c9iio9o.mongodb.net/?retryWrites=true&w=majority&appName=MongoDB";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("MongoDB");
            MongoCollection<Document> collection = database.getCollection("bankingUserInfo");
            List<Document> document = new ArrayList<>();
            document.add (new Document("First Name", "John")
                    .append("Last Name", "Wick")
                    .append("Address", "23 Dog Ave")
                    .append("Zip Code", "11917")
                    .append("Date of Birth", "12/25/76")
                    .append("Username", "wickj")
                    .append("Password", "dog76")
                    .append("Checking Balance", 800)
                    .append("Savings Balance", 4500));
            document.add (new Document("First Name", "Jack")
                    .append("Last Name", "Sparrow")
                    .append("Address", "100 Black Pearl Ave")
                    .append("Zip Code", "11354")
                    .append("Date of Birth", "04/05/81")
                    .append("Username", "sparrowj")
                    .append("Password", "rum81")
                    .append("Checking Balance", 1200)
                    .append("Savings Balance", 5000));
            document.add (new Document("First Name", "Jason")
                    .append("Last Name", "Bourne")
                    .append("Address", "17 Fight Ave")
                    .append("Zip Code", "11678")
                    .append("Date of Birth", "07/08/79")
                    .append("Username", "bournej")
                    .append("Password", "fight79")
                    .append("Checking Balance", 500)
                    .append("Savings Balance", 2600));
            document.add (new Document("First Name", "Thomas")
                    .append("Last Name", "Shelby")
                    .append("Address", "34 Peaky Ave")
                    .append("Zip Code", "11453")
                    .append("Date of Birth", "09/26/88")
                    .append("Username", "shelbyt")
                    .append("Password", "blinders88")
                    .append("Checking Balance", 4000)
                    .append("Savings Balance", 7800));
            document.add (new Document("First Name", "Bruce")
                    .append("Last Name", "Wayne")
                    .append("Address", "Wayne Enterprise")
                    .append("Zip Code", "11897")
                    .append("Date of Birth", "10/31/82")
                    .append("Username", "wayneb")
                    .append("Password", "batman82")
                    .append("Checking Balance", 7000)
                    .append("Savings Balance", 12500));
            collection.insertMany(document);
            System.out.println ("User information added to database");
       }
             */
    }
}


