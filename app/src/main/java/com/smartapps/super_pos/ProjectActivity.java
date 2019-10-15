package com.smartapps.super_pos;

import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.smartapps.super_pos.Utils.Views.LoadView;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProjectActivity extends AppCompatActivity {
    private LoadView loadView;
    private ArrayList<Disposable> disposables = new ArrayList<>();
    private ViewGroup viewGroup;

    @Override
    public void setContentView(int view) {
        super.setContentView(view);
        viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);

        if (viewGroup instanceof RelativeLayout  ) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            loadView = new LoadView(this,layoutParams);

        } else if (viewGroup instanceof ConstraintLayout) {
            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            loadView = new LoadView(this,layoutParams);
        } else if (viewGroup instanceof CoordinatorLayout) {
            CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            loadView = new LoadView(this,layoutParams);
        }
        else if (viewGroup instanceof FrameLayout) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            loadView = new LoadView(this,layoutParams);
        } else {
            loadView = null;
        }
        if(loadView != null) {
            viewGroup.addView(loadView);
        }

    }

    public void show() {
        addToDisposables(executeDelay(50).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(string -> loadView.show()));
    }

    public void show(String s) {
        addToDisposables(executeDelay(50).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(string -> loadView.show(s)));
    }

    public void executeDelay(int delay, DelayedTask delayedTask) {
        addToDisposables(executeDelay(delay).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(string -> delayedTask.onFinish()));
    }

    public Observable<String> executeDelay(int delay) {

        Callable<String> stringCallable = () -> {
            Thread.sleep(delay);
            return "Hello World";
        };
        return Observable.fromCallable(stringCallable);

    }

    public void hide() {
        addToDisposables(executeDelay(500).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(string -> loadView.hide()));
    }

    public void showInternetError() {
        addToDisposables(executeDelay(50).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(string -> loadView.showError()));
    }

    public void showError(String s) {
        addToDisposables(executeDelay(50).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(string -> loadView.showError(s)));
    }

    public void showError(String s, LoadView.OnErrorViewClickListener onErrorViewClickListener, LoadView.OnErrorCancelClickListener onErrorCancelClickListener) {
        addToDisposables(executeDelay(50).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(string -> loadView.showError(s, onErrorViewClickListener, onErrorCancelClickListener)));
    }

    public void showInternetError(LoadView.OnErrorViewClickListener onErrorViewClickListener) {
        addToDisposables(executeDelay(50).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(string -> loadView.showError(onErrorViewClickListener)));
    }

    public void showError(String s, LoadView.OnErrorViewClickListener onErrorViewClickListener) {
        addToDisposables(executeDelay(50).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(string -> loadView.showError(s, onErrorViewClickListener)));
    }

    public void addToDisposables(Disposable disposable) {
        if (disposable != null)
            disposables.add(disposable);
    }

    @Override
    protected void onDestroy() {
        if (disposables.size() > 0) {
            for (Disposable disposable : disposables) {
                disposable.dispose();
            }
        }
        super.onDestroy();
    }

    public void showProgress(int progress) {
        addToDisposables(executeDelay(50).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(string -> loadView.showUpload(progress )));

    }

    interface DelayedTask {
        void onFinish();
    }
}
