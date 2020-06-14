package com.contactsunny.poc.couchbase_curd_poc;

import com.contactsunny.poc.couchbase_curd_poc.services.CouchbaseService;
import com.couchbase.client.core.message.kv.subdoc.multi.Mutation;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.subdoc.DocumentFragment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class App implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    @Autowired
    private CouchbaseService couchbaseService;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String id = "583d7542-7f25-431a-979a-79b300554b0d";
        JsonObject user = JsonObject.create();
        user.put("id", id);
        user.put("name", "Sunny");
        user.put("email", "sunny.3mysore@gmail.com");
        JsonDocument result = couchbaseService.saveUser(id, user);
        logger.info("Upsert Result: " + result);

        JsonDocument retrievedDocument = couchbaseService.getDocumentWithId(id);
        logger.info("Retrieved document: " + retrievedDocument);

        DocumentFragment<Mutation> updateResult = couchbaseService.updateUser(id, "sunny@contactsunny.com");
        logger.info("Updated result: " + updateResult.toString());

        JsonDocument deleteResult = couchbaseService.deleteDocument(id);
        logger.info("Delete result: " + deleteResult.toString());
    }
}
