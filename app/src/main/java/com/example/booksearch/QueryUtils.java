package com.example.booksearch;

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

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getName();

    private QueryUtils(){}

    public static ArrayList<Books> fetchBooksData(String requestUrl){
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        ArrayList<Books> books = extractBooksFromJSON(jsonResponse);

        // Return the {@link Event}
        return books;
    }

    public static URL createUrl(String enterUrl){
        if (enterUrl == null){
            Log.e(LOG_TAG, "NULL URL passed in createUrl() method");
        }

        URL url = null;
        try {
            url = new URL(enterUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            Log.e(LOG_TAG, "NULL URL passed in the makeHttpRequest() method");
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
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

    public static ArrayList<Books> extractBooksFromJSON(String JSONString){
        if (JSONString == null){
            Log.e(LOG_TAG, "NULL JSONString passed in extractBooksFromJSON() method");
            return null;
        }

        try {
            ArrayList<Books> books = new ArrayList<>();
            JSONObject rootJSONObject = new JSONObject(JSONString);
            JSONArray itemsJSONArray = rootJSONObject.optJSONArray("items");

            if (itemsJSONArray == null){
                return null;
            }

            for (int i = 0; i < itemsJSONArray.length(); i++){
                JSONObject bookJSONObject = itemsJSONArray.optJSONObject(i);
                JSONObject volumeInfoJSONObject = bookJSONObject.optJSONObject("volumeInfo");

                //getting the title from JSON
                String title = null;
                if (volumeInfoJSONObject != null) {
                    title = volumeInfoJSONObject.optString("title");
                }

                //getting the author from the JSON
                JSONArray authorsJSONArray = volumeInfoJSONObject.optJSONArray("authors");
                String author = null;
                if (authorsJSONArray != null) {
                    author = authorsJSONArray.optString(0);
                }

                //getting the published date from the JSON
                String publishedDate = volumeInfoJSONObject.optString("publishedDate");

                //getting the info URL
                String infoURL = volumeInfoJSONObject.optString("infoLink");

                //getting the image URL from the JSON
                JSONObject imageLinksJSONObject = volumeInfoJSONObject.optJSONObject("imageLinks");
                String imageURL = null;
                if (imageLinksJSONObject != null) {
                    imageURL = imageLinksJSONObject.optString("smallThumbnail");
                }

                books.add(new Books(title, author, publishedDate, infoURL, imageURL));
            }
            return books;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
