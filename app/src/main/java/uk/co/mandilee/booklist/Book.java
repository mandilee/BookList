package uk.co.mandilee.booklist;

public class Book {
    private String mTitle;
    private String mSubtitle;
    private String mAuthors;
    private String mDescription;
    private String mImageUrl;

    public Book(String title, String subtitle, String authors, String description, String imageUrl) {
        mTitle = title;
        mSubtitle = subtitle;
        mAuthors = authors;
        mDescription = description;
        mImageUrl = imageUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public String getAuthors() {
        return mAuthors;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
}
