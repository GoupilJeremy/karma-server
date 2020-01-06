package fr.arolla.karma;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class NewPostPayload {
    private String title;
    private List<String> categories = new LinkedList<>();
    private String content;

    public boolean isValid() {
        return title != null && !title.isEmpty() && content != null && !content.isEmpty();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<String> getCategories() {
        return categories;
    }
}
