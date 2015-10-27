package zied.curiousbat.fetchnewschallenge;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import zied.curiousbat.fetchnewschallenge.Logging.L;
import zied.curiousbat.fetchnewschallenge.extras.SortListener;
import zied.curiousbat.fetchnewschallenge.fragments.FragmentSearch;
import zied.curiousbat.fetchnewschallenge.fragments.FragmentThree;
import zied.curiousbat.fetchnewschallenge.fragments.FragmentTwo;


public class MainActivity extends AppCompatActivity implements MaterialTabListener, View.OnClickListener {

    private ViewPager viewPager;
    // private SlidingTabLayout mTabs;

    private MaterialTabHost tabHost;

    // Fragment position
    public static final int NEWS_TECH_SEARCH = 0;

    // TAGS to filter Recyclerview
    private static final String TAG_SORT_NAME = "sortName";
    private static final String TAG_SORT_DATE = "sortDate";

    private MyPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);


        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabHost.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mAdapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab().setIcon(mAdapter.getIcon(i)).setTabListener(this)
            );
        }


        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.ic_search);


        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(imageView)
                .setBackgroundDrawable(R.drawable.selector_fab_accent)
                .build();

        ImageView iconSortByName = new ImageView(this);
        iconSortByName.setImageResource(R.drawable.ic_action_alphabets);

        ImageView iconSortByDate = new ImageView(this);
        iconSortByDate.setImageResource(R.drawable.ic_action_calendar);


        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_sub_btn_gray));

        // Create sub menu
        SubActionButton btnSortName = itemBuilder.setContentView(iconSortByName).build();
        SubActionButton btnSortDate = itemBuilder.setContentView(iconSortByDate).build();

        btnSortDate.setTag(TAG_SORT_DATE);
        btnSortName.setTag(TAG_SORT_NAME);

        btnSortName.setOnClickListener(this);
        btnSortDate.setOnClickListener(this);


        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(btnSortName)
                .addSubActionView(btnSortDate)

                .attachTo(actionButton)
                .build();


    }

    @Override
    public void onTabSelected(MaterialTab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }


    // Sort RecyclerView
    @Override
    public void onClick(View v) {
        Fragment fragment = (Fragment) mAdapter.instantiateItem(viewPager, viewPager.getCurrentItem());
        if (fragment instanceof SortListener) {

            if (v.getTag().equals(TAG_SORT_NAME)) {

                ((SortListener) fragment).onSortByName();

            }
            if (v.getTag().equals(TAG_SORT_DATE)) {
                ((SortListener) fragment).onSortByDate();
                //L.t(this, "sort by date");
            }
        }


    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        int icons[] = {R.drawable.ic_action_articles, R.drawable.ic_action_home, R.drawable.ic_action_personal};


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment myFragment = null;

            switch (position) {
                case NEWS_TECH_SEARCH:
                    myFragment = FragmentSearch.newInstance("", "");
                    break;
                case 1:
                    myFragment = FragmentTwo.newInstance("", "");
                    break;
                case 2:
                    myFragment = FragmentThree.newInstance("", "");
                    break;

            }

            return myFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return getResources().getStringArray(R.array.tabs)[position];

        }

        public Drawable getIcon(int position) {
            return getResources().getDrawable(icons[position], getTheme());
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


}
