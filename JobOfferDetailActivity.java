package com.softapp.aboutmassage;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.softapp.aboutmassage.fragment.Job_OfferDetailFragment;
import com.softapp.aboutmassage.retro.ApiUtils;
import com.softapp.aboutmassage.util.ActivityUtils;


public class JobOfferDetailActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_offer_detail);


        String idx = getIntent().getStringExtra("idx");
        String title = getIntent().getStringExtra("title");

        Job_OfferDetailFragment job_offerDetailFragment = (Job_OfferDetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (job_offerDetailFragment == null) {
            job_offerDetailFragment = Job_OfferDetailFragment.newInstance(idx, title);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    job_offerDetailFragment, R.id.contentFrame);
        }

//        // Create the presenter
        new JobOfferDetailPresenter(
                idx,
                ApiUtils.getRetrofitService(),
                job_offerDetailFragment);
    }


}
