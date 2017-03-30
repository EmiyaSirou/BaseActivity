package com.myself.baseactivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myself.HttpConnect.HttpRequest;
import com.myself.HttpConnect.HttpResponse;
import com.myself.HttpConnect.RetrofitUtil;

import java.lang.ref.WeakReference;
import java.util.List;

import rx.Subscriber;

/**
 * Created by Emiya on 16/7/7.
 */
public abstract class BaseFragmentActivity extends FragmentActivity implements OnClickListener {
    private RelativeLayout mTitleBarRLayout;
    private FrameLayout mBaseContentFlayout;
    private ViewStub mProgressViewStub;
    private ViewStub mEmptyViewStub;

//    protected MyApplication mMyApplication = MyApplication.getInstance();

    protected RetrofitUtil mRetrofitUtil = RetrofitUtil.getInstance();

    protected LayoutInflater mLayoutInflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBaseView(savedInstanceState);
        initBaseData(savedInstanceState);
        initView(savedInstanceState);
        initData(savedInstanceState);
    }

    /**
     * 初始化基础View
     */
    private void initBaseView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_base);
        mTitleBarRLayout = (RelativeLayout) findViewById(R.id.rlayout_titlebar);
        mBaseContentFlayout = (FrameLayout) findViewById(R.id.flayout_base_content);
        mProgressViewStub = (ViewStub) findViewById(R.id.viewStub_progress);
        mEmptyViewStub = (ViewStub) findViewById(R.id.viewStub_empty);

        mLayoutInflater = getLayoutInflater();
    }

    /**
     * 初始化基础数据
     */
    private void initBaseData(Bundle savedInstanceState) {

    }

    /**
     * 设置layout
     */
    protected void setCustomView(int layout) {
        mBaseContentFlayout.addView(mLayoutInflater.inflate(layout, null), new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    /**
     * 初始化View，由子类实现
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 初始化数据，由子类实现
     */
    protected abstract void initData(Bundle savedInstanceState);

    /**
     * 初始化TitleBar
     */
    private void initTitleBar() {
        if (mTitleBarRLayout.getVisibility() == View.GONE) {
            mTitleBarRLayout.setVisibility(View.VISIBLE);
            findViewById(R.id.iv_titlebar_back).setOnClickListener(this);
        }
    }

    /**
     * 设置自定义标题内容
     */
    protected void setCustomTitle(String title) {
        initTitleBar();
        ((TextView) findViewById(R.id.tv_titlebar_title)).setText(title);
    }

    /**
     * 设置自定义标题内容
     */
    protected void setCustomTitle(int title) {
        initTitleBar();
        ((TextView) findViewById(R.id.tv_titlebar_title)).setText(title);
    }

    /**
     * 设置自定义标题栏右侧文字按钮
     */
    protected void setCustomTextButtonRight(int resId) {
        initTitleBar();
        TextView mTitleBtn = (TextView) findViewById(R.id.tv_titlebar_rightbutton);
        mTitleBtn.setOnClickListener(this);
        mTitleBtn.setText(resId);
        mTitleBtn.setVisibility(View.VISIBLE);
    }

    /**
     * 设置自定义标题栏右侧文字按钮
     */
    protected void setCustomTextButtonRight(String str) {
        initTitleBar();
        TextView mTitleBtn = (TextView) findViewById(R.id.tv_titlebar_rightbutton);
        mTitleBtn.setOnClickListener(this);
        mTitleBtn.setText(str);
        mTitleBtn.setVisibility(View.VISIBLE);
    }

    /**
     * 设置自定义标题栏右侧图片按钮
     */
    protected void setCustomImageButtonRight(int resId) {
        initTitleBar();
        ImageView mImageBtn = (ImageView) findViewById(R.id.iv_titlebar_rightbutton);
        mImageBtn.setOnClickListener(this);
        mImageBtn.setImageResource(resId);
        mImageBtn.setVisibility(View.VISIBLE);
    }

    /**
     * 标题栏监听
     */
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.iv_titlebar_back) {
            onClickBackBtn();

        } else if (i == R.id.tv_titlebar_rightbutton) {
            onClickRightButton(view);

        } else if (i == R.id.iv_titlebar_rightbutton) {
            onClickRightButton(view);

        } else if (i == R.id.tv_empty) {
            onClickEmptyView(view);

        } else if (i == R.id.iv_empty) {
            onClickEmptyView(view);

        }
    }

    /**
     * 标题栏右侧按钮点击事件
     */
    protected void onClickRightButton(View view) {

    }

    /**
     * 标题栏返回按钮事件响应
     */
    protected void onClickBackBtn() {
        finish();
    }

    /**
     * EmptyView点击事件
     */
    protected void onClickEmptyView(View view) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 显示读取进度条
     */
    protected void showLoadingProgress() {
//        if (mProgressViewStub.getVisibility() == View.GONE) {
        mProgressViewStub.setVisibility(View.VISIBLE);
//        }
    }

    /**
     * 结束读取进度条
     */
    protected void dismissLoadingProgress() {
        if (mProgressViewStub.getVisibility() == View.VISIBLE) {
            mProgressViewStub.setVisibility(View.GONE);
        }
    }

    /**
     * 设置EmptyView文字
     */
    protected void setEmptyViewTextView(int resId) {
        initEmptyView();
        TextView mTitleBtn = (TextView) findViewById(R.id.tv_empty);
        mTitleBtn.setOnClickListener(this);
        mTitleBtn.setText(resId);
    }

    /**
     * 设置EmptyView文字
     */
    protected void setEmptyViewTextView(String str) {
        initEmptyView();
        TextView mTitleBtn = (TextView) findViewById(R.id.tv_empty);
        mTitleBtn.setOnClickListener(this);
        mTitleBtn.setText(str);
    }

    /**
     * 设置EmptyView图片
     */
    protected void setEmptyViewImageView(int resId) {
        initEmptyView();
        ImageView mImageBtn = (ImageView) findViewById(R.id.iv_empty);
        mImageBtn.setOnClickListener(this);
        mImageBtn.setImageResource(resId);
    }

    /**
     * 初始化EmptyView
     */
    private void initEmptyView() {
        if (mEmptyViewStub.getVisibility() == View.GONE) {
            mEmptyViewStub.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏EmptyView
     */
    protected void dismissEmptyView() {
        if (mEmptyViewStub.getVisibility() == View.VISIBLE) {
            mEmptyViewStub.setVisibility(View.GONE);
        }
    }

    /**
     * 请求网络数据
     */
    protected void requestHttpResponse(HttpRequest httpRequest) {
        requestHttpResponse(httpRequest, true, true);
    }

    /**
     * 请求网络数据
     */
    protected void requestHttpResponse(HttpRequest httpRequest, boolean isShowProgress) {
        requestHttpResponse(httpRequest, isShowProgress, true);
    }

    /**
     * 请求网络数据
     */
    protected void requestHttpResponse(HttpRequest httpRequest, boolean isShowProgress, boolean isShowToast) {
        if (isShowProgress)
            showLoadingProgress();
        Subscriber subscriber = getSubscription(httpRequest.getIdentify(), isShowProgress, isShowToast);
        mRetrofitUtil.setObservable(httpRequest.getObservable(), subscriber);
    }

    /**
     * 网络数据返回
     */
    private <T> Subscriber getSubscription(final String identify, final boolean isShowProgress, final boolean isShowToast) {
        Subscriber subscriber = new Subscriber<HttpResponse<T>>() {

            @Override
            public void onCompleted() {
                if (isShowProgress == true)
                    dismissLoadingProgress();
//                    onHttpResponseFinish();
                if (this != null && !this.isUnsubscribed()) {
                    this.unsubscribe();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (isShowProgress == true)
                    dismissLoadingProgress();
                WeakReference<String> weakClass = new WeakReference<>(identify);
                Log.v("onHttpResponse", weakClass.get() + " " + e.toString());
                onHttpResponseError(e, weakClass.get());
                if (isShowToast) {
                    Toast.makeText(BaseFragmentActivity.this,
                            R.string.network_connect_fail,
                            Toast.LENGTH_SHORT).show();
                }
                if (this != null && !this.isUnsubscribed()) {
                    this.unsubscribe();
                }
            }

            @Override
            public void onNext(HttpResponse<T> httpResponse) {
                String code = httpResponse.getCode();
                if (code != null) {
                    switch (code) {
                        case "N00000":
                            WeakReference<String> weakClass = new WeakReference<>(identify);
                            Log.v("onHttpResponse", weakClass.get() + " " + httpResponse.toString());
                            onHttpResponse((T) httpResponse.getData(), weakClass.get());
                            break;
                        case "E03000":
                            WeakReference<Context> weakContext = new WeakReference<Context>(BaseFragmentActivity.this);
//                            Logout.logout(weakContext.get());
                            break;
                        default:
                            if (isShowToast) {
                                Toast.makeText(BaseFragmentActivity.this, httpResponse.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                            break;
                    }
                }
            }
        };
        return subscriber;
    }

    /**
     * 请求ArrayList类型的网络数据
     */
    protected void requestHttpResponseForList(HttpRequest httpRequest) {
        requestHttpResponseForList(httpRequest, true, true);
    }

    /**
     * 请求ArrayList类型的网络数据
     */
    protected void requestHttpResponseForList(HttpRequest httpRequest, boolean isShowProgress) {
        requestHttpResponseForList(httpRequest, isShowProgress, true);
    }


    /**
     * 请求ArrayList类型的网络数据
     */
    protected void requestHttpResponseForList(HttpRequest httpRequest, boolean isShowProgress, boolean isShowToast) {
        if (isShowProgress)
            showLoadingProgress();
        Subscriber subscriber = getSubscriptionForList(httpRequest.getIdentify(), isShowProgress, isShowToast);
        mRetrofitUtil.setObservable(httpRequest.getObservable(), subscriber);
    }

    /**
     * ArrayList类型的网络数据返回
     */
    private <T> Subscriber getSubscriptionForList(final String identify, final boolean isShowProgress, final boolean isShowToast) {
        Subscriber subscriber = new Subscriber<HttpResponse<List<T>>>() {

            @Override
            public void onCompleted() {
                if (isShowProgress == true)
                    dismissLoadingProgress();
//                    onHttpResponseFinish();
                if (this != null && !this.isUnsubscribed()) {
                    this.unsubscribe();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (isShowProgress == true)
                    dismissLoadingProgress();
                WeakReference<String> weakClass = new WeakReference<>(identify);
                Log.v("onHttpResponse", weakClass.get() + " " + e.toString());
                onHttpResponseError(e, weakClass.get());
                if (isShowToast) {
                    Toast.makeText(BaseFragmentActivity.this,
                            R.string.network_connect_fail,
                            Toast.LENGTH_SHORT).show();
                }
                if (this != null && !this.isUnsubscribed()) {
                    this.unsubscribe();
                }
            }

            @Override
            public void onNext(HttpResponse<List<T>> httpResponse) {
                String code = httpResponse.getCode();
                if (code != null) {
                    switch (code) {
                        case "N00000":
                            WeakReference<String> weakClass = new WeakReference<>(identify);
                            Log.v("onHttpResponse", weakClass.get() + " " + httpResponse.toString());
                            onHttpResponse((T) httpResponse.getData(), weakClass.get());
                            break;
                        case "E03000":
                            WeakReference<Context> weakContext = new WeakReference<Context>(BaseFragmentActivity.this);
//                            Logout.logout(weakContext.get());
                            break;
                        default:
                            if (isShowToast) {
                                Toast.makeText(BaseFragmentActivity.this, httpResponse.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                            break;
                    }
                }
            }
        };
        return subscriber;
    }

    /**
     * 获取返回的网络数据,重写调用
     */
    protected <T> void onHttpResponse(T t, String identify) {

    }

//    /**
//     * 网络数据返回结束
//     */
//    protected void onHttpResponseFinish() {
//
//    }

    /**
     * 网络错误
     */
    protected void onHttpResponseError(Throwable e, String identify) {

    }
}
