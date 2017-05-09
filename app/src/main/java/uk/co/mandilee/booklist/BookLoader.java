package uk.co.mandilee.booklist;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

class BookLoader extends AsyncTaskLoader<List<Book>> {

    final private String mUrl;

    BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        return HttpRequest.fetchBookData(mUrl);
    }
}
