package ru.butt.xmltv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import org.xmlpull.v1.XmlPullParserException;
import ru.butt.xmltv.model.ChannelList;
import ru.butt.xmltv.model.XMLTVParser;
import ru.butt.xmltv.web.DownloadFileTask;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class XMLTVActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final Button button = (Button) findViewById(R.id.Button01);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DownloadFileTask task = new DownloadFileTask(XMLTVActivity.this);
                task.execute("http://www.teleguide.info/download/new3/xmltv.xml.gz");
                try {
					File file = task.get();
					if (file != null){
						GZIPInputStream stream = new GZIPInputStream(new FileInputStream(file));
						XMLTVParser.parse(getContentResolver(),stream);

					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}