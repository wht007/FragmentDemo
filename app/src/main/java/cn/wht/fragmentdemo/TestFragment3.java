package cn.wht.fragmentdemo;

import android.util.Log;
import android.view.View;

public class TestFragment3 extends BaseLazyLoadFragment {
    private static final String TAG = "TestFragment3";

    @Override
    protected void initView(View view) {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test3;
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
    }
}
