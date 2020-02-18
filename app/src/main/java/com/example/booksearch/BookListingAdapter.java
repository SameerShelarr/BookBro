package com.example.booksearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;


public class BookListingAdapter extends ArrayAdapter<Books> {

    public BookListingAdapter(@NonNull Context context, ArrayList<Books> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_item, parent, false);
        }

        Books currentBook = getItem(position);

        //setting up the title TextView in the list item layout
        TextView titleTextView = listItemView.findViewById(R.id.list_item_title_text_view);
        String title = currentBook.getTitle();
        titleTextView.setText(title);

        //setting up the author TextView in the list item layout
        TextView authorTextView = listItemView.findViewById(R.id.list_item_author_text_view);
        authorTextView.setText(currentBook.getAuthor());

        //setting up the published date TextView in the list item layout
        TextView publishedDateTextView = listItemView.findViewById(R.id.list_item_published_date);
        publishedDateTextView.setText(currentBook.getPublishedDate());

        return listItemView;
    }
}
