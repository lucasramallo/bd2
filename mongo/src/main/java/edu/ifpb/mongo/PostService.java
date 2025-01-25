package edu.ifpb.mongo;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import com.mongodb.MongoException;
import com.mongodb.client.result.InsertOneResult;
import static com.mongodb.client.model.Filters.eq;

import java.time.LocalDateTime;

public class PostService {
    private MongoClient mongoClient;

    public PostService() {
        String uri = "";
        this.mongoClient = MongoClients.create(uri);
    }

    public void savePost(String usename, String content) {
        MongoDatabase database = this.mongoClient.getDatabase("Post");
        MongoCollection<Document> collection = database.getCollection("posts");
        LocalDateTime data = LocalDateTime.now();
        try {
            InsertOneResult result = collection.insertOne(new Document()
                    .append("_id", new ObjectId())
                    .append("username", usename)
                    .append("postContent", new Document()
                            .append("content", content)
                            .append("createdAt", data)
                    )

            );
            System.out.println("Success! Inserted document id: " + result.getInsertedId());

        } catch (MongoException me) {
            System.err.println("Unable to insert due to an error: " + me);
        }
    }

    public void getPostsByUsername(String username) {
        MongoDatabase database = this.mongoClient.getDatabase("Post");
        MongoCollection<Document> collection = database.getCollection("posts");

        FindIterable<Document> docs = collection.find(eq("username", username));

        if (docs.iterator().hasNext()) {
            for (Document doc : docs) {
                System.out.println(doc.toJson());
            }
        } else {
            System.out.println("No matching documents found.");
        }
    }

    public void deletePostById(String id) {
        MongoDatabase database = this.mongoClient.getDatabase("Post");
        MongoCollection<Document> collection = database.getCollection("posts");

        ObjectId objectId = new ObjectId(id);

        collection.deleteOne(Filters.eq("_id", objectId));
        System.out.println("Post com id " + id + " excluído com sucesso.");
    }

    public void editPostById(String id, String newContent) {
        MongoDatabase database = this.mongoClient.getDatabase("Post");
        MongoCollection<Document> collection = database.getCollection("posts");

        ObjectId objectId = new ObjectId(id);

        Bson filter = eq("_id", objectId);

        Bson update = Updates.set("postContent.content", newContent);

        try {
            collection.updateOne(filter, update);
            System.out.println("Post com id " + id + " atualizado com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao atualizar o post: " + e.getMessage());
        }
    }


}