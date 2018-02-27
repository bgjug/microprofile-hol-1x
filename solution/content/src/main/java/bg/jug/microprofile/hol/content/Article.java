package bg.jug.microprofile.hol.content;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.concurrent.atomic.AtomicLong;

public class Article {

    private static AtomicLong sequence = new AtomicLong(0);

    private Long id;
    private String title;
    private String content;
    private String author;

    public Article(Long id, String title, String content, String author) {
        if (id == null) {
            this.id = sequence.incrementAndGet();
        } else {
            this.id = id;
        }
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public static Article fromJson(JsonObject jsonObject) {
        Long id = null;
        if (jsonObject.containsKey("id")) {
            id = jsonObject.getJsonNumber("id").longValue();
        }

        return new Article(id, jsonObject.getString("title"),
                jsonObject.getString("content"), jsonObject.getString("author"));
    }

    public JsonObject toJson() {
        return toJson(null);
    }

    public JsonObject toJson(JsonObject authorJson) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("id", id);
        builder.add("title", title);
        builder.add("content", content);
        if (authorJson != null) {
            builder.add("author", authorJson);
        }
        return builder.build();
    }
}
