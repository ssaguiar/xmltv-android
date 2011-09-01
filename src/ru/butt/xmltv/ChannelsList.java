package ru.butt.xmltv;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

/**
 * Created by IntelliJ IDEA.
 * User: butt
 * Date: 16.01.11
 * Time: 20:53
 */
public class ChannelsList extends ListActivity {
    private static final String TAG = "ChannelsList";

    private static final String[] PROJECTION = new String[] {
        XmlTvDefs.ChannelColumns._ID, // 0
        XmlTvDefs.ChannelColumns.NAME, // 1
    };

    private static final int COLUMN_INDEX_TITLE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setDefaultKeyMode(DEFAULT_KEYS_SHORTCUT);

        // If no data was given in the intent (because we were started
        // as a MAIN activity), then use our default content provider.
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(XmlTvDefs.ChannelColumns.CONTENT_URI);
        }

        // Inform the list we provide context menus for items
//        getListView().setOnCreateContextMenuListener(this);

        // Perform a managed query. The Activity will handle closing and requerying the cursor
        // when needed.
        Cursor cursor = managedQuery(getIntent().getData(), PROJECTION, null, null,
                                        XmlTvDefs.ChannelColumns.DEFAULT_SORT_ORDER);

        // Used to map notes entries from the database to views
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.channelslist_item, cursor,
                new String[] { XmlTvDefs.ChannelColumns.NAME }, new int[] { android.R.id.text1 });
        setListAdapter(adapter);
    }
}