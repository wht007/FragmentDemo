package cn.wht.fragmentdemo;

import android.util.Log;
import android.view.View;

public class TestFragment4 extends BaseLazyLoadFragment {
    private static final String TAG = "TestFragment4";

    @Override
    protected void initView(View view) {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test4;
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
