package uk.co.mandilee.booklist;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

final class HttpRequest {

    private static final String LOG_TAG = HttpRequest.class.getSimpleName();

    private HttpRequest() {
        // intentionally blank - shouldn't be called!
    }

    static List<Book> fetchBookData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        return extractFeatureFromJson(jsonResponse);
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000 /* milliseconds */);
            connection.setConnectTimeout(15000 /* milliseconds */);
            connection.setRequestMethod("GET");
            connection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (connection.getResponseCode() == 200) {
                inputStream = connection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the book JSON results.", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Book> extractFeatureFromJson(String bookJSON) {
        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }

        List<Book> books = new ArrayList<>();
        try {

            JSONObject baseJsonResponse = new JSONObject(bookJSON);

            JSONArray bookArray = baseJsonResponse.getJSONArray("items");

            for (int i = 0; i < bookArray.length(); i++) {
                String subtitle = "",
                        authors = "",
                        description = "";

                JSONObject currentBook = bookArray.getJSONObject(i);
                JSONObject properties = currentBook.getJSONObject("volumeInfo");

                String title = properties.getString("title");

                try {
                    subtitle = properties.getString("subtitle");
                } catch (JSONException e) {
                    // No subtitle!
                }

                JSONArray authorsArray = properties.getJSONArray("authors");
                for (int b = 0; b < authorsArray.length(); b++) {
                    authors += authorsArray.getString(b);
                }

                try {
                    description = properties.getString("description");
                } catch (JSONException e) {
                    // No description!
                }

                JSONObject imageObject = properties.getJSONObject("imageLinks");
                String imageUrl = imageObject.getString("smallThumbnail");

                Book book = new Book(title, subtitle, authors, description, imageUrl);

                books.add(book);
            }

        } catch (JSONException e) {
            Log.e("HttpRequest", "Problem parsing the book JSON results", e);
        }

        return books;
    }

}
