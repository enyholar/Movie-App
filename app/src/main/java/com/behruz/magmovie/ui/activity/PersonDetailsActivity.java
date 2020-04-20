package com.behruz.magmovie.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.behruz.magmovie.R;
import com.behruz.magmovie.utils.AppUtils;
import com.behruz.magmovie.utils.Constant;
import com.behruz.magmovie.utils.ExpandableTextView;
import com.behruz.magmovie.utils.PreferenUtil.PreferenUtil;
import com.behruz.magmovie.utils.StringUtils;
import com.squareup.picasso.Picasso;
import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.AppendToResponse;
import com.uwetrottmann.tmdb2.entities.Person;
import com.uwetrottmann.tmdb2.enumerations.AppendToResponseItem;
import com.uwetrottmann.tmdb2.services.PeopleService;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class PersonDetailsActivity extends AppCompatActivity {

    private static final String TAG = PersonDetailsActivity.class.getSimpleName();

    private static final String PERSON_ID = "org.asdtm.fas.details.person_id";

    private String mId;
    private Person person;

    @BindDrawable(R.drawable.ic_launcher_background)
    Drawable placeholderImage;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.person_details_photo)
    ImageView photo;
    @BindView(R.id.person_details_name)
    TextView name;
    @BindView(R.id.person_details_birthday)
    TextView birthday;
    @BindView(R.id.person_details_place_of_birth)
    TextView placeOfBirth;
    @BindView(R.id.person_details_deathday)
    TextView deathday;
    @BindView(R.id.person_details_death_layout)
    LinearLayout deathLayout;
    @BindView(R.id.person_details_biography)
    ExpandableTextView biography;
    @BindView(R.id.person_details_imdb_page)
    TextView imdbPage;
    @BindView(R.id.person_details_homepage)
    TextView homepage;
    @BindView(R.id.progressBar)
    AVLoadingIndicatorView progressBar;
    private PreferenUtil preferenUtil;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);
        ButterKnife.bind(this);
        preferenUtil = PreferenUtil.getInstant(getApplicationContext());
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Profile");
        }

        Bundle args = getIntent().getExtras();
        mId = args.getString(PERSON_ID);

        loadPerson(Integer.parseInt(mId));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }


    private void loadFromInternet(Person person) {
        if (!AppUtils.isNetworkAvailableAndConnected(this)) {
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.network_error), Snackbar.LENGTH_LONG).show();
        }

        if (person.profile_path != null) {
            Picasso.with(PersonDetailsActivity.this)
                    .load(Constant.IMG_URLS + Constant.PROFILE_SIZE_W185 + person.profile_path)
                    .placeholder(placeholderImage)
                    .fit().centerCrop()
                    .error(placeholderImage)
                    .into(photo);
        }


        name.setText(person.name);
        if (person.birthday != null) {
            birthday.setText(StringUtils.formatReleaseDate(person.birthday));
        }

        deathLayout.setVisibility(person.deathday == null || person.deathday.equals("") ? View.GONE : View.VISIBLE);
        if (person.deathday != null) {
            deathday.setText(StringUtils.formatReleaseDate(person.deathday));
        }

        placeOfBirth.setText(person.place_of_birth);
        biography.setText(person.biography);
        imdbPage.setText(String.format(Constant.IMDB_PERSON_URL, person.imdb_id));
        homepage.setText(person.homepage);


    }

    @SuppressLint("StaticFieldLeak")
    public void loadPerson(final int page) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                updateProgressBar(true);
            }

            @Override
            protected Void doInBackground(final Void... unused) {
                Tmdb tmdb = new Tmdb(Constant.API_KEYS);
                PeopleService moviesService = tmdb.personService();
// Call any of the available endpoints
                try {
                    AppendToResponse appendToResponse = new AppendToResponse(AppendToResponseItem.values());
                    Response<Person> response = moviesService.summary(page, "en-US").execute();
                    if (response.isSuccessful()) {
                        person = response.body();
                        updateProgressBar(false);

                    } else {
                        System.out.println(" is awesome!");
                    }
                } catch (Exception e) {
                    updateProgressBar(false);
                    System.out.println(e.toString() + " is awesome!");
                    // see execute() javadoc
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                updateProgressBar(false);
                loadFromInternet(person);
            }
        }.execute();
    }


    public void updateProgressBar(boolean visible) {
        if (progressBar != null) {
            progressBar.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public static Intent newIntent(Context context, String id) {
        Intent intent = new Intent(context, PersonDetailsActivity.class);
        Bundle args = new Bundle();
        args.putString(PERSON_ID, id);
        intent.putExtras(args);
        return intent;
    }

}
