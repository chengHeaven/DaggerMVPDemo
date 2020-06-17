package com.heavencheng.daggermvpdemo.di.component;

import com.heavencheng.daggermvpdemo.di.FragmentScoped;
import com.heavencheng.daggermvpdemo.di.module.MainPresenterModule;
import com.heavencheng.daggermvpdemo.view.main.MainActivity;

import dagger.Component;

/**
 * @author Heaven
 */
@FragmentScoped
@Component(dependencies = DataRepositoryComponent.class, modules = MainPresenterModule.class)
public interface MainActivityComponent {

    /**
     * inject activity
     *
     * @param activity {@link MainActivity}
     */
    void inject(MainActivity activity);
}
