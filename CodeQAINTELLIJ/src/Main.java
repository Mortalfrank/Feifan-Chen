import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // 创建一个Post实例
        Post myPost = new Post(1, "Example Title", "This is the body of the post with at least 300 characters. " +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. " +
                "Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. " +
                "Praesent mauris. Fusce nec tellus sed augue semper porta.", "tag1,tag2", "Difficult", "Immediately Needed");

        // 调用addPost方法
        addPost(myPost);

        // 调用addComments方法
        ArrayList<String> comments = new ArrayList<>();
        comments.add("This is a valid comment.");

        addComments(myPost, comments);
    }

    /**
     * 添加帖子的方法
     * @param post Post对象
     */
    public static void addPost(Post post) {
        boolean result = post.addPost();
        if (result) {
            System.out.println("Post added successfully.");
        } else {
            System.out.println("Failed to add post.");
        }
    }
    /**
     * 添加评论的方法
     * @param post Post对象
     * @param comments 评论列表
     */
    public static void addComments(Post post, ArrayList<String> comments) {
        boolean commentResult = post.addComments(comments);
        if (commentResult) {
            System.out.println("Comments added successfully.");
        } else {
            System.out.println("Failed to add comments.");
        }
    }
}
