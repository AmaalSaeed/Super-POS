package com.smartapps.super_pos.Utils.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.smartapps.super_pos.R;

public class LoadView extends RelativeLayout {

    private CardView load, error, upload;
    private Button button;
    private TextView percent, message, errorText,wait;
    private CircleProgressBar circleProgressBar;
    private OnErrorViewClickListener onErrorViewClickListener;
    private OnErrorCancelClickListener onErrorCancelClickListener;
    private OnUploadCancelViewClickListener onUploadCancelViewClickListener;
    private OnUploadHideViewClickListener onUploadHideViewClickListener;
    private Button hide, cancel;
    private View errorCancel;

    public LoadView(Context context , ViewGroup.LayoutParams layoutParams) {
        super(context);
        setLayoutParams(layoutParams);
        setId(R.id.load_view);
        init();
    }

    public LoadView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public LoadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.load_progress_view, this);
        load = findViewById(R.id.load_card);
        error = findViewById(R.id.error_card);
        button = findViewById(R.id.error_button);
        errorCancel = findViewById(R.id.cancel_button_error);
        upload = findViewById(R.id.upload_card);
        percent = findViewById(R.id.percent);
        hide = findViewById(R.id.hide_button);
        cancel = findViewById(R.id.cancel_button);
        errorText = findViewById(R.id.error_text);
        message = findViewById(R.id.message);
        wait = findViewById(R.id.waiting);

        errorCancel.setOnClickListener(v -> {
            if(onErrorCancelClickListener != null){
                onErrorCancelClickListener.onErrorCancelClickListener();
            }
            hide();
        });
        cancel.setOnClickListener(v -> {
            if (onUploadCancelViewClickListener != null)
                onUploadCancelViewClickListener.onUploadCancelViewClickListener();
            hide();

        });
        hide.setOnClickListener(v -> {
            if (onUploadHideViewClickListener != null)
                onUploadHideViewClickListener.onUploadHideViewClickListener();

            hide();

        });
        circleProgressBar = findViewById(R.id.circle_progress);
        hide();
        this.setOnClickListener(v -> {
        });


    }

    public void show() {
        this.wait.setText("الرجاء الانتظار ...");
        showLoad();
        this.setVisibility(VISIBLE);
    }

    public void show(String s) {
        this.wait.setText(s);
        showLoad();
        this.setVisibility(VISIBLE);
    }

    @SuppressLint("SetTextI18n")
    public void setProgress(int i) {
        circleProgressBar.setProgressWithAnimation(i);
        percent.setText(i + "%");
    }

    public void hide() {
        this.setVisibility(GONE);
    }

    public void setOnErrorViewClickListener(OnErrorViewClickListener onErrorViewClickListener) {
        this.onErrorViewClickListener = onErrorViewClickListener;
        button.setOnClickListener(v -> {this.onErrorViewClickListener.onErrorViewClickListener();
            hide();
        });
    }


    public void setOnUploadCancelViewClickListener(OnUploadCancelViewClickListener onUploadCancelViewClickListener) {
        this.onUploadCancelViewClickListener = onUploadCancelViewClickListener;
    }

    public void setOnUploadHideViewClickListener(OnUploadHideViewClickListener onUploadHideViewClickListener) {
        this.onUploadHideViewClickListener = onUploadHideViewClickListener;
    }

    public void showError(OnErrorViewClickListener onErrorViewClickListener) {

        errorText.setText("لايوجد اتصال ..");
        this.setVisibility(VISIBLE);
        load.setVisibility(GONE);
        upload.setVisibility(GONE);
        errorCancel.setVisibility(GONE);
        error.setVisibility(VISIBLE);

        this.onErrorViewClickListener = onErrorViewClickListener;
        button.setOnClickListener(v -> {this.onErrorViewClickListener.onErrorViewClickListener();
            hide();
        });
    }

    public void showError(String s,OnErrorViewClickListener onErrorViewClickListener , OnErrorCancelClickListener onErrorCancelClickListener) {

        errorText.setText("لايوجد اتصال ..");
        this.setVisibility(VISIBLE);
        load.setVisibility(GONE);
        upload.setVisibility(GONE);
        errorText.setText(s);
        error.setVisibility(VISIBLE);

        errorCancel.setVisibility(VISIBLE);
        this.onErrorCancelClickListener = onErrorCancelClickListener;
        this.onErrorViewClickListener = onErrorViewClickListener;
        button.setOnClickListener(v -> {this.onErrorViewClickListener.onErrorViewClickListener();
            hide();
        });
    }

    public void showError() {
        errorText.setText("لايوجد اتصال ..");
        this.setVisibility(VISIBLE);
        load.setVisibility(GONE);
        upload.setVisibility(GONE);
        error.setVisibility(VISIBLE);
        errorCancel.setVisibility(VISIBLE);
        button.setVisibility(GONE);

        errorCancel.setOnClickListener(v -> hide());

    }
    public void showError(String s) {
        if (s.length() > 32)
            s = s.substring(0, 30);
        errorText.setText(s);
        this.setVisibility(VISIBLE);
        load.setVisibility(GONE);
        upload.setVisibility(GONE);
        error.setVisibility(VISIBLE);
        errorCancel.setVisibility(VISIBLE);
        button.setVisibility(GONE);

        errorCancel.setOnClickListener(v -> hide());

    }



    public void showError(String error , OnErrorViewClickListener onErrorViewClickListener) {
        if (error.length() > 32)
            error = error.substring(0, 30);
        errorText.setText(error);
        this.setVisibility(VISIBLE);
        load.setVisibility(GONE);
        upload.setVisibility(GONE);
        this.error.setVisibility(VISIBLE);
        errorCancel.setVisibility(GONE);

        this.onErrorViewClickListener = onErrorViewClickListener;
        button.setOnClickListener(v -> {this.onErrorViewClickListener.onErrorViewClickListener();
            hide();
        });    }

    public void setUploadMessage(String me) {
        message.setText(me);
    }

    public void showLoad() {
        error.setVisibility(GONE);
        upload.setVisibility(GONE);
        load.setVisibility(VISIBLE);
        setProgress(0);
    }

    public void showUpload(int progress) {
        error.setVisibility(GONE);
        load.setVisibility(GONE);
        upload.setVisibility(VISIBLE);
        setProgress(progress);

    }

    public void showUpload(boolean showCancel, boolean showHide, String message) {
        if (showCancel)
            cancel.setVisibility(VISIBLE);
        else
            cancel.setVisibility(GONE);
        if (showHide)
            hide.setVisibility(VISIBLE);
        else
            hide.setVisibility(GONE);
        if (notEmpty(message))
            setUploadMessage(message);
        this.setVisibility(VISIBLE);
        error.setVisibility(GONE);
        load.setVisibility(GONE);
        upload.setVisibility(VISIBLE);
        setProgress(0);
    }

    public interface OnErrorViewClickListener {
        void onErrorViewClickListener();
    }

    public interface OnErrorCancelClickListener {
        void onErrorCancelClickListener();
    }

    public interface OnUploadCancelViewClickListener {
        void onUploadCancelViewClickListener();
    }

    public interface OnUploadHideViewClickListener {
        void onUploadHideViewClickListener();
    }

    public static boolean notEmpty(String s) {
        return s != null && !s.equals("") && !s.equals("null");
    }

}


