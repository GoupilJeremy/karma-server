package fr.arolla.karma;

import java.util.List;
import java.util.UUID;

public interface Model {
    UUID createPost(String title, String content, List categories);
    UUID createComment(UUID post, String author, String content);
    List getAllPosts();
    List getAllEvents();
    List getAllCommentsOn(UUID post);
    boolean existPost(UUID post);
}
