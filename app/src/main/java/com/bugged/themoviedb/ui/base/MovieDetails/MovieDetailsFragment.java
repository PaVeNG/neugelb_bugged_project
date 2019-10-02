package com.bugged.themoviedb.ui.base.MovieDetails;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bugged.themoviedb.R;
import com.bugged.themoviedb.data.model.Movie;
import com.bugged.themoviedb.ui.base.BaseFragment;
import com.bugged.themoviedb.util.AlertDialogManager;
import com.bugged.themoviedb.util.ConstantUtils;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsFragment extends BaseFragment implements MovieDetailsView {


    @Order(1)
    @BindView(R.id.tv_movie_name)
    TextView tv_movie_name;

    @Order(2)
    @BindView(R.id.iv_poster)
    ImageView iv_poster;

    @Order(3)
    @BindView(R.id.popularity_value_tv)
    TextView popularity_value_tv;

    @Order(4)
    @BindView(R.id.language_value_tv)
    TextView language_value_tv;

    @Order(5)
    @BindView(R.id.release_date_value_tv)
    TextView release_date_value_tv;

    @Order(6)
    @BindView(R.id.tv_overview)
    TextView tv_overview;

    @Inject
    MovieDetailsPresenter movieDetailsPresenter;

    @Inject
    AlertDialogManager alert;

    private BaseFragment baseFragment;
    private Gson gson;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    ProgressDialog progressDialog;
    Movie movie;


    @Override
    public void initViews(View parentView) {
        baseActivity.activityComponent().inject(this);
        movieDetailsPresenter.attachView(this);
        ButterKnife.bind(Objects.requireNonNull(getActivity()));
        gson = new Gson();
        getActivity().setTitle("Movie Details");
        if(getArguments()!=null){
            Bundle bundle = getArguments();
            movie = movieDetailsPresenter.getMovie(bundle);
            setValues(movie);
        }

    }

    private void setValues(Movie movie) {
        tv_movie_name.setText(movie.getTitle());
        Picasso.with(getActivity()).load(ConstantUtils.HOST_NAME1+movie.getPoster_path()).into(iv_poster);
        popularity_value_tv.setText(movie.getPopularity());
        language_value_tv.setText(movie.getOriginal_language());
        release_date_value_tv.setText(movie.getRelease_date());
        tv_overview.setText(movie.getOverview());
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie_details;
    }
}
