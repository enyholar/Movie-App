package com.behruz.magmovie.ui.activity;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.behruz.magmovie.R;
import com.behruz.magmovie.utils.Constant;
import com.behruz.magmovie.utils.GridMarginDecoration;
import com.behruz.magmovie.utils.StringUtils;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.Media;
import com.uwetrottmann.tmdb2.entities.MediaResultsPage;
import com.uwetrottmann.tmdb2.services.SearchService;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;


public class SearchActivity extends AppCompatActivity {

    private static final String TAG = SearchActivity.class.getSimpleName();
    private String charSequence;
//    @BindDrawable(R.drawable.background_reel)
//    Drawable placeholderImage;
    Toolbar mToolbar;
    RecyclerView mRecyclerView;
    SearchView mSearchView;

    private SearchAdapter mAdapter;
    private GridLayoutManager gridLayoutManager;
    private AVLoadingIndicatorView progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setupActionBar();
        initView();
        setupSearchView();

        List<Media> searchItems = new ArrayList<>();
        mAdapter = new SearchAdapter(searchItems);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mRecyclerView.addItemDecoration(new GridMarginDecoration(getApplicationContext(), 1, 1, 1, 1));
        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.search_recycler_view);
        progressBar = (AVLoadingIndicatorView) findViewById(R.id.progreesbar_home_fragment);
    }

    private void setupSearchView() {
        mSearchView = findViewById(R.id.search_view);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setIconified(false);
        mSearchView.setQueryHint(getString(R.string.search_hint));
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() >= 2) {
                    mAdapter.getFilter(query);
                    return true;
                }
              //  mSearchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return false;
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                finish();
                return true;
            }
        });
    }

    private void setupActionBar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    class SearchAdapter extends RecyclerView.Adapter<SearchHolder>  {

        private List<Media> mMultiSearchItems;

        public SearchAdapter(List<Media> multiMultiSearchItems) {
            mMultiSearchItems = multiMultiSearchItems;
        }

        @Override
        public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.item_search_update, parent, false);
            return new SearchHolder(v);
        }

        @Override
        public void onBindViewHolder(SearchHolder holder, int position) {
            Media item = mMultiSearchItems.get(position);
            holder.bindItem(item);
        }

        @Override
        public int getItemCount() {
            return (mMultiSearchItems != null) ? mMultiSearchItems.size() : 0;
        }


        @SuppressLint("StaticFieldLeak")
        public void getFilter(String q) {
//            return new Filter() {
//                @SuppressLint("StaticFieldLeak")
//                @Override
//                protected FilterResults performFiltering(CharSequence charSequence) {
//                    final FilterResults results = new FilterResults();
//                    String lang = PrefUtils.getFormatLocale(SearchActivity.this);
//                    SearchService service = ServiceGenerator.createService(SearchService.class);
//                    Call<MultiSearch> call = service.multiSearch(charSequence.toString(), ServiceGenerator.API_KEY, lang, "1");
//                    call.enqueue(new Callback<MultiSearch>() {
//                        @Override
//                        public void onResponse(Call<MultiSearch> call, Response<MultiSearch> response) {
//                            if (response.isSuccessful()) {
//                                List<MultiSearch.MultiSearchItem> movies = response.body().getMultiSearchItems();
//                                results.values = movies;
//                                results.count = movies != null ? movies.size() : 0;
//                                mMultiSearchItems.clear();
//                                if (movies != null) {
//                                    mMultiSearchItems.addAll(movies);
//                                    notifyDataSetChanged();
//                                }
//                            } else {
//                                Log.i(TAG, "Error: " + response.code());
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<MultiSearch> call, Throwable t) {
//                            Log.i(TAG, "Error: " + t.getMessage());
//                        }
//                    });


                    new AsyncTask<Void, Void, Void>() {


                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            progressBar.setVisibility(View.VISIBLE);
                         //   updateProgressBarSeries(true);
                        }

                        @Override
                        protected Void doInBackground(final Void... unused) {
                            Tmdb tmdb = new Tmdb(Constant.API_KEYS);
                            SearchService moviesServicess = tmdb.searchService();
// Call any of the available endpoints
                            try {
                                Response<MediaResultsPage> response = moviesServicess.multi(q, 1,"en-US",true,"").execute();
                                if (response.isSuccessful()) {
                                    List<Media> movies = response.body().results;
                                   // results.values = movies;
                                  //  results.count = movies != null ? movies.size() : 0;
                                    mMultiSearchItems.clear();
                                    if (movies != null) {
                                        mMultiSearchItems.addAll(movies);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                } else {
                                    System.out.println(" is awesome!");
                                }
                            } catch (Exception e) {

                                System.out.println(e.toString() + " is awesome!");
                                // see execute() javadoc
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            updateProgressBarSeries(false);
                            System.out.println( " is awesome!");
                        }
                    }.execute();

                 //   return results;
                }

//                @Override
//                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                    if (filterResults.values != null) {
//                        mMultiSearchItems.addAll((Collection<? extends Media>) filterResults.values);
//                        mAdapter.notifyDataSetChanged();
//                    }
//                    progressBar.setVisibility(View.GONE);
//                 //   updateProgressBarSeries(false);
//
//                }
//            };
 //       }
    }

    public void updateProgressBarSeries(boolean visibility) {
        progressBar.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    public class SearchHolder extends RecyclerView.ViewHolder {

        private final String TYPE_MOVIE = "movie";
        private final String TYPE_TV = "tv";
        private final String TYPE_PERSON = "person";

        private Context mContext;
        private Media mItem;

        RoundedImageView poster;
        TextView name;
        TextView originalName;
        TextView voteAverage;
        TextView voteCount;

        public SearchHolder(View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.search_poster);
            name = itemView.findViewById(R.id.search_name);
            originalName = itemView.findViewById(R.id.search_original_name);
            progressBar = itemView.findViewById(R.id.progreesbar_home_fragment);
            voteAverage = itemView.findViewById(R.id.search_vote_average);
            voteCount = itemView.findViewById(R.id.search_vote_count);
            mContext = itemView.getContext();
        }

        void bindItem(Media item) {
            mItem = item;
            switch (item.media_type.toString()) {
                case TYPE_MOVIE:
                        Picasso.with(mContext)
                                .load(Constant.IMG_URL + item.movie.poster_path)
                                .placeholder(R.drawable.ic_launcher_background)
                                .fit().centerCrop()
                                .noFade()
                                .error(R.drawable.ic_launcher_background)
                                .into(poster);
                    name.setText(mContext.getString(R.string.search_name, item.movie.title, item.movie.media_type));
                    if (item.movie.release_date != null ){
                   originalName.setText(mContext.getString(R.string.search_original_name, item.movie.original_title, StringUtils.getYear(item.movie.release_date)));
                    }else {
                        originalName.setText( item.movie.original_title);
                    }
//                    originalName.setText(mContext.getString(R.string.search_original_name, item.movie.original_title, StringUtils.getYear(item.movie.release_date)));
                    voteAverage.setText(String.valueOf(item.movie.vote_average));
                    voteCount.setText(String.valueOf(item.movie.vote_count));
                    break;
                case TYPE_TV:

                        Picasso.with(mContext)
                                .load(Constant.IMG_URL + item.tvShow.poster_path)
                                .placeholder(R.drawable.ic_launcher_background)
                                .fit().centerCrop()
                                .noFade()
                                .error(R.drawable.ic_launcher_background)
                                .into(poster);
                    name.setText(mContext.getString(R.string.search_name, item.tvShow.name, item.tvShow.media_type));
                    if (item.tvShow.first_air_date != null ){
                        originalName.setText(mContext.getString(R.string.search_original_name, item.tvShow.original_name, StringUtils.getYear(item.tvShow.first_air_date)));
                    }else {
                        originalName.setText( item.tvShow.original_name);
                    }
//
                    voteAverage.setText(String.valueOf(item.tvShow.vote_average));
                    voteCount.setText(String.valueOf(item.tvShow.vote_count));
                    break;
                case TYPE_PERSON:
                        Picasso.with(mContext)
                                .load(Constant.IMG_URL + item.person.profile_path)
                                .placeholder(R.drawable.ic_launcher_background)
                                .fit().centerCrop()
                                .noFade()
                                .error(R.drawable.ic_launcher_background)
                                .into(poster);
                    name.setText(item.person.name);
                    originalName.setText("");
                    voteAverage.setText("");
                    voteCount.setText("");
                    break;
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startDetailActivity();
                }
            });
        }

        void startDetailActivity() {
            switch (mItem.media_type.toString()) {
                case "movie":
                    Intent intent = new Intent(SearchActivity.this, MovieFullDetailActivity.class);
                    Gson gson = new Gson();
                    String json = gson.toJson(mItem.movie);
                    intent.putExtra("baseMovie", json);
                   startActivity(intent);
                    break;
                case "tv":
                    Intent intents = new Intent(SearchActivity.this, TvFullDetailActivity.class);
                    Gson gsons = new Gson();
                    String jsons = gsons.toJson(mItem.tvShow);
                    intents.putExtra("baseMovie", jsons);
                    startActivity(intents);
                    break;
                case "person":
                    Intent person = PersonDetailsActivity.newIntent(SearchActivity.this, String.valueOf(mItem.person.id));
                    startActivity(person);
                    break;
            }
        }
    }
}
