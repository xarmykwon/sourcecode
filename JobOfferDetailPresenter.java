package com.softapp.aboutmassage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lionkwon.kwonutils.log.Logger;
import com.softapp.aboutmassage.bean.EmployerResponse;
import com.softapp.aboutmassage.retro.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

public class JobOfferDetailPresenter implements JobOfferDetailContract.Presenter {

    private final RetrofitService mTasksRepository;
    private final JobOfferDetailContract.View mTaskDetailView;

    @Nullable
    private String idx;

    public JobOfferDetailPresenter(@Nullable String idx,
                               @NonNull RetrofitService tasksRepository,
                               @NonNull JobOfferDetailContract.View taskDetailView) {
        this.idx = idx;
        mTasksRepository = checkNotNull(tasksRepository, "tasksRepository cannot be null!");
        mTaskDetailView = checkNotNull(taskDetailView, "taskDetailView cannot be null!");
        mTaskDetailView.setPresenter(this);
    }

    @Override
    public void start() {
        mTaskDetailView.initView();
        initTask();
    }
    @Override
    public void initTask() {
        mTasksRepository.getEmployerResponseView(idx).enqueue(callback);
    }

    @Override
    public void delTask(String idx, String mem_id, String uuid) {
        mTasksRepository.getEmployerResponseDel(idx, mem_id, uuid).enqueue(del_callback);
    }

    Callback callback = new Callback<EmployerResponse>() {
        @Override
        public void onResponse(Call<EmployerResponse> call, Response<EmployerResponse> response) {
            Logger.debug("retrofit onResponse " + response.code());
            if (response.isSuccessful()) {
                Logger.debug("retrofit 성공 좋아용");
                Logger.debug("idx : " + response.body().idx);
                Logger.debug("title : " + response.body().title);
                Logger.debug("reg_dte : " + response.body().reg_dte);
                Logger.debug("addr: " + response.body().addr);
                Logger.debug("cntt : " + response.body().cntt);
                Logger.debug("gender : " + response.body().gender);
                Logger.debug("hp : " + response.body().hp);
                Logger.debug("salary : " + response.body().salary);
                Logger.debug("salary_type : " + response.body().salary_type);
                Logger.debug("shop_nm : " + response.body().shop_nm);
                Logger.debug("theme : " + response.body().theme);
                Logger.debug("work_type : " + response.body().work_type);
                Logger.debug("age : " + response.body().age);
                mTaskDetailView.setData(response.body());
            } else {
                int statusCode = response.code();
            }
        }

        @Override
        public void onFailure(Call<EmployerResponse> call, Throwable t) {
            Logger.debug("retrofit 실패 onFailure" + t.getMessage());
            Logger.debug("retrofit 실패 onFailure" + t.toString());

        }
    };

    Callback del_callback = new Callback<EmployerResponse>() {
        @Override
        public void onResponse(Call<EmployerResponse> call, Response<EmployerResponse> response) {
            Logger.debug("retrofit onResponse " + response.code());
            if (response.isSuccessful()) {
                Logger.debug("구직 삭제 성공");
//                ((MainActivity) getContext()).onBackPressed();
                mTaskDetailView.onBack();
            } else {
                int statusCode = response.code();
                // 에러 코드로 예외처리
            }
        }

        @Override
        public void onFailure(Call<EmployerResponse> call, Throwable t) {
            Logger.debug("retrofit 실패 onFailure" + t.getMessage());
            Logger.debug("retrofit 실패 onFailure" + t.toString());
        }
    };

}