package fr.arolla.karma;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Comment {
    UUID comment_uuid;
    UUID post_uuid;
    String author;
    String content;
    boolean approved;
    Date submission_date;
}
