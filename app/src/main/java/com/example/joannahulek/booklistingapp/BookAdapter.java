package com.example.joannahulek.booklistingapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joasia on 05.07.2017.
 */

public class BookAdapter extends ArrayAdapter<Book> implements Serializable {
    public BookAdapter(@NonNull Context context, List<Book> objects) {
        super(context, 0, objects == null ? new ArrayList<Book>() : objects);
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }

        final Book currentBook = getItem(position);
        assert currentBook != null;

        setElementVisibilityOrText(listItemView, R.id.authors_text_view, currentBook.getAuthors(), 1);
        setElementVisibilityOrText(listItemView, R.id.title_text_view, currentBook.getTitle(), 0);
        setElementVisibilityOrText(listItemView, R.id.subtitle_text_view, currentBook.getSubtitle(), 0);
        setElementVisibilityOrText(listItemView, R.id.publisher_text_view, currentBook.getPublisher(), 2);
        setElementVisibilityOrText(listItemView, R.id.published_date_text_view, currentBook.getPublishedDate(), 0);

        View bookInfoLayout = listItemView.findViewById(R.id.book_info_layout);

        bookInfoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri previewLink = Uri.parse(currentBook.getPreviewLink());
                Intent i = new Intent(Intent.ACTION_VIEW, previewLink);
                getContext().startActivity(i);
            }
        });

        return listItemView;
    }

    private void setElementVisibilityOrText(View listItemView, int recource_id, String currentText, int textFormatId) {
        TextView textView = (TextView) listItemView.findViewById(recource_id);

        if (currentText == null) {
            textView.setVisibility(View.GONE);
        } else {
            switch (textFormatId) {
                /*  text formatting:
                        1 - for authors
                        2 - for publisher   */
                case 1:
                    currentText = currentText.replace("[", "").replace("\"", "").replace("]", "").replace(",", ", ");
                    break;
                case 2:
                    currentText = currentText + ", ";
                    break;
                default:
                    break;
            }
            textView.setText(currentText);
        }
    }

    private class ViewHolder {
    }
}

