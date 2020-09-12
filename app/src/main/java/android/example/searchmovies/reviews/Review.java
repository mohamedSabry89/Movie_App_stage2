package android.example.searchmovies.reviews;

public class Review {

    private int reviewId;
    private String author;
    private String theReview;

    public Review() {
    }

    public Review(int reviewId, String author, String theReview) {
        this.reviewId = reviewId;
        this.author = author;
        this.theReview = theReview;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTheReview() {
        return theReview;
    }

    public void setTheReview(String theReview) {
        this.theReview = theReview;
    }
}
