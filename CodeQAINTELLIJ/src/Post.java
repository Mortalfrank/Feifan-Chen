import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Post {
    private int postID;
    private String postTitle;
    private String postBody;
    private String postTags;
    private String postType;
    private String postEmergency;
    private ArrayList<String> postComments = new ArrayList<>();
    private static final String[] postTypes = {"Very Difficult", "Difficult", "Easy"};
    private static final String[] postEmergencies = {"Immediately Needed", "Highly Needed", "Ordinary"};

    public Post(int postID, String postTitle, String postBody, String postTags, String postType, String postEmergency) {
        this.postID = postID;
        this.postTitle = postTitle;
        this.postBody = postBody;
        this.postTags = postTags;
        this.postType = postType;
        this.postEmergency = postEmergency;
    }

    public int getPostID() {
        return postID;
    }

    public String getPostType() {
        return postType;
    }

    public String getPostEmergency() {
        return postEmergency;
    }

    public ArrayList<String> getPostComments() {
        return postComments;
    }

    public boolean addPost() {
        if (!validateTitle(postTitle) || !validateBody(postBody) || !validateTags(postTags) || !validateType(postType, postTags, postBody) || !validateEmergency(postType, postEmergency)) {
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("post.txt", true))) {
            writer.write("Post ID: " + postID);
            writer.write("\nTitle: " + postTitle);
            writer.write("\nBody: " + postBody);
            writer.write("\nTags: " + postTags);
            writer.write("\nType: " + postType);
            writer.write("\nEmergency: " + postEmergency);
            writer.write("\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addComments(ArrayList<String> comments) {
        int newCommentCount = postComments.size() + comments.size();
        int maxComments = postType.equals("Easy") || postEmergency.equals("Ordinary") ? 3 : 5;

        if (newCommentCount > maxComments) {
            // System.out.println("Failed to add comments: Total comments exceed limit");
            return false;
        }

        for (String comment : comments) {
            if (!validateComment(comment)) {
                return false;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("comment.txt", true))) {
            for (String comment : comments) {
                writer.write("Comment: " + comment);
                writer.write("\n");
                postComments.add(comment);
                // System.out.println("Successfully added comment: " + comment);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean validateTitle(String title) {
        if (title.length() < 10 || title.length() > 250) {
            return false;
        }
        for (int i = 0; i < 5; i++) {
            char ch = title.charAt(i);
            if (Character.isDigit(ch) || !Character.isLetter(ch)) {
                return false;
            }
        }
        return true;
    }

    private boolean validateBody(String body) {
        return body.length() >= 250;
    }

    private boolean validateTags(String tags) {
        String[] tagArray = tags.split(",");
        if (tagArray.length < 2 || tagArray.length > 5) {
            return false;
        }
        for (String tag : tagArray) {
            if (tag.length() < 2 || tag.length() > 10 || !tag.equals(tag.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    private boolean validateType(String type, String tags, String body) {
        String[] tagArray = tags.split(",");
        if (type.equals("Easy") && tagArray.length > 3) {
            return false;
        }
        if ((type.equals("Very Difficult") || type.equals("Difficult")) && body.length() < 300) {
            return false;
        }
        return true;
    }

    private boolean validateEmergency(String type, String emergency) {
        if (type.equals("Easy") && (!emergency.equals("Ordinary"))) {
            return false;
        }
        if ((type.equals("Very Difficult") || type.equals("Difficult")) && emergency.equals("Ordinary")) {
            return false;
        }
        return true;
    }

    private boolean validateComment(String comment) {
        String[] words = comment.split(" ");
        if (words.length < 4 || words.length > 10) {
            // System.out.println("Failed validateComment: Invalid word count in comment");
            return false;
        }
        if (!Character.isUpperCase(words[0].charAt(0))) {
            // System.out.println("Failed validateComment: First character not uppercase");
            return false;
        }
        int maxComments = postType.equals("Easy") || postEmergency.equals("Ordinary") ? 3 : 5;
        if (postComments.size() >= maxComments) {
            // System.out.println("Failed validateComment: Too many comments for post");
            return false;
        }
        return true;
    }
}
