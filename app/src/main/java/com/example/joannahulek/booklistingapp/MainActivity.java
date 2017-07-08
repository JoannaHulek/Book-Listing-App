package com.example.joannahulek.booklistingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.joannahulek.booklistingapp.R.id.empty_view;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static final String urlPrefix = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final int BOOK_LOADER_ID = 1;
    public static final String ADAPTER_KEY = "adapter";
    private BookAdapter adapter;

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        adapter = (BookAdapter) bundle.get(ADAPTER_KEY);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putSerializable(ADAPTER_KEY, adapter);
        super.onSaveInstanceState(bundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkInternetConnection();
        deriveListView(savedInstanceState);
        enrichInteractions();
    }

    private void enrichInteractions() {
        Button searchButton = (Button) findViewById(R.id.search_button);
        final LoaderManager.LoaderCallbacks<List<Book>> loader = this;
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportLoaderManager().restartLoader(BOOK_LOADER_ID, null, loader);
            }
        });
    }

    private void deriveListView(Bundle savedInstanceState) {
        final TextView mEmptyStateTextView = (TextView) findViewById(empty_view);
        final ListView bookListView = (ListView) findViewById(R.id.book_list_view);
        bookListView.setEmptyView(mEmptyStateTextView);
        if (savedInstanceState != null && savedInstanceState.containsKey(ADAPTER_KEY))
            adapter = (BookAdapter) savedInstanceState.get(ADAPTER_KEY);
        else if (adapter == null)
            adapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(adapter);
    }

    private void checkInternetConnection() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        TextView mEmptyStateTextView = (TextView) findViewById(empty_view);
        if (networkInfo != null && networkInfo.isConnected()) {
            mEmptyStateTextView.setText(R.string.no_books_found);
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        EditText keywordEditText = (EditText) findViewById(R.id.keyword_edit_text);
        String keyword = keywordEditText.getText().toString();
        String url = urlPrefix + keyword.replace(" ", "+");
        return new BookLoader(this, url);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        adapter.clear();
        if (data != null && !data.isEmpty())
            adapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        adapter.clear();
    }
}