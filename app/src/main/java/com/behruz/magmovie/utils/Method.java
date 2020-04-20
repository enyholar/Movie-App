package com.behruz.magmovie.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by admin on 08-08-2017.
 */

public class Method {

    public static Activity activity;
    public static boolean share = false, onBackPress = false, allowPermitionExternalStorage = false,slider=false,loginBack=false;
    public static Bitmap mbitmap;
    private static File file;
    private String filename;
   // private DatabaseHandler db;

    public static Typeface scriptable;

    public SharedPreferences pref;
    public SharedPreferences.Editor editor;
    private final String myPreference = "login";
    public String pref_login = "pref_login";
    private String firstTime = "firstTime";
    public String profileId = "profileId";
    public String userEmail = "userEmail";
    public String userPassword = "userPassword";
    public String userName = "userName";

    public Method(Activity activity) {
        this.activity = activity;
        scriptable = Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_bold.ttf");
        pref = activity.getSharedPreferences(myPreference, 0); // 0 - for private mode
        editor = pref.edit();
       // db = new DatabaseHandler(activity);
    }

    public void login() {
        if (!pref.getBoolean(firstTime, false)) {
            editor.putBoolean(pref_login, false);
            editor.putBoolean(firstTime, true);
            editor.commit();
        }
    }

    //rtl
//    public static void forceRTLIfSupported(Window window, Activity activity) {
//        if (activity.getResources().getString(R.string.isRTL).equals("true")) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                window.getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
//            }
//        }
//    }


    //network check
    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //Database add
//    public void addData(DatabaseHandler db, int position) {
//        db.addDetail(new ScdList(Constant_Api.scdLists.get(position).getId(),
//                Constant_Api.scdLists.get(position).getBook_title(),
//                Constant_Api.scdLists.get(position).getBook_description(),
//                Constant_Api.scdLists.get(position).getBook_cover_img(),
//                Constant_Api.scdLists.get(position).getBook_bg_img(),
//                Constant_Api.scdLists.get(position).getBook_file_type(),
//                Constant_Api.scdLists.get(position).getBook_file_url(),
//                Constant_Api.scdLists.get(position).getTotal_rate(),
//                Constant_Api.scdLists.get(position).getRate_avg(),
//                Constant_Api.scdLists.get(position).getBook_views(),
//                Constant_Api.scdLists.get(position).getAuthor_name()));
//        Toast.makeText(activity, activity.getResources().getString(R.string.add_to_favourite), Toast.LENGTH_SHORT).show();
//    }


    public int getScreenWidth() {
        int columnWidth;
        WindowManager wm = (WindowManager) activity
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();

        point.x = display.getWidth();
        point.y = display.getHeight();

        columnWidth = point.x;
        return columnWidth;
    }

     /*<---------------------- share & downlode and methodes ---------------------->*/

    public static void share_save(String image, String bookName, String bookAuthor, String description, String bookLink) {
        new DownloadImageTask().execute(image, bookName, bookAuthor, description, bookLink);
    }

    public static class DownloadImageTask extends AsyncTask<String, String, String> {

        String bookName, bookAuthor, bookDescription, bookLink;

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                bookName = params[1];
                bookAuthor = params[2];
                bookDescription = params[3];
                bookLink = params[4];
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                mbitmap = BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                // Log exception
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            if (share) {
                ShareImage(mbitmap, bookName, bookAuthor, bookDescription, bookLink);
                share = false;
            }
            super.onPostExecute(s);
        }
    }

    private static void ShareImage(Bitmap finalBitmap, String bookName, String bookAuthor, String description, String bookLink) {

        String root = activity.getExternalCacheDir().getAbsolutePath();
        String fname = "Image_share" + ".jpg";
        file = new File(root, fname);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Uri contentUri = Uri.fromFile(file);
        Log.d("url", String.valueOf(contentUri));

        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.setData(contentUri);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            shareIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(bookName)
                    + "\n" + "\n" + Html.fromHtml(bookAuthor)
                    + "\n" + "\n" + Html.fromHtml(description)
                    + "\n" + "\n" + Html.fromHtml(bookLink));
            activity.startActivity(Intent.createChooser(shareIntent, "Choose an app"));
        }
    }




}
