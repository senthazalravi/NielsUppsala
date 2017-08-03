package io.github.datwheat.juwan.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.datwheat.juwan.R;


public class CardFragment extends Fragment {
    public static final String ARG_TITLE = "title";
    public static final String ARG_DESCRIPTION = "description";
    public static final String ARG_IMAGE = "image";
    public static final String ARG_DESTINATION = "destination";

    @BindView(R.id.card_title)
    TextView cardTitle;

    @BindView(R.id.card_description)
    TextView cardDescription;

    @BindView(R.id.icon)
    ImageView icon;

    @BindView(R.id.go_button)
    Button goButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_card, container, false);
        ButterKnife.bind(this, rootView);

        Bundle arguments = getArguments();

        String title = arguments.getString(ARG_TITLE);
        String description = arguments.getString(ARG_DESCRIPTION);
        int imageResource = arguments.getInt(ARG_IMAGE);

        cardTitle.setText(title);
        cardDescription.setText(description);
        icon.setImageResource(imageResource);

        String destination = arguments.getString(ARG_DESTINATION);

        Class DestinationActivity = null;
        try {
            DestinationActivity = Class.forName(destination);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        final Class finalDestinationActivity = DestinationActivity;
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), finalDestinationActivity);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
