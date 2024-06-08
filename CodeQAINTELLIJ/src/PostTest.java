import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PostTest {

    private List<Post> posts;
    private List<Boolean> postExpectedResults;
    private List<CommentTestData> commentTestDataList;

    @BeforeEach
    public void setUp() throws IOException {
        posts = new ArrayList<>();
        postExpectedResults = new ArrayList<>();
        commentTestDataList = new ArrayList<>();

        try (BufferedReader postReader = new BufferedReader(new FileReader("post_test_data.txt"))) {
            String line;
            while ((line = postReader.readLine()) != null) {
                if (line.trim().isEmpty() || line.trim().startsWith("#")) continue;
                int postID = Integer.parseInt(line.trim());
                String postTitle = postReader.readLine().trim();
                String postBody = postReader.readLine().trim();
                String postTags = postReader.readLine().trim();
                String postType = postReader.readLine().trim();
                String postEmergency = postReader.readLine().trim();
                boolean expectedResult = Boolean.parseBoolean(postReader.readLine().trim());
                Post post = new Post(postID, postTitle, postBody, postTags, postType, postEmergency);
                posts.add(post);
                postExpectedResults.add(expectedResult);
            }
        }

        try (BufferedReader commentReader = new BufferedReader(new FileReader("comment_test_data.txt"))) {
            String line;
            while ((line = commentReader.readLine()) != null) {
                if (line.trim().isEmpty() || line.trim().startsWith("#")) continue;
                String comment = line.trim();
                boolean expectedResult = Boolean.parseBoolean(commentReader.readLine().trim());
                commentTestDataList.add(new CommentTestData(comment, expectedResult));
            }
        }
    }



    @Test
    public void testAddPost() {
        for (int i = 0; i < posts.size(); i++) {
            Post post = posts.get(i);
            boolean result = post.addPost();
            boolean expectedResult = postExpectedResults.get(i);
            if (expectedResult) {
                assertTrue(result);
            } else {
                assertFalse(result);
            }
        }
    }

    @Test
    public void testAddComments() {
        for (Post post : posts) {
            for (CommentTestData commentTestData : commentTestDataList) {
                ArrayList<String> commentList = new ArrayList<>();
                commentList.add(commentTestData.getComment());
                boolean result = post.addComments(commentList);
                boolean expectedResult = commentTestData.isExpectedResult();
                if (expectedResult) {
                    assertTrue(result, "Expected success, got: Failure for comment: " + commentTestData.getComment());
                } else {
                    assertFalse(result, "Expected failure, got: Success for comment: " + commentTestData.getComment());
                }
            }
        }
    }

    private static class CommentTestData {
        private final String comment;
        private final boolean expectedResult;

        public CommentTestData(String comment, boolean expectedResult) {
            this.comment = comment;
            this.expectedResult = expectedResult;
        }

        public String getComment() {
            return comment;
        }

        public boolean isExpectedResult() {
            return expectedResult;
        }
    }
}
