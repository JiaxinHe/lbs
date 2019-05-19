package app.lbs.com.lbsapp.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import app.lbs.com.lbsapp.R;


public abstract class BaseFragment
        extends Fragment {

    //    public T mPresenter;
    //    public E                      mInterator;

    protected abstract int initLayout();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View headerView = inflater.inflate(initLayout(), container, false);
        return headerView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 设置导航栏标题
     * 
     * @param title
     */
    protected final void setActionTitle(String title) {
        TextView titleTv = findView(R.id.tv_action_title);
        if (titleTv != null && !TextUtils.isEmpty(title)) {
            titleTv.setText(title);
        }
    }

    /**
     * 设置导航栏标题
     * 
     * @param resId
     */
    protected final void setActionTitle(int resId) {
        String title = getResources().getString(resId);
        setActionTitle(title);
    }


    /**
     * 通过控件的Id获取对应的控件
     * 
     * @param viewId
     * @return
     */
    @SuppressWarnings("unchecked")
    protected final <U extends View> U findView(int viewId) {
        View rView = getView().findViewById(viewId);
        return (U) rView;
    }

    /**
     * 通过控件的Id和控件所在布局获取对应的控件
     * 
     * @param view
     * @param viewId
     * @return
     */
    @SuppressWarnings("unchecked")
    protected final <U extends View> U findView(View view, int viewId) {
        View rView = view.findViewById(viewId);
        return (U) rView;
    }

    /**
     * 给id设置监听
     *
     * @param ids
     */
    protected final void setOnClick(int... ids) {
        if (ids == null) {
            return;
        }
        for (int i : ids) {
            setOnClick(this.<View> findView(i));
        }
    }

    /**
     * 给view设置监听
     * 
     * @param params
     */
    protected final void setOnClick(View... params) {
        if (params == null) {
            return;
        }
        for (View view : params) {
            if (view != null && this instanceof OnClickListener) {
                view.setOnClickListener((OnClickListener) this);
            }
        }
    }

    public void finishActivity() {
    }

    public void showMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

}
