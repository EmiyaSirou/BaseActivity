package com.myself.baseactivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
public abstract class BaseFragment extends Fragment implements OnClickListener {
    private FrameLayout mBaseContentFlayout;
    private ViewStub mProgressViewStub;
    private ViewStub mEmptyViewStub;
    private View mView;

//    protected MyApplication mMyApplication = MyApplication.getInstance();

    protected RetrofitUtil mRetrofitUtil = RetrofitUtil.getInstance();

    protected LayoutInflater mLayoutInflater;

//    private Map<String, Subscriber> mSubscriberMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = initBaseView(inflater, container, savedInstanceState);
        initView(mView, savedInstanceState);
        initBaseData(savedInstanceState);
        initData(mView, savedInstanceState);
        return mView;
    }

    /**
     * 初始化基础View
     */
    private View initBaseView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        mLayoutInflater = inflater;
        View view = inflater.inflate(R.layout.activity_base, null);
        mBaseContentFlayout = (FrameLayout) view.findViewById(R.id.flayout_base_content);
        mProgressViewStub = (ViewStub) view.findViewById(R.id.viewStub_progress);
        mEmptyViewStub = (ViewStub) view.findViewById(R.id.viewStub_empty);
        return view;
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
    protected abstract void initView(View view, Bundle savedInstanceState);

    /**
     * 初始化数据，由子类实现
     */
    protected abstract void initData(View view, Bundle savedInstanceState);

    public void showFragment() {

    }

    public void hideFragment() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unSubscribe();
    }

    /**
     * 显示读取进度条
     */
    public void showLoadingProgress() {
//        if (mProgressViewStub.getVisibility() == View.GONE) {
        mProgressViewStub.setVisibility(View.VISIBLE);
//        }
    }

    /**
     * 结束读取进度条
     */
    public void dismissLoadingProgress() {
        if (mProgressViewStub.getVisibility() == View.VISIBLE) {
            mProgressViewStub.setVisibility(View.GONE);
        }
    }

    /**
     * 设置EmptyView文字
     */
    protected void setEmptyViewTextView(int resId) {
        initEmptyView();
        TextView mTitleBtn = (TextView) mView.findViewById(R.id.tv_empty);
        mTitleBtn.setOnClickListener(this);
        mTitleBtn.setText(resId);
    }

    /**
     * 设置EmptyView文字
     */
    protected void setEmptyViewTextView(String str) {
        initEmptyView();
        TextView mTitleBtn = (TextView) mView.findViewById(R.id.tv_empty);
        mTitleBtn.setOnClickListener(this);
        mTitleBtn.setText(str);
    }

    /**
     * 设置EmptyView图片
     */
    protected void setEmptyViewImageView(int resId) {
        initEmptyView();
        ImageView mImageBtn = (ImageView) mView.findViewById(R.id.iv_empty);
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
     * 标题栏监听
     */
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tv_empty) {
            onClickEmptyView(view);

        } else if (i == R.id.iv_empty) {
            onClickEmptyView(view);

        }
    }

    /**
     * EmptyView点击事件
     */
    protected void onClickEmptyView(View view) {

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
//        if (mSubscriberMap == null) {
//            mSubscriberMap = new WeakHashMap<String, Subscriber>();
//        }
//        if (mSubscriberMap.containsKey(identify)) {
//            return mSubscriberMap.get(identify);
//        } else {
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
                Log.v("onHttpResponse", e.toString());
                onHttpResponseError(e, weakClass.get());
                if (isShowToast) {
                    Toast.makeText(getActivity(),
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
                            Log.v("onHttpResponse", httpResponse.toString());
                            onHttpResponse((T) httpResponse.getData(), weakClass.get());
                            break;
                        case "E03000":
                            WeakReference<Context> weakContext = new WeakReference<Context>(getActivity());
//                            Logout.logout(weakContext.get());
                            break;
                        default:
                            if (isShowToast) {
                                Toast.makeText(getActivity(), httpResponse.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                            break;
                    }
                }
            }
        };
//            mSubscriberMap.put(identify, subscriber);
        return subscriber;
//        }
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
//        if (mSubscriberMap == null) {
//            mSubscriberMap = new WeakHashMap<String, Subscriber>();
//        }
//        if (mSubscriberMap.containsKey(identify)) {
//            return mSubscriberMap.get(identify);
//        } else {
        Subscriber subscriber = new Subscriber<HttpResponse<List<T>>>() {

            @Override
            public void onCompleted() {
                if (isShowProgress == true)
                    dismissLoadingProgress();
                if (this != null && !this.isUnsubscribed()) {
                    this.unsubscribe();
                }
//                    onHttpResponseFinish();
            }

            @Override
            public void onError(Throwable e) {
                if (isShowProgress == true)
                    dismissLoadingProgress();
                WeakReference<String> weakClass = new WeakReference<>(identify);
                Log.v("onHttpResponse", e.toString());
                onHttpResponseError(e, weakClass.get());
                if (isShowToast) {
                    Toast.makeText(getActivity(),
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
                            Log.v("onHttpResponse", httpResponse.toString());
                            onHttpResponse((T) httpResponse.getData(), weakClass.get());
                            break;
                        case "E03000":
                            WeakReference<Context> weakContext = new WeakReference<Context>(getActivity());
//                            Logout.logout(weakContext.get());
                            break;
                        default:
                            if (isShowToast) {
                                Toast.makeText(getActivity(), httpResponse.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                            break;
                    }
                }
            }
        };
//        mSubscriberMap.put(identify, subscriber);
        return subscriber;
//        }
    }

    /**
     * 取消订阅
     */
//    protected void unSubscribe() {
//        if (mSubscriberMap != null) {
//            for (Subscriber subscriber : mSubscriberMap.values()) {
//                if (subscriber != null && !subscriber.isUnsubscribed()) {
//                    subscriber.unsubscribe();
//                }
//                mSubscriberMap.remove(subscriber);
//            }
//        }
//    }

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
