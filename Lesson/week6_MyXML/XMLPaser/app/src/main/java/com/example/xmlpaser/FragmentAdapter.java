package com.example.xmlpaser;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentAdapter extends FragmentStateAdapter {
    private SourceFragment sourceFragment;
    private SAXFragment saxFragment;
    private PULLFragment pullFragment;

    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0)
            return sourceFragment;
        else if (position == 1)
            return saxFragment;
        else
            return pullFragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void setFragments(Fragment sourceFragment, Fragment saxFragment, Fragment pullFragment) {
        this.sourceFragment = (SourceFragment) sourceFragment;
        this.saxFragment = (SAXFragment) saxFragment;
        this.pullFragment = (PULLFragment) pullFragment;
        notifyItemChanged(0);
    }
}
