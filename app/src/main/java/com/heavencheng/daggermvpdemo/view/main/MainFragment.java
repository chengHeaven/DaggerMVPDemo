package com.heavencheng.daggermvpdemo.view.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.heavencheng.daggermvpdemo.R;
import com.heavencheng.daggermvpdemo.presenter.main.MainContract;
import com.heavencheng.daggermvpdemo.view.BaseFragment;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Heaven
 */
public class MainFragment extends BaseFragment<MainContract.View, MainContract.Presenter> implements MainContract.View {

    @NonNull
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    public View bindView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_frag, container, false);

        return view;
    }
}