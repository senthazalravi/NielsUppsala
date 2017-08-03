package io.github.datwheat.juwan.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.datwheat.juwan.R;
import io.github.datwheat.juwan.fragment.ProjectFragment;
import io.github.datwheat.juwan.utils.DimensionUtils;


public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ViewHolder> {
    private List<ProjectFragment> projectFragments;
    private Context context;

    public ProjectsAdapter(List<ProjectFragment> dataset) {
        super();

        projectFragments = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View projectView = inflater.inflate(R.layout.item_project, parent, false);

        return new ViewHolder(projectView);
    }

    @Override
    public void onBindViewHolder(ProjectsAdapter.ViewHolder holder, int position) {
        // populate viewholder fields
        final ProjectFragment projectFragment = projectFragments.get(position);

        holder.projectNameTextView.setText(projectFragment.name());
        holder.projectDescriptionTextView.setText(projectFragment.description());

        Picasso.with(context)
                .load(projectFragment.imageURL())
                .resize(256, 256)
                .centerCrop()
                .into(holder.projectImageView);

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

        holder.rootCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(projectFragment.url()));
                context.startActivity(browserIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return projectFragments.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        // get fields
        @BindView(R.id.projectImageView)
        ImageView projectImageView;

        @BindView(R.id.projectName)
        TextView projectNameTextView;

        @BindView(R.id.projectDescription)
        TextView projectDescriptionTextView;

        @BindView(R.id.rootCardView)
        CardView rootCardView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
