package cn.wht.fragmentdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TestFragment1 extends BaseLazyLoadFragment {
    private Button open_act;

    @Override
    protected void initView(View view) {
        open_act = view.findViewById(R.id.open_act);
        open_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FirstActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test1;
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
