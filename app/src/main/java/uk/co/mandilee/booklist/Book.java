package uk.co.mandilee.booklist;

class Book {
    private final String mTitle,
            mSubtitle,
            mAuthors,
            mDescription,
            mImageUrl;

    Book(String title, String subtitle, String authors, String description, String imageUrl) {
        mTitle = title;
        mSubtitle = subtitle;
        mAuthors = authors;
        mDescription = description;
        mImageUrl = imageUrl;
    }

    String getTitle() {
        return mTitle;
    }

    String getSubtitle() {
        return mSubtitle;
    }

    String getAuthors() {
        return mAuthors;
    }

    String getDescription() {
        return mDescription;
    }

    String getImageUrl() {
        return mImageUrl;
    }
}
