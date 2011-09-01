package ru.butt.xmltv;

import java.util.HashMap;

import android.content.*;
import android.database.SQLException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.text.TextUtils;
import ru.butt.xmltv.XmlTvDefs.*;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class XMLTVProvider extends ContentProvider {
    private static final String DATABASE_NAME = "xmltv.db";
    private static final int DATABASE_VERSION = 5;
    private static final String TAG = "XMLTVProvider";
    private static final String TABLE_CATEGORIES_NAME = "category";
    private static final String TABLE_CHANNELS_NAME = "channels";
    private static final String TABLE_PROGRAMME_NAME = "programme";

    private static final String CREATE_TABLE_CHANNELS = "create table " + TABLE_CHANNELS_NAME + " (" +
            ChannelColumns._ID+" integer primary key, " +
            ChannelColumns.NAME+" text not null, " +
            ChannelColumns.ICON+" text);";

    private static final String CREATE_TABLE_CATEGORIES = "create table " + TABLE_CATEGORIES_NAME + " ("
            + CategoryColumns._ID + " integer primary key autoincrement,"
            + CategoryColumns.TITLE + " text not null)";
    private static final String CREATE_TABLE_PROGRAMMES = "create table " + TABLE_PROGRAMME_NAME + " ("
            + ProgrammeColumns._ID + " integer primary key autoincrement, "
            + ProgrammeColumns.START + " timestamp not null, "
            + ProgrammeColumns.STOP + " timestamp not null, "
            + ProgrammeColumns.TITLE + " text not null, "
            + ProgrammeColumns.DESC + " text, "
            + ProgrammeColumns.CHANNEL_ID + " integer not null, "
            + ProgrammeColumns.CATEGORY_ID + " integer)";
    private static final int CATEGORIES = 1;
    private static final int CATEGORY_ID = 2;
    private static final int CHANNELS = 3;
    private static final int CHANNEL_ID = 4;
    private static final int PROGRAMMES = 5;
    private static final int PROGRAMME_ID = 6;
    private static UriMatcher sUriMatcher;
    private static HashMap<String, String> channelProjectionMap;
    private static HashMap<String, String> categoryProjectionMap;
    private static HashMap<String, String> programmeProjectionMap;
    private DatabaseHelper mOpenHelper;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case CATEGORIES:
                return CategoryColumns.CONTENT_TYPE;
            case CATEGORY_ID:
                return CategoryColumns.CONTENT_ITEM_TYPE;
            case CHANNELS:
                return ChannelColumns.CONTENT_TYPE;
            case CHANNEL_ID:
                return ChannelColumns.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // Validate the requested uri
        if (sUriMatcher.match(uri) == CATEGORIES) {
            return insertCategory(values);
        } else if (sUriMatcher.match(uri) == CHANNELS) {
            return insertChannel(values);
        } if (sUriMatcher.match(uri) == PROGRAMMES) {
            return insertProgramme(values);
        }

        throw new SQLException("Failed to insert row into " + uri);
    }

    private Uri insertProgramme(ContentValues initialValues) {
        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long rowId = db.insert(TABLE_PROGRAMME_NAME, ProgrammeColumns.TITLE, values);
        if (rowId > 0) {
            Uri noteUri = ContentUris.withAppendedId(ProgrammeColumns.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }

        throw new SQLException("Failed to insert row into " + ProgrammeColumns.CONTENT_URI);
    }

    private Uri insertChannel(ContentValues initialValues) {
        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long rowId = db.insert(TABLE_CHANNELS_NAME, ChannelColumns.NAME, values);
        if (rowId > 0) {
            Uri noteUri = ContentUris.withAppendedId(ChannelColumns.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }

        throw new SQLException("Failed to insert row into " + ChannelColumns.CONTENT_URI);
    }

    private Uri insertCategory(ContentValues initialValues) {
        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        // Make sure that the fields are all set                                                                                                                                    
/*
        if (values.containsKey(CategoryColumns.TITLE) == false) {
            values.put(CategoryColumns.TITLE, now);
        }

        if (values.containsKey(CategoryColumns.MODIFIED_DATE) == false) {
            values.put(CategoryColumns.MODIFIED_DATE, now);
        }

        if (values.containsKey(CategoryColumns.TITLE) == false) {
            values.put(NoteColumns.TITLE, Resources.getSystem().getString(android.R.string.untitled));
        }

        if (values.containsKey(CategoryColumns.NOTE) == false) {
            values.put(CategoryColumns.NOTE, "");
        }
*/

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        long rowId = db.insert(TABLE_CATEGORIES_NAME, CategoryColumns.TITLE, values);
        if (rowId > 0) {
            Uri noteUri = ContentUris.withAppendedId(CategoryColumns.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }

        throw new SQLException("Failed to insert row into " + CategoryColumns.CONTENT_URI);
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (sUriMatcher.match(uri)) {
            case CATEGORIES:
                return queryCategories(uri, projection, selection, selectionArgs, sortOrder);
            case CATEGORY_ID:
                return queryCategory(uri, projection, selection, selectionArgs, sortOrder);
            case CHANNELS:
                return queryChannels(uri, projection, selection, selectionArgs, sortOrder);
            case CHANNEL_ID:
                return queryChannel(uri, projection, selection, selectionArgs, sortOrder);
            case PROGRAMMES:
                return queryProgrammes(uri, projection, selection, selectionArgs, sortOrder);
            case PROGRAMME_ID:
                return queryProgramme(uri, projection, selection, selectionArgs, sortOrder);
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    private Cursor queryProgramme(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    private Cursor queryProgrammes(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    private Cursor queryChannel(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    private Cursor queryChannels(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    private Cursor queryCategory(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_CATEGORIES_NAME);
        qb.appendWhere(CategoryColumns._ID + "=" + uri.getPathSegments().get(1));
        qb.setProjectionMap(categoryProjectionMap);

        String orderBy;
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = CategoryColumns.DEFAULT_SORT_ORDER;
        } else {
            orderBy = sortOrder;
        }

        // Get the database and run the query
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);

        // Tell the cursor what uri to watch, so it knows when its source data changes
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    private Cursor queryCategories(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_CATEGORIES_NAME);
        qb.setProjectionMap(categoryProjectionMap);

        String orderBy;
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = CategoryColumns.DEFAULT_SORT_ORDER;
        } else {
            orderBy = sortOrder;
        }

        // Get the database and run the query
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);

        // Tell the cursor what uri to watch, so it knows when its source data changes
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO Auto-generated method stub
        return 0;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_CHANNELS);
            db.execSQL(CREATE_TABLE_CATEGORIES);
            db.execSQL(CREATE_TABLE_PROGRAMMES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHANNELS_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRAMME_NAME);
            onCreate(db);
        }

    }

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(XmlTvDefs.AUTHORITY, "category", CATEGORIES);
        sUriMatcher.addURI(XmlTvDefs.AUTHORITY, "category/#", CATEGORY_ID);
        sUriMatcher.addURI(XmlTvDefs.AUTHORITY, "channel", CHANNELS);
        sUriMatcher.addURI(XmlTvDefs.AUTHORITY, "channel/#", CHANNEL_ID);
        sUriMatcher.addURI(XmlTvDefs.AUTHORITY, "programme", PROGRAMMES);
        sUriMatcher.addURI(XmlTvDefs.AUTHORITY, "programme/#", PROGRAMME_ID);
        //sUriMatcher.addURI(XmlTvDefs.AUTHORITY, "live_folders/notes", LIVE_FOLDER_NOTES);

        categoryProjectionMap = new HashMap<String, String>();
        categoryProjectionMap.put(CategoryColumns._ID, CategoryColumns._ID);
        categoryProjectionMap.put(CategoryColumns.TITLE, CategoryColumns.TITLE);

        channelProjectionMap = new HashMap<String, String>();
        channelProjectionMap.put(ChannelColumns._ID, ChannelColumns._ID);
        channelProjectionMap.put(ChannelColumns.NAME, ChannelColumns.NAME);
        channelProjectionMap.put(ChannelColumns.ICON, ChannelColumns.ICON);

        programmeProjectionMap = new HashMap<String, String>();
        programmeProjectionMap.put(ProgrammeColumns._ID, ProgrammeColumns._ID);
        programmeProjectionMap.put(ProgrammeColumns.TITLE, ProgrammeColumns.TITLE);
        programmeProjectionMap.put(ProgrammeColumns.START, ProgrammeColumns.START);
        programmeProjectionMap.put(ProgrammeColumns.STOP, ProgrammeColumns.STOP);
        programmeProjectionMap.put(ProgrammeColumns.CHANNEL_ID, ProgrammeColumns.CHANNEL_ID);
        programmeProjectionMap.put(ProgrammeColumns.CATEGORY_ID, ProgrammeColumns.CATEGORY_ID);

        // Support for Live Folders.
//        sLiveFolderProjectionMap = new HashMap<String, String>();                                                                                                                   
        //      sLiveFolderProjectionMap.put(LiveFolders._ID, NoteColumns._ID + " AS " +
        //            LiveFolders._ID);
        //  sLiveFolderProjectionMap.put(LiveFolders.NAME, NoteColumns.TITLE + " AS " +
        //        LiveFolders.NAME);                                                                                                                                                  
        // Add more columns here for more robust Live Folders.                                                                                                                      
    }
}
