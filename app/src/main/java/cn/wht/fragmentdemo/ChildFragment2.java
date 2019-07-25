package cn.wht.fragmentdemo;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ChildFragment2 extends BaseLazyLoadFragment {
    private static final String TAG = "ChildFragment2";

    @Override
    protected void initView(View view) {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_child2;
    }


    @Override
    public void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();

    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();

    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
