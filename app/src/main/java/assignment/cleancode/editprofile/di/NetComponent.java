package assignment.cleancode.editprofile.di;


import android.app.Application;

import javax.inject.Singleton;

import assignment.cleancode.editprofile.AppApplication;
import assignment.cleancode.editprofile.di.modules.ActivityBuilderModule;
import assignment.cleancode.editprofile.di.modules.AppModule;
import assignment.cleancode.editprofile.di.modules.SharedPreferencesModule;
import assignment.cleancode.editprofile.di.modules.ViewModelModule;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by Munir Ahmad on 07/07/19.
 */

@Singleton
@Component(modules = {AppModule.class, AndroidInjectionModule.class,
        SharedPreferencesModule.class, ActivityBuilderModule.class,
        AndroidSupportInjectionModule.class,
        ViewModelModule.class
})

public interface NetComponent {

    void inject(AppApplication app);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        NetComponent build();
    }
}