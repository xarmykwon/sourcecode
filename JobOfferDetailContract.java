package com.softapp.aboutmassage;

import com.softapp.aboutmassage.bean.EmployerResponse;

public interface JobOfferDetailContract {

    interface View extends BaseView<Presenter> {

        void initView();

        void setData(EmployerResponse employerResponse);

        void onBack();

        void goWrite(String title);

        void goEdit(String idx, String title);
    }

    interface Presenter extends BasePresenter {

        void initTask();

        void delTask(String idx, String mem_id, String uuid);

    }
}