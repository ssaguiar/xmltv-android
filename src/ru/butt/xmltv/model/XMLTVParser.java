package ru.butt.xmltv.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import ru.butt.xmltv.XmlTvDefs;

public class XMLTVParser {
    private static String XML = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
            "<!DOCTYPE tv SYSTEM \"http://www.teleguide.info/xmltv.dtd\">\n" +
            "<tv generator-info-name=\"TVH_W/2.0\" generator-info-url=\"http://www.teleguide.info/\">\n" +
            "<channel id=\"1\">\n" +
            "<display-name lang=\"ru\">Первый канал</display-name>\n" +
            "<icon src=\"http://www.teleguide.info/img/channel/1.gif\" />\n" +
            "</channel>\n" +
            "<channel id=\"2\">\n" +
            "<display-name lang=\"ru\">Россия 1</display-name>\n" +
            "<icon src=\"http://www.teleguide.info/img/channel/2.gif\" />\n" +
            "</channel>\n" +
            "<channel id=\"3\">\n" +
            "<display-name lang=\"ru\">ТВЦ</display-name>\n" +
            "<icon src=\"http://www.teleguide.info/img/channel/3.gif\" />\n" +
            "</channel>\n" +
            "<channel id=\"4\">\n" +
            "<display-name lang=\"ru\">НТВ</display-name>\n" +
            "<icon src=\"http://www.teleguide.info/img/channel/4.gif\" />\n" +
            "</channel>\n" +
            "<channel id=\"5\">\n" +
            "<display-name lang=\"ru\">Россия Культура</display-name>\n" +
            "<icon src=\"http://www.teleguide.info/img/channel/5.gif\" />\n" +
            "</channel>\n" +
            "<channel id=\"235\">\n" +
            "<display-name lang=\"ru\">Россия 2 (Спорт)</display-name>\n" +
            "<icon src=\"http://www.teleguide.info/img/channel/235.gif\" />\n" +
            "</channel>\n" +
            "<channel id=\"7\">\n" +
            "<display-name lang=\"ru\">3 канал+</display-name>\n" +
            "<icon src=\"http://www.teleguide.info/img/channel/7.gif\" />\n" +
            "</channel>\n" +
            "<channel id=\"101\">\n" +
            "<display-name lang=\"ru\">ТНТ</display-name>\n" +
            "<icon src=\"http://www.teleguide.info/img/channel/101.gif\" />\n" +
            "</channel>\n" +
            "<channel id=\"102\">\n" +
            "<display-name lang=\"ru\">Домашний</display-name>\n" +
            "<icon src=\"http://www.teleguide.info/img/channel/102.gif\" />\n" +
            "</channel>\n" +
            "<channel id=\"103\">\n" +
            "<display-name lang=\"ru\">РЕН ТВ</display-name>\n" +
            "<icon src=\"http://www.teleguide.info/img/channel/103.gif\" />\n" +
            "</channel>\n" +
            "<programme start=\"20110112023500 +0300\" stop=\"20110112030000 +0300\" channel=\"1\">\n" +
            "<title lang=\"ru\">Официантка</title>\n" +
            "<desc lang=\"ru\">Красавица-официантка Дженна Хантерсон работает в закусочной захолустного городка. Разнося по столикам заказы, она придумывает новые рецепты пирогов. Ее заветная мечта - выиграть соревнования по кулинарному искусству и, воспользовавшись призовыми деньгами, развестись со своим нелюбимым патологически ревнивым мужем Эрлом. Но обо всем приходится забыть, когда выясняется, что Дженна беременна. Смирившись со своей беспросветной жизнью, идет на прием к гинекологу Джиму Поматтеру и… теряет голову от симпатичного доктора …. В ролях: Энди Гриффит, Джереми Систо, Кери Расселл, Шерил Хайнс, Натан Филлион, Дэрби Стэнчфилд, Синди Драммонд, Хайди Сулцман.  Режиссер: Эдриенн Шелли. </desc>\n" +
            "<category lang=\"ru\">Художественный фильм</category>\n" +
            "</programme>\n" +
            "<programme start=\"20110112030000 +0300\" stop=\"20110112030500 +0300\" channel=\"1\">\n" +
            "<title lang=\"ru\">Новости</title>\n" +
            "</programme>\n" +
            "<programme start=\"20110112030500 +0300\" stop=\"20110112050000 +0300\" channel=\"1\">\n" +
            "<title lang=\"ru\">Официантка</title>\n" +
            "<desc lang=\"ru\">Красавица-официантка Дженна Хантерсон работает в закусочной захолустного городка. Разнося по столикам заказы, она придумывает новые рецепты пирогов. Ее заветная мечта - выиграть соревнования по кулинарному искусству и, воспользовавшись призовыми деньгами, развестись со своим нелюбимым патологически ревнивым мужем Эрлом. Но обо всем приходится забыть, когда выясняется, что Дженна беременна. Смирившись со своей беспросветной жизнью, идет на прием к гинекологу Джиму Поматтеру и… теряет голову от симпатичного доктора …. В ролях: Энди Гриффит, Джереми Систо, Кери Расселл, Шерил Хайнс, Натан Филлион, Дэрби Стэнчфилд, Синди Драммонд, Хайди Сулцман.  Режиссер: Эдриенн Шелли. </desc>\n" +
            "<category lang=\"ru\">Художественный фильм</category>\n" +
            "</programme>\n" +
            "<programme start=\"20110112050000 +0300\" stop=\"20110112050500 +0300\" channel=\"1\">\n" +
            "<title lang=\"ru\">Новости</title>\n" +
            "</programme>" +
            "</tv>";
    private static final String RU = "ru";
    private static final String TAG = "XMLTVParser";

    public static void parse(ContentResolver contentResolver, InputStream stream) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        Log.d(TAG,"Start parsing");
        xpp.setInput(new InputStreamReader(stream));
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_DOCUMENT) {
            } else if (eventType == XmlPullParser.END_DOCUMENT) {
            } else if (eventType == XmlPullParser.START_TAG) {
                String name = xpp.getName();
                if (name.equals("channel")) {
                    createChannel(contentResolver, xpp);
                } else if (name.equals("programme")) {
                    createProgramme(contentResolver, xpp);
                }
            } else if (eventType == XmlPullParser.END_TAG) {
            } else if (eventType == XmlPullParser.TEXT) {
            }
            eventType = xpp.next();
            Log.d(TAG,"parse "+xpp.getLineNumber()+" line");
        }
        Log.d(TAG,"Finish parsing");
    }

    private static int getCategoryId(ContentResolver contentResolver, String name) {
        Cursor query = contentResolver.query(XmlTvDefs.CategoryColumns.CONTENT_URI, new String[]{XmlTvDefs.CategoryColumns._ID},
                XmlTvDefs.CategoryColumns.TITLE + " = ?", new String[]{name},
                null);
        try{
        if (query.moveToFirst()){
            return query.getInt(0);
        } else {
            ContentValues contentValues1 = new ContentValues();
            contentValues1.put(XmlTvDefs.CategoryColumns.TITLE,name);
//            Uri insert = contentResolver.insert(XmlTvDefs.CategoryColumns.CONTENT_URI, contentValues1);
//            return Integer.parseInt(insert.getLastPathSegment());
            return 1;
        }
        }finally {
            query.close();
        }
    }


    private static void createProgramme(ContentResolver contentResolver, XmlPullParser xpp) throws IOException, XmlPullParserException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(XmlTvDefs.ProgrammeColumns.CHANNEL_ID, Integer.parseInt(xpp.getAttributeValue(null, "channel")));
        contentValues.put(XmlTvDefs.ProgrammeColumns.START, getTime(xpp.getAttributeValue(null, "start")).getTime());
        contentValues.put(XmlTvDefs.ProgrammeColumns.STOP, getTime(xpp.getAttributeValue(null, "stop")).getTime());
        int eventType = xpp.next();
        while (eventType != XmlPullParser.END_TAG && !"programme".equals(xpp.getName())) {
            if (eventType == XmlPullParser.START_TAG) {
                if ("title".equals(xpp.getName())) {
                    contentValues.put(XmlTvDefs.ProgrammeColumns.TITLE, xpp.nextText());
                } else if ("desc".equals(xpp.getName())) {
                    contentValues.put(XmlTvDefs.ProgrammeColumns.DESC, xpp.nextText());
                } else if ("category".equals(xpp.getName())) {
                    contentValues.put(XmlTvDefs.ProgrammeColumns.CATEGORY_ID, getCategoryId(contentResolver,xpp.nextText()));
                }
            }
            eventType = xpp.next();
        }

//        contentResolver.insert(XmlTvDefs.ProgrammeColumns.CONTENT_URI, contentValues);

    }

    private static Date getTime(String start) {
        Calendar cal = new GregorianCalendar(Integer.parseInt(start.substring(0, 4)),
                Integer.parseInt(start.substring(4, 6)) - 1,
                Integer.parseInt(start.substring(6, 8)),
                Integer.parseInt(start.substring(8, 10)),
                Integer.parseInt(start.substring(10, 12)),
                Integer.parseInt(start.substring(12, 14)));
//        cal.setTimeZone(new ZoneInfo()); //TODO need include time zone info
        return cal.getTime();
    }

    private static void createChannel(ContentResolver contentResolver, XmlPullParser xpp) throws XmlPullParserException, IOException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(XmlTvDefs.ChannelColumns._ID,xpp.getAttributeValue(null, "id"));
        int eventType = xpp.next();
        while (eventType != XmlPullParser.END_TAG && !"channel".equals(xpp.getName())) {
            if (eventType == XmlPullParser.START_TAG) {
                if ("display-name".equals(xpp.getName())) {
                    contentValues.put(XmlTvDefs.ChannelColumns.NAME, xpp.nextText());
                } else if ("icon".equals(xpp.getName())) {
                    contentValues.put(XmlTvDefs.ChannelColumns.ICON, xpp.getAttributeValue(null, "src"));
                }
            }
            eventType = xpp.next();
        }

/*
        try{
            contentResolver.insert(XmlTvDefs.ChannelColumns.CONTENT_URI, contentValues);
        } catch (SQLException e){
            Log.w(TAG,"cant insert channel - primary key");
        }
*/
    }

}
