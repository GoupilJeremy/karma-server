package fr.arolla.karma;

import lombok.Data;

@Data
public class NewCommentPayload {
    private String author;
    private String content;

    public boolean isValid() {
        return author != null && !author.isEmpty() && content != null && !content.isEmpty();
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
