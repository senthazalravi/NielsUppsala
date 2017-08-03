package io.github.datwheat.juwan.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
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
import io.github.datwheat.juwan.GetAllSkillsQuery;
import io.github.datwheat.juwan.JApplication;
import io.github.datwheat.juwan.R;
import io.github.datwheat.juwan.fragment.SkillFragment;
import io.github.datwheat.juwan.ui.adapters.SkillsAdapter;

public class SkillsActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.skillsRecyclerView)
    RecyclerView skillsRecyclerView;

    @BindView(R.id.rootConstraintLayout)
    ConstraintLayout rootConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);
        ButterKnife.bind(this);

        JApplication application = (JApplication) getApplication();

        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        assert ab != null;

        ab.setTitle(R.string.skills_activity_toolbar_title);
        ab.setDisplayHomeAsUpEnabled(true);

        final RecyclerView.LayoutManager recyclerViewLayoutManager = new LinearLayoutManager(this);
        skillsRecyclerView.setLayoutManager(recyclerViewLayoutManager);

        final List<SkillFragment> skills = new ArrayList<>();

        final RecyclerView.Adapter skillsRecyclerViewAdapter = new SkillsAdapter(skills);
        skillsRecyclerView.setAdapter(skillsRecyclerViewAdapter);
        skillsRecyclerView.setMinimumWidth(300);

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Loading...");
        progress.setCancelable(false);
        progress.show();

        application.apolloClient().newCall(new GetAllSkillsQuery()).cacheControl(CacheControl.NETWORK_FIRST).enqueue(new ApolloCall.Callback<GetAllSkillsQuery.Data>() {
            @Override
            public void onResponse(@Nonnull Response<GetAllSkillsQuery.Data> response) {
                for (final GetAllSkillsQuery.Data.Skill skill : response.data().skills()) {
                    SkillsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            skills.add(skill.fragments().skillFragment());
                            skillsRecyclerView.setAdapter(null);
                            skillsRecyclerView.setLayoutManager(null);
                            skillsRecyclerView.setAdapter(skillsRecyclerViewAdapter);
                            skillsRecyclerView.setLayoutManager(recyclerViewLayoutManager);
                            skillsRecyclerViewAdapter.notifyDataSetChanged();
                        }
                    });
                }
                SkillsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                    }
                });
            }

            @Override
            public void onFailure(@Nonnull final ApolloException e) {
                SkillsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                        Toast.makeText(SkillsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
