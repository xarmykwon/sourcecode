package com.softapp.aboutmassage.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lionkwon.kwonutils.etc.UUID_Create;
import com.lionkwon.kwonutils.log.Logger;
import com.lionkwon.kwonutils.preference.SharedPreference;
import com.softapp.aboutmassage.JobOfferDetailContract;
import com.softapp.aboutmassage.MainActivity;
import com.softapp.aboutmassage.R;
import com.softapp.aboutmassage.bean.EmployerResponse;
import com.softapp.aboutmassage.databinding.FragmentJobOfferDetailBinding;
import com.softapp.aboutmassage.listener.PageFragmentListener;

import static com.google.common.base.Preconditions.checkNotNull;


public class Job_OfferDetailFragment extends BaseFragment implements JobOfferDetailContract.View {

    public FragmentJobOfferDetailBinding binding;

    private JobOfferDetailContract.Presenter mPresenter;

    private String idx;
    private String title;

    private Context context;

    public static Job_OfferDetailFragment newInstance(String idx, String job_title) {
        Job_OfferDetailFragment f = new Job_OfferDetailFragment();
        f.title = job_title;
        f.idx = idx;
        return f;
    }

    public static Job_OfferDetailFragment newInstance(String idx, String job_title, PageFragmentListener listener) {
        Job_OfferDetailFragment f = new Job_OfferDetailFragment();
        f.title = job_title;
        f.idx = idx;
        f.mListener = listener;
        return f;
    }

    @Override
    public void goWrite(String title) {
        mListener.onSwitchToWriteFragment(title);
    }

    @Override
    public void goEdit(String idx, String title) {
        mListener.onSwitchToWriteFragment(idx, title);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_offer_detail, container, false);
        binding.setFragment(this);
        View v = binding.getRoot();

        mPresenter.start();
        return v;
    }

    @Override
    public void initView() {

        binding.btnGoback.setOnClickListener(view -> ((MainActivity) getContext()).onBackPressed());
        binding.btnJobOfferEdit.setOnClickListener(view -> goEdit(binding.storeJobOfferIdx.getText().toString(), "수정"));
        binding.btnJobOfferDel.setOnClickListener(view -> {
            Logger.debug("구직 삭제 확인");

            android.app.AlertDialog.Builder ab = new android.app.AlertDialog.Builder(context);
            ab.setTitle("구직 삭제");
            ab.setMessage("계속 진행 하시겠습니까?");
            ab.setPositiveButton("확인", (dialog, which) -> {
                mPresenter.delTask(idx, SharedPreference.getInstance(context).getString("mem_id"), UUID_Create.getDeviceId(context));
            });
            ab.setNegativeButton("취소", (dialog, which) -> dialog.dismiss());
            ab.create().show();
        });
        binding.btnHpDial.setOnClickListener(view -> {
            try {
                android.app.AlertDialog.Builder ab = new android.app.AlertDialog.Builder(getContext());
                ab.setTitle("전화하기");
                ab.setMessage("전화연결을 하시겠습니까?");
                ab.setPositiveButton("예", (dialog, which) -> {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + binding.textHp.getText().toString()));
                    startActivity(intent);
                });
                ab.setNegativeButton("아니오", (dialog, which) -> dialog.dismiss());
                ab.create().show();
            } catch (Exception e) {
                Logger.error(e);
                Toast.makeText(context, "이용할 수 없는 전화번호 입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnJobOfferControl.setVisibility(View.GONE);
    }

    @Override
    public void setData(EmployerResponse employerResponse) {
        binding.setData(employerResponse);
        if (SharedPreference.getInstance(getContext()).getString("mem_id").equals(employerResponse.reg_id)) {
            // 본인 글인 경우
            binding.btnJobOfferControl.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBack() {
        ((MainActivity) getContext()).onBackPressed();
    }


    @Override
    public void setPresenter(JobOfferDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
