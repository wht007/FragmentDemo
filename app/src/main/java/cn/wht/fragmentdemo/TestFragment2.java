package cn.wht.fragmentdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class TestFragment2 extends BaseLazyLoadFragment {
    private static final String TAG = "TestFragment2";
    private Fragment fragment1, fragment2, fragment3;
    private RadioGroup rg;
    private ViewPager vp;
    private List<Fragment> fragments;

    @Override
    protected void initView(View view) {
        rg = (RadioGroup) view.findViewById(R.id.rg);
        vp = (ViewPager) view.findViewById(R.id.vp);
        fragments = new ArrayList<>();
        fragment1 = new ChildFragment1();
        fragment2 = new ChildFragment2();
        fragment3 = new ChildFragment3();

        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);

    }

    @Override
    public void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        vp.setOffscreenPageLimit(0);
        vp.setAdapter(new ContentPagerAdapter(getChildFragmentManager(), fragments));
        showWhichFragment(0);

        initListener();
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();

    }

    private void showWhichFragment(int index) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        switch (index) {
            case 0:
                if (fragment1 == null) {
                    fragment1 = new ChildFragment1();
                    fragmentTransaction.add(fragment1, "fragment1");
                } else {
                    fragmentTransaction.show(fragment1);
                }
                vp.setCurrentItem(0, false);
                break;

            case 1:
                if (fragment2 == null) {
                    fragment2 = new ChildFragment2();
                    fragmentTransaction.add(fragment2, "fragment2");
                } else {
                    fragmentTransaction.show(fragment2);
                }
                vp.setCurrentItem(1, false);
                break;
            case 2:
                if (fragment3 == null) {
                    fragment3 = new ChildFragment3();
                    fragmentTransaction.add(fragment3, "fragment3");
                } else {
                    fragmentTransaction.show(fragment3);
                }
                vp.setCurrentItem(2, false);
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (fragment1 != null) {
            fragmentTransaction.hide(fragment1);
        }

        if (fragment2 != null) {
            fragmentTransaction.hide(fragment2);
        }

        if (fragment3 != null) {
            fragmentTransaction.hide(fragment3);
        }
    }

    protected void initListener() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.button1:
                        showWhichFragment(0);
                        break;

                    case R.id.button3:
                        showWhichFragment(1);
                        break;
                    case R.id.button4:
                        showWhichFragment(2);
                        break;
                    default:
                        break;
                }


            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test2;
    }


    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
    }
}
