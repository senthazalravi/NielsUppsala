package io.github.datwheat.juwan.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.datwheat.juwan.R;
import io.github.datwheat.juwan.fragment.SkillFragment;
import io.github.datwheat.juwan.utils.DimensionUtils;


public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.ViewHolder> {
    private Context context;
    private List<SkillFragment> skills;

    public SkillsAdapter(List<SkillFragment> dataset) {
        super();
        skills = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View skillView = inflater.inflate(R.layout.item_skill, parent, false);
        return new ViewHolder(skillView);
    }

    @Override
    public void onBindViewHolder(SkillsAdapter.ViewHolder holder, int position) {
        // populate fields
        SkillFragment skillFragment = skills.get(position);

        holder.skillNameTextView.setText(skillFragment.name());
        holder.progressBar.setProgress(skillFragment.progress());
        holder.progressBar.setReachedBarColor(Color.parseColor(skillFragment.color()));
        holder.progressBar.setProgressTextVisibility(NumberProgressBar.ProgressTextVisibility.Invisible);

        if (position == getItemCount() - 1) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.rootCardView.getLayoutParams();
            params.setMargins(
                    params.leftMargin,
                    params.topMargin,
                    params.rightMargin,
                    DimensionUtils.pxToDp(context, 8)
            );
            holder.rootCardView.setLayoutParams(params);
        } else {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.rootCardView.getLayoutParams();
            params.setMargins(
                    params.leftMargin,
                    params.topMargin,
                    params.rightMargin,
                    params.bottomMargin
            );
            holder.rootCardView.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return skills.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        // get fields

        @BindView(R.id.rootConstraintLayout)
        ConstraintLayout rootConstraintLayout;

        @BindView(R.id.rootCardView)
        CardView rootCardView;

        @BindView(R.id.skillNameTextView)
        TextView skillNameTextView;

        @BindView(R.id.number_progress_bar)
        NumberProgressBar progressBar;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
