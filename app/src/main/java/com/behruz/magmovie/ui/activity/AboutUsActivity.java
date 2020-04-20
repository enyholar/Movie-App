package com.behruz.magmovie.ui.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.behruz.magmovie.R;
import com.behruz.magmovie.utils.Method;
import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Created by admin on 29-06-2017.
 */

public class AboutUsActivity extends AppCompatActivity {

    public Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        //Method.forceRTLIfSupported(getWindow(), AboutUsActivity.this);

        toolbar = findViewById(R.id.toolbar_profile);
        toolbar.setTitle(getResources().getString(R.string.about_us));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        TextView textView_app_name = findViewById(R.id.textView_app_name_about_us);
        TextView textView_app_version = findViewById(R.id.textView_app_version_about_us);
        TextView textView_app_author = findViewById(R.id.textView_app_author_about_us);
        TextView textView_app_contact = findViewById(R.id.textView_app_contact_about_us);
        TextView textView_app_email = findViewById(R.id.textView_app_email_about_us);
        TextView textView_app_website = findViewById(R.id.textView_app_website_about_us);
        TextView textView_app_description = findViewById(R.id.textView_app_description_about_us);
        TextView text_title_version=findViewById(R.id.text_title_version);
        TextView text_title_com=findViewById(R.id.text_title_com);
        TextView text_title_email=findViewById(R.id.text_title_email);
        TextView text_title_web=findViewById(R.id.text_title_web);
        TextView text_title_contact=findViewById(R.id.text_title_contact);
        TextView text_title_about=findViewById(R.id.text_title_about);

        ImageView app_logo = findViewById(R.id.app_logo_about_us);
        textView_app_website.setMovementMethod(LinkMovementMethod.getInstance());
     //   if (Constant_Api.aboutUsList != null) {

           // textView_app_name.setText(Constant_Api.aboutUsList.getApp_name());

//            Picasso.with(getApplication()).load(Constant_Api.image + Constant_Api.aboutUsList.getApp_logo())
//                    .into(app_logo);

            textView_app_version.setText(getVersionName());
          //  textView_app_author.setText(Constant_Api.aboutUsList.getApp_author());
          //  textView_app_contact.setText(Constant_Api.aboutUsList.getApp_contact());
           // textView_app_email.setText(Constant_Api.aboutUsList.getApp_email());
           // textView_app_website.setText(Constant_Api.aboutUsList.getApp_website());
           // textView_app_description.setText(Html.fromHtml(Constant_Api.aboutUsList.getApp_description()));
  //      }

    }

    private String getVersionName() {
        String versionName;
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
        //    Log.e(TAG, "Get version name error: ", e);
            versionName = "666";
        }
        return versionName;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
