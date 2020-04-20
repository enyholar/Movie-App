package com.behruz.magmovie.ui.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.behruz.magmovie.R;
import com.behruz.magmovie.adapter.MovieGenreListner;
import com.behruz.magmovie.adapter.MovieGenresAdapter;
import com.behruz.magmovie.base.BaseFragment;
import com.behruz.magmovie.base.presenter.Presenter;
import com.behruz.magmovie.internal.di.component.DaggerProjectComponent;
import com.behruz.magmovie.internal.di.module.ProjectModule;
import com.behruz.magmovie.model.GenresModel;
import com.behruz.magmovie.presenter.PresenterView.MovieGenresPresenter;
import com.behruz.magmovie.presenter.PresenterView.MovieGenresView;

import java.util.List;

import javax.inject.Inject;


public class MovieGenresFragment extends BaseFragment implements MovieGenresView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    @Inject
    MovieGenresPresenter presenter;
    private RecyclerView recyclerView;
    private MovieGenresAdapter adapter;


    public MovieGenresFragment() {
        // Required empty public constructor
    }

    public static MovieGenresFragment newInstance(String param1, String param2) {
        MovieGenresFragment fragment = new MovieGenresFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        injectInjector();
    }

    @Override
    protected void injectInjector() {
        DaggerProjectComponent.builder()
                .projectModule(new ProjectModule(this.getActivity()))
                .build()
                .inject(this);
        presenter.setView(this);
        presenter.start();

    }

    @Override
    public void initModels() {

    }

    @Override
    public void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_genres, container, false);
        initViews(view);

        return view;
    }


    @Override
    public void showLineLoading() {

    }

    @Override
    public void hideLineLoading() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void initAdapter(List<GenresModel> genres) {
        adapter = new MovieGenresAdapter(getContext(), genres, new MovieGenreListner() {
            @Override
            public void ItemClick(GenresModel model, int pos) {
                presenter.openGenreDetailScreen(model);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void updateAdapter(List<GenresModel> genresList) {
        adapter.updateDataSet(genresList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public MovieGenresAdapter getAdapter() {
        return adapter;
    }
}
