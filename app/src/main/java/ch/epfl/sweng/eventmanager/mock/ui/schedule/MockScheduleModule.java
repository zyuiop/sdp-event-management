package ch.epfl.sweng.eventmanager.mock.ui.schedule;

import android.arch.lifecycle.ViewModel;
import ch.epfl.sweng.eventmanager.ui.schedule.ScheduleActivity;
import ch.epfl.sweng.eventmanager.ui.schedule.ScheduleModule;
import ch.epfl.sweng.eventmanager.ui.schedule.ScheduleViewModel;
import ch.epfl.sweng.eventmanager.viewmodel.ViewModelKey;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * @author Louis Vialar
 */
@Module
public abstract class MockScheduleModule  {
    @ContributesAndroidInjector
    abstract ScheduleActivity contributeScheduleActivityInjector();

    @Binds
    @IntoMap
    @ViewModelKey(MockScheduleViewModel.class)
    abstract ViewModel provideScheduleViewModel(MockScheduleViewModel mockScheduleViewModel);
}
