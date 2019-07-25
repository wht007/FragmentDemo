package cn.wht.fragmentdemo;

import android.view.View;

public class ChildFragment1 extends BaseLazyLoadFragment {
    private static final String TAG = "ChildFragment1";

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_child1;
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
