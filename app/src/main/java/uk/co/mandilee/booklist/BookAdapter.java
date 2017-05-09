package uk.co.mandilee.booklist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

class BookAdapter extends ArrayAdapter<Book> {

    BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }

        Book currentBook = getItem(position);

        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        TextView subtitleView = (TextView) listItemView.findViewById(R.id.subtitle);
        TextView authorsView = (TextView) listItemView.findViewById(R.id.authors);
        TextView descriptionView = (TextView) listItemView.findViewById(R.id.description);
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.imageView);

        titleView.setText(currentBook.getTitle());
        subtitleView.setText(currentBook.getSubtitle());
        authorsView.setText(currentBook.getAuthors());
        descriptionView.setText(currentBook.getDescription());

        if (currentBook.getImageUrl().equals("")) {
            imageView.setImageResource(R.drawable.ic_no_preview);
        } else {
            new DownloadImageTask(imageView).execute(currentBook.getImageUrl());
        }

        return listItemView;
    }


    // this function grabbed from Stack Overflow. Can't remember where...
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        final ImageView bmImage;

        DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
