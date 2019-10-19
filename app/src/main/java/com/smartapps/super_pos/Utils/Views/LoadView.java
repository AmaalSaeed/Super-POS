package com.smartapps.super_pos.Utils.Views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.smartapps.super_pos.R;

public class LoadView extends RelativeLayout {

    private CardView load, error, upload, editQuntity;
    private Button button, editButton;
    private TextView percent, message, errorText,wait;
    private EditText quntityText;
    private CircleProgressBar circleProgressBar;
    private OnErrorViewClickListener onErrorViewClickListener;
    private OnErrorCancelClickListener onErrorCancelClickListener;
    private OnUploadCancelViewClickListener onUploadCancelViewClickListener;
    private OnUploadHideViewClickListener onUploadHideViewClickListener;
    private OnEditQuntityViewClickListener onEditQuntityViewClickListener;
    private Button hide, cancel, cancelEdit;
    private View errorCancel;
    int quntity;

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
        editQuntity = findViewById(R.id.edit_quntity_card);
        button = findViewById(R.id.error_button);
        errorCancel = findViewById(R.id.cancel_button_error);
        upload = findViewById(R.id.upload_card);
        percent = findViewById(R.id.percent);
        hide = findViewById(R.id.hide_button);
        cancel = findViewById(R.id.cancel_button);
        errorText = findViewById(R.id.error_text);
        message = findViewById(R.id.message);
        wait = findViewById(R.id.waiting);
        editButton = findViewById(R.id.edit_quntity_button);
        quntityText = findViewById(R.id.edit_quntity_text);
        cancelEdit = findViewById(R.id.cancel_button_edit);

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
        editQuntity.setVisibility(GONE);

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
        editQuntity.setVisibility(GONE);

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
        editQuntity.setVisibility(GONE);

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
        editQuntity.setVisibility(GONE);

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
        editQuntity.setVisibility(GONE);

        this.onErrorViewClickListener = onErrorViewClickListener;
        button.setOnClickListener(v -> {this.onErrorViewClickListener.onErrorViewClickListener();
            hide();
        });
    }

    public void setUploadMessage(String me) {
        message.setText(me);
    }

    public void showLoad() {
        error.setVisibility(GONE);
        upload.setVisibility(GONE);
        load.setVisibility(VISIBLE);
        editQuntity.setVisibility(GONE);
        setProgress(0);
    }

    public void showUpload(int progress) {
        error.setVisibility(GONE);
        load.setVisibility(GONE);
        upload.setVisibility(VISIBLE);
        editQuntity.setVisibility(GONE);
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
        editQuntity.setVisibility(GONE);
        setProgress(0);
    }

    public void showEditQuntity(OnEditQuntityViewClickListener onEditQuntityViewClickListener) {
        this.setVisibility(VISIBLE);
        this.editQuntity.setVisibility(VISIBLE);
        this.quntityText.setVisibility(VISIBLE);
        this.quntityText.requestFocus();
        this.editButton.setVisibility(VISIBLE);
        this.cancelEdit.setVisibility(VISIBLE);
        errorText.setVisibility(GONE);
        load.setVisibility(GONE);
        upload.setVisibility(GONE);
        error.setVisibility(GONE);
        errorCancel.setVisibility(GONE);
        button.setVisibility(GONE);
        percent.setVisibility(GONE);
        message.setVisibility(GONE);
        wait.setVisibility(GONE);
        //quntity = quntityText.getText().toString();
        quntityText.setText("");


        this.onEditQuntityViewClickListener = onEditQuntityViewClickListener;
        editButton.setOnClickListener(v -> {
            quntity = Integer.parseInt(quntityText.getText().toString());
            this.onEditQuntityViewClickListener.onEditQuntityViewClickListener(quntity);

        hide();
        });
        cancelEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
            }
        });
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

    public interface OnEditQuntityViewClickListener {
        void onEditQuntityViewClickListener(int quntity);
    }

    public static boolean notEmpty(String s) {
        return s != null && !s.equals("") && !s.equals("null");
    }

}


