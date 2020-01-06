package fr.arolla.karma;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class Post {
    private UUID post_uuid;
    private String title;
    private String content;
    private Date publishing_date;
    private List<String> categories;

    public UUID getPost_uuid() {
        return post_uuid;
    }

    public void setCategories(List<String> categoriesFor) {
        this.categories.addAll(categoriesFor);
    }
}
