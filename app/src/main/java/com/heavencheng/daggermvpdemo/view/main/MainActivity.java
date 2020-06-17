package com.heavencheng.daggermvpdemo.view.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.heavencheng.daggermvpdemo.R;
import com.heavencheng.daggermvpdemo.application.App;
import com.heavencheng.daggermvpdemo.di.component.DaggerMainActivityComponent;
import com.heavencheng.daggermvpdemo.di.module.MainPresenterModule;
import com.heavencheng.daggermvpdemo.presenter.main.MainPresenter;
import com.heavencheng.daggermvpdemo.view.BaseActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * @author Heaven
 */
public class MainActivity extends BaseActivity {

    @Inject
    MainPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.main_content_layout);

        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_content_layout, mainFragment);
            transaction.commit();
        }

        DaggerMainActivityComponent.builder()
                .dataRepositoryComponent(((App) getApplication()).getDataRepositoryComponent())
                .mainPresenterModule(new MainPresenterModule(mainFragment))
                .build()
                .inject(this);
    }
}
