package com.heavencheng.daggermvpdemo.view;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.heavencheng.daggermvpdemo.R;
import com.heavencheng.daggermvpdemo.constants.Configs;
import com.heavencheng.daggermvpdemo.presenter.BaseAbstractPresenter;
import com.heavencheng.daggermvpdemo.presenter.BasePresenter;
import com.heavencheng.daggermvpdemo.util.CheckNetwork;
import com.heavencheng.daggermvpdemo.view.main.MainActivity;

import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author Heaven
 * 所有Fragment父类，必须继承
 */
public abstract class BaseFragment<T extends BaseView<P>, P extends BasePresenter> extends Fragment implements BaseView<P> {

    protected BaseAbstractPresenter<T, P> mBaseAbstractPresenter;
    protected Disposable mDisposable;
    protected CompositeDisposable mCompositeDisposable;
    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isInvade() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = Objects.requireNonNull(getActivity()).getWindow();
            TypedValue typedValue = new TypedValue();
            getActivity().getTheme().resolveAttribute(R.color.colorPrimary, typedValue, true);
            int[] attribute = new int[]{R.color.colorPrimary};
            TypedArray array = getActivity().obtainStyledAttributes(typedValue.resourceId, attribute);
            int color = array.getColor(0, getResources().getColor(R.color.colorPrimary));
            array.recycle();
            window.setStatusBarColor(color);
        }

        mCompositeDisposable = new CompositeDisposable();

        if (CheckNetwork.getConnectedType(getContext()) == -1) {
            toastMessage(R.string.network_disconnected);
        }
    }

    protected boolean isInvade() {
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(Configs.TAG, "mBaseAbstractPresenter: " + (mBaseAbstractPresenter == null));
        if (mBaseAbstractPresenter == null) {
            restartApp();
            return null;
        }
        return bindView(inflater, container, savedInstanceState);
    }

    public void restartApp() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Nullable
    protected abstract View bindView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    @SuppressWarnings("unchecked")
    @Override
    public void setPresenter(BasePresenter presenter) {
        mBaseAbstractPresenter = (BaseAbstractPresenter<T, P>) presenter;
        mPresenter = (P) presenter;
    }

    @Override
    public void showWaiting() {
    }

    @Override
    public void hideWaiting() {
    }

    @Override
    public boolean disconnectedNetwork() {
        if (CheckNetwork.getConnectedType(getContext()) == -1) {
            toastMessage(R.string.network_disconnected);
        }
        return CheckNetwork.getConnectedType(getContext()) == -1;
    }

    @Override
    public void toastMessage(String message) {
        hideWaiting();
        if (getActivity() != null) {
            if (message.contains("401")) {
                toastMessage(R.string.reLogin);
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void toastMessage(@StringRes int stringRes) {
        toastMessage(getString(stringRes));
    }

    @Override
    public void finishActivityForResult() {
        finishActivityForResult(0);
    }

    @Override
    public void finishActivityForResult(int resultCode) {
        if (getActivity() != null) {
            getActivity().setResult(resultCode);
            getActivity().finish();
        }
    }

    @Override
    public void finishActivity() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    @Override
    public void whetherLogin(String message) {
        if (getActivity() != null) {
            if (message.contains("401")) {
                toastMessage(R.string.reLogin);
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        if (mCompositeDisposable != null && mCompositeDisposable.size() != 0) {
            mCompositeDisposable.dispose();
        }
        if (mBaseAbstractPresenter != null) {
            mBaseAbstractPresenter.clear();
        }
        super.onDestroy();
    }
}
