package ru.butt.xmltv.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.util.Log;
import ru.butt.xmltv.R;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Environment;

public class DownloadFileTask extends AsyncTask<String, Integer, File> {

	private static final int MAX_BUFFER_SIZE = 1024; // 1kb
	public static final int DOWNLOADING = 0;
	public static final int COMPLETE = 1;
    private static final String TAG = "DownloadFileTask";

    private ProgressDialog progressDialog;

	private Context context;

	public DownloadFileTask(Context context) {
		super();
		this.context = context;
	}

	@Override
	protected File doInBackground(String... arg0) {
		try {
            Log.d(TAG,"Try down load file");
			File file = new File(Environment.getExternalStorageDirectory(),
					"data/xmltv/nameofthefile.ext");
			if (file.exists()) {
				if ((System.currentTimeMillis() - file.lastModified()) > (1000 * 60 * 60 * 7)) {
					file.delete();
				} else {
                    Log.d(TAG,"Use from cache");
					return file;
				}

			} else {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();

            Log.d(TAG,"Download from net");
			URL url = new URL(arg0[0]);
			URLConnection conexion = url.openConnection();
			conexion.connect();

			// this will be useful so that you can show a typical 0-100%
			// progress bar
			int lenghtOfFile = conexion.getContentLength();
			progressDialog.setMax(lenghtOfFile);
			// download the file
			InputStream input = new BufferedInputStream(url.openStream());

			OutputStream output = new FileOutputStream(file);

			byte data[] = new byte[MAX_BUFFER_SIZE];
			int total = 0;
			int count;

			while ((count = input.read(data)) != -1) {
				total += count;
				// publishing the progress....
				publishProgress(total, lenghtOfFile);
				output.write(data, 0, count);
			}

			// TODO close in finally block
			output.flush();
			output.close();
			input.close();
			return file;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPreExecute() {
		progressDialog = new ProgressDialog(context);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setMessage(context.getResources().getString(R.string.loadingLabel));
		progressDialog.setCancelable(false);
		progressDialog.show();
	}

	@Override
	protected void onProgressUpdate(Integer... changed) {
		
		progressDialog.setProgress(changed[0]);
	}

	@Override
	protected void onPostExecute(File result) {
		if (result != null) {
			progressDialog.dismiss();
		} else {
			progressDialog.dismiss();
			AlertDialog alertDialog;
			alertDialog = new AlertDialog.Builder(context).create();
			alertDialog.setTitle(R.string.infoLabel);
			alertDialog.setMessage(context.getString(R.string.loadErrorLabel));
			alertDialog.setButton(context.getString(R.string.closeLabel),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dlg, int sum) {
							// do nothing, close
						}
					});
			alertDialog.show();
		}
	}

	public boolean isOnline() {
		try {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			return cm.getActiveNetworkInfo().isConnectedOrConnecting();
		} catch (Exception e) {
			return false;
		}
	}

}
