package com.contactsunny.poc.couchbase_curd_poc.services;

import com.couchbase.client.core.message.kv.subdoc.multi.Mutation;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.subdoc.DocumentFragment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class CouchbaseService {

    @Value("${couchbase.host}")
    private String host;

    @Value("${couchbase.auth.username}")
    private String username;

    @Value("${couchbase.auth.password}")
    private String password;

    private static final String USERS_BUCKET = "users";

    Bucket usersBucket;

    @PostConstruct
    private void postConstructor() {
        Cluster cluster = CouchbaseCluster.create(host);
        cluster.authenticate(username, password);
        usersBucket = cluster.openBucket(USERS_BUCKET);
    }

    public JsonDocument saveUser(String id, JsonObject user) {
        return usersBucket.upsert(JsonDocument.create(id, user));
    }

    public JsonDocument getDocumentWithId(String id) {
        return usersBucket.get(id);
    }

    public DocumentFragment<Mutation> updateUser(String id, String updatedEmail) {
        return usersBucket.mutateIn(id).upsert("email", updatedEmail).execute();
    }

    public JsonDocument deleteDocument(String id) {
        return usersBucket.remove(id);
    }
}
