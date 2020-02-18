package com.example.booksearch;

import android.content.AsyncTaskLoader;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BooksLoader extends AsyncTaskLoader<ArrayList<Books>> {

    String mUrl;

    public BooksLoader(@NonNull Context context, String url) {
        super(context);
        mUrl = url;
        onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()){forceLoad();}
    }

    @Nullable
    @Override
    public ArrayList<Books> loadInBackground() {
        return QueryUtils.fetchBooksData(mUrl);
    }
}
