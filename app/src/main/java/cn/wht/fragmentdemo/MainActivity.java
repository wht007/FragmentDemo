package cn.wht.fragmentdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RadioGroup rg;
    private ViewPager vp;
    private Fragment fragment1, fragment2, fragment3, fragment4;
    private int currentTabPosition = 0;

    private void assignViews() {
        rg = (RadioGroup) findViewById(R.id.rg);
        vp = (ViewPager) findViewById(R.id.vp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
        final List<Fragment> fragments = new ArrayList<>();
        fragment1 = new TestFragment1();
        fragment2 = new TestFragment2();
        fragment3 = new TestFragment3();
        fragment4 = new TestFragment4();

        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        vp.setOffscreenPageLimit(0);
        vp.setAdapter(new ContentPagerAdapter(getSupportFragmentManager(), fragments));
        initFragments(savedInstanceState);
        initListener();
    }


    private void showWhichFragment(int index) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        switch (index) {
            case 0:
                if (fragment1 == null) {
                    fragment1 = new TestFragment1();
                    fragmentTransaction.add(fragment1, "fragment1");
                } else {
                    fragmentTransaction.show(fragment1);
                }
                vp.setCurrentItem(0, false);
                break;
            case 1:
                if (fragment2 == null) {
                    fragment2 = new TestFragment2();
                    fragmentTransaction.add(fragment2, "fragment2");
                } else {
                    fragmentTransaction.show(fragment2);
                }
                vp.setCurrentItem(1, false);
                break;
            case 2:
                if (fragment3 == null) {
                    fragment3 = new TestFragment3();
                    fragmentTransaction.add(fragment3, "fragment3");
                } else {
                    fragmentTransaction.show(fragment3);
                }
                vp.setCurrentItem(2, false);
                break;
            case 3:
                if (fragment4 == null) {
                    fragment4 = new TestFragment4();
                    fragmentTransaction.add(fragment4, "fragment4");
                } else {
                    fragmentTransaction.show(fragment4);
                }
                vp.setCurrentItem(3, false);
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

        if (fragment4 != null) {
            fragmentTransaction.hide(fragment4);
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
                    case R.id.button2:
                        showWhichFragment(1);
                        break;
                    case R.id.button3:
                        showWhichFragment(2);
                        break;
                    case R.id.button4:
                        showWhichFragment(3);
                        break;
                    default:
                        break;
                }


            }
        });
    }


    private void initFragments(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            fragment1 = (TestFragment1) getSupportFragmentManager().findFragmentByTag("fragment1");
            fragment2 = (TestFragment2) getSupportFragmentManager().findFragmentByTag("fragment2");
            fragment3 = (TestFragment3) getSupportFragmentManager().findFragmentByTag("fragment3");
            fragment4 = (TestFragment4) getSupportFragmentManager().findFragmentByTag("fragment4");
            // 假如异常结束了,那么可以从原来中获取到
            currentTabPosition = savedInstanceState.getInt("position");
        }
        showWhichFragment(currentTabPosition);
        // 设置默认

    }

    /**
     * fragment崩溃时会进行调用
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //奔溃前保存位置
        if (vp != null) {
            outState.putInt("position", vp.getCurrentItem());
        }
    }


}