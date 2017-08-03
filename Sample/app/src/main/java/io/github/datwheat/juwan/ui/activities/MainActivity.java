package io.github.datwheat.juwan.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.datwheat.juwan.R;
import io.github.datwheat.juwan.ui.fragments.CardFragment;


public class MainActivity extends AppCompatActivity {
    private static final int NUM_PAGES = 3;

    @BindView(R.id.pager)
    ViewPager pager;

    @BindView(R.id.profile_picture)
    ImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openAboutActivityIntent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(openAboutActivityIntent);
            }
        });

        PagerAdapter pagerAdapter = new CardPagerAdapter(getSupportFragmentManager());
        pager.setPageMargin(48);
        pager.setAdapter(pagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }

    private class CardPagerAdapter extends FragmentStatePagerAdapter {

        CardPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            CardFragment cardFragment = new CardFragment();
            Bundle cardFragmentArguments;

            switch (position) {
                case 0:
                    cardFragmentArguments = generateCardFragmentArguments(
                            "Work",
                            "Projects I’m proud of, featuring a lot of open-source work.",
                            R.drawable.ic_code_white_48dp,
                            WorkActivity.class.getName()
                    );
                    break;
                case 1:
                    cardFragmentArguments = generateCardFragmentArguments(
                            "Skills",
                            "Learn about what I can do for you!",
                            R.drawable.ic_assessment_white_48dp,
                            SkillsActivity.class.getName()
                    );
                    break;
                case 2:
                    cardFragmentArguments = generateCardFragmentArguments(
                            "Social",
                            "Find me on all of my social media platforms.",
                            R.drawable.ic_whatshot_white_48dp,
                            SocialActivity.class.getName()
                    );
                    break;
                default:
                    cardFragmentArguments = new Bundle();
                    break;
            }

            cardFragment.setArguments(cardFragmentArguments);
            return cardFragment;
        }

        private Bundle generateCardFragmentArguments(String title, String description, int imageResourceId, String destination) {
            Bundle cardFragmentArguments = new Bundle();

            cardFragmentArguments.putString(CardFragment.ARG_TITLE, title);
            cardFragmentArguments.putString(CardFragment.ARG_DESCRIPTION, description);
            cardFragmentArguments.putInt(CardFragment.ARG_IMAGE, imageResourceId);
            cardFragmentArguments.putString(CardFragment.ARG_DESTINATION, destination);

            return cardFragmentArguments;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
