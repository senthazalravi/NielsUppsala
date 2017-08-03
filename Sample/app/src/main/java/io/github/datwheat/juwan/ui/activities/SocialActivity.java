package io.github.datwheat.juwan.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.cache.normalized.CacheControl;
import com.apollographql.apollo.exception.ApolloException;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.datwheat.juwan.GetAllSocialOutletsQuery;
import io.github.datwheat.juwan.JApplication;
import io.github.datwheat.juwan.R;
import io.github.datwheat.juwan.fragment.SocialOutletFragment;
import io.github.datwheat.juwan.ui.adapters.SocialAdapter;
import io.github.datwheat.juwan.ui.decorations.SocialItemDecoration;
import io.github.datwheat.juwan.utils.DimensionUtils;

public class SocialActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.socialRecyclerView)
    RecyclerView socialRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        ButterKnife.bind(this);

        JApplication application = (JApplication) getApplication();

        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        assert ab != null;

        ab.setTitle(R.string.social_activity_toolbar_title);
        ab.setDisplayHomeAsUpEnabled(true);

        final RecyclerView.LayoutManager recyclerViewLayoutManager = new GridLayoutManager(this, 2);
        socialRecyclerView.setLayoutManager(recyclerViewLayoutManager);

        final List<SocialOutletFragment> socialOutlets = new ArrayList<>();
        final RecyclerView.Adapter socialRecyclerViewAdapter = new SocialAdapter(socialOutlets);

        socialRecyclerView.addItemDecoration(new SocialItemDecoration(DimensionUtils.pxToDp(this, 8)));
        socialRecyclerView.setAdapter(socialRecyclerViewAdapter);

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading...");
        progress.setCancelable(false);
        progress.show();

        application.apolloClient().newCall(new GetAllSocialOutletsQuery()).cacheControl(CacheControl.NETWORK_FIRST).enqueue(new ApolloCall.Callback<GetAllSocialOutletsQuery.Data>() {
            @Override
            public void onResponse(@Nonnull Response<GetAllSocialOutletsQuery.Data> response) {
                for (final GetAllSocialOutletsQuery.Data.SocialOutlet socialOutlet : response.data().socialOutlets()) {
                    SocialActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            socialOutlets.add(socialOutlet.fragments().socialOutletFragment());
                            socialRecyclerView.setAdapter(null);
                            socialRecyclerView.setLayoutManager(null);
                            socialRecyclerView.setAdapter(socialRecyclerViewAdapter);
                            socialRecyclerView.setLayoutManager(recyclerViewLayoutManager);
                            socialRecyclerViewAdapter.notifyDataSetChanged();
                        }
                    });
                }
                SocialActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                    }
                });
            }

            @Override
            public void onFailure(@Nonnull final ApolloException e) {
                SocialActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                        Toast.makeText(SocialActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
