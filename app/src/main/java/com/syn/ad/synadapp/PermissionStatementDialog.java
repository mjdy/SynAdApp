package com.syn.ad.synadapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.syn.ad.synad.testapp.R;


/**
 * 权益说明
 */
public class PermissionStatementDialog extends Dialog {
    private ConfirmedCallback callback;
    private TextView tv_desc;
    private Button btn_use;
    private TextView iv_close;
    private String agreement = "我们非常重视您的个人信息和隐私保护。为了更好地保障您的个人权益，在您使用我们的产品前，请务必审慎阅读《服务协议》与《隐私政策》内的所有条款。";

    public interface ConfirmedCallback {
        void onConfirmed();

        void onCancel();
    }

    public PermissionStatementDialog(@NonNull Context context, ConfirmedCallback callback) {
        super(context, R.style.dialog);
        this.callback = callback;
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_permission_statement);
        iv_close = findViewById(R.id.iv_close);
        btn_use = findViewById(R.id.btn_use);
        tv_desc = findViewById(R.id.tv_desc);
        initRegistSpanString(tv_desc);

        btn_use.setOnClickListener(view -> {
            cancel();
            if (callback != null) callback.onConfirmed();
        });
        iv_close.setOnClickListener(v -> {
            cancel();
            if (callback != null) callback.onCancel();
        });
    }

    /**
     * 协议
     */
    private void initRegistSpanString(TextView tv_ag) {
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(agreement);
        //隐私协议
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
            }
        };
        spannableString.setSpan(clickableSpan, 57, 63, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        //用户协议
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
            }
        };
        spannableString.setSpan(clickableSpan1, 50, 56, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getContext().getResources().getColor(R.color.color_017ef2));
        spannableString.setSpan(colorSpan, 50, 56, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(getContext().getResources().getColor(R.color.color_017ef2));
        spannableString.setSpan(colorSpan1, 57, 63, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_ag.setText(spannableString);
        tv_ag.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
