package cn.wht.fragmentdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 懒加载
 */
public abstract class BaseLazyLoadFragment extends Fragment {
    private View view;
    private boolean isViewCreate = false;// View 是否创建
    private boolean isFirstEnter = false;// View 第一次可见
    /**
     * 保存 fragment 显示状态(默认值false)
     * 假如有4个 Fragment 每一个 Fragment 都显示过，
     * 此时如果跨页面跳转，例如 1 -> 4
     * 那么就会调用1 2 3 的 onFragmentPause 和4的 onFragmentResume
     * 1 -> 4 显然 2 3 的 onFragmentPause 调用时多余的，
     * 正常的情况下应该是 1 的 onFragmentPause 和 4 的onFragmentPause
     * 所以就需要使用 currentVisiableState 记录当前 Fragment 的显示状态
     * 如果 currentVisiableState==isVisibleToUser，
     * 就代表跨页面跳转时中间 Fragment 的 onFragmentPause 方法被调用
     * <p>
     * 解决办法就是在两个值相等时 return
     */
    private boolean currentFragmentVisibleState = false;// 保存 fragmet 显示状态(默认隐藏)

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(getClass().getSimpleName(), "onCreateView: ");
        if (view == null) {
            view = inflater.inflate(getLayoutId(), null);
        }
        initView(view);
        isViewCreate = true;
        isFirstEnter = true;
        // 当第一次进入时 会先调用 1 的 setUserVisibleHint=false ，2 的 setUserVisibleHint=false
        // 然后才是 1 的 setUserVisibleHint =true 之后才会调用 1 的 onCreateView 方法
        // 此时会导致 1 的 onFragmentFirstVisible 在第一次进入时不会调用，
        // 所以需要判断 getUserVisibleHint()=true ，即第一次进入，此时在调用 dispatchVisibleHint（true）触发即可解决
        if (getUserVisibleHint() && !isHidden()) {
            dispatchVisibleHint(true);
        }
        return view;
    }

    /**
     * setUserVisibleHint()在Fragment创建时会先被调用一次，传入isVisibleToUser = false
     * 如果当前Fragment可见，那么setUserVisibleHint()会再次被调用一次，传入isVisibleToUser = true
     * 如果Fragment从可见->不可见，那么setUserVisibleHint()也会被调用，传入isVisibleToUser = false
     * 总结：setUserVisibleHint()除了Fragment的可见状态发生变化时会被回调外，在new Fragment()时也会被回调
     * 如果我们需要在 Fragment 可见与不可见时干点事，用这个的话就会有多余的回调了，那么就需要重新封装一个
     *
     * @param isVisibleToUser 界面是否可见（创建时也会调用 isVisibleToUser=false）
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(getClass().getSimpleName(), "setUserVisibleHint: " + isVisibleToUser);
        if (isViewCreate) {
            if (isVisibleToUser && !currentFragmentVisibleState) {
                dispatchVisibleHint(true);
            } else if (currentFragmentVisibleState && !isVisibleToUser) {
                dispatchVisibleHint(false);
            }
        }
    }

    /**
     * 分发 显示/隐藏状态
     *
     * @param isVisible 显示/因此
     */
    public void dispatchVisibleHint(boolean isVisible) {
        // 如果当前的 Fragment 的分发状态和 isVisible 相同，此时就没有必要分发了
        if (currentFragmentVisibleState == isVisible) {
            return;
        }
        // 记录当前 Fragment 的显示状态
        currentFragmentVisibleState = isVisible;
        if (isVisible) {
            if (isFirstEnter) {
                // 第一次进入的标识改为false
                isFirstEnter = false;
                // 当前fragment首次可见
                onFragmentFirstVisible();
            }
            onFragmentResume();
            dispatchChildVisibleState(true);
        } else {
            onFragmentPause();
            dispatchChildVisibleState(false);
        }
    }

    /**
     * 对子类进行 显示/隐藏 状态的分发
     *
     * @param isChildVisible 显示/隐藏
     */
    public void dispatchChildVisibleState(boolean isChildVisible) {
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        Log.d(getClass().getSimpleName(), "dispatchChildVisibleState: " + (fragments != null && fragments.size() > 0));
        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                Log.d(getClass().getSimpleName(), "dispatchChildVisibleState: " +
                        fragment.getUserVisibleHint() + !fragment.isHidden());
                if (fragment instanceof BaseLazyLoadFragment && fragment.getUserVisibleHint() && !fragment.isHidden()) {
                    ((BaseLazyLoadFragment) fragment).dispatchVisibleHint(isChildVisible);
                }
            }

        }

    }

    // 页面首次可见
    public void onFragmentFirstVisible() {
    }

    // 页面不可见
    public void onFragmentPause() {
        Log.d(getClass().getSimpleName(), "onFragmentPause: ");
    }

    // 页面可见
    public void onFragmentResume() {
        Log.d(getClass().getSimpleName(), "onFragmentResume: ");
    }


    protected abstract void initView(View view);


    protected abstract int getLayoutId();

    @Override
    public void onPause() {
        super.onPause();
        if (currentFragmentVisibleState && getUserVisibleHint() && !isHidden()) {
            dispatchVisibleHint(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!currentFragmentVisibleState && getUserVisibleHint() && !isHidden()) {
            dispatchVisibleHint(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreate = false;// View 是否创建
        isFirstEnter = false;// View 第一次可见
        currentFragmentVisibleState = false;// 保存 fragmet 显示状态(默认隐藏)
    }
}
