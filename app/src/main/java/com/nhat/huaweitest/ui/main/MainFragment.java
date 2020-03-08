package com.nhat.huaweitest.ui.main;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.nhat.huaweitest.R;
import com.nhat.huaweitest.common.Resource;
import com.nhat.huaweitest.common.ResourceState;

import java.util.Locale;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private TextView mAInput;
    private TextView mBInput;
    private TextView mResult;
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainViewModel.Factory factory = new MainViewModel.Factory(requireActivity().getApplication());

        mViewModel = ViewModelProviders.of(requireActivity(), factory).get(MainViewModel.class);
        mViewModel.getLiveData().observe(this.getViewLifecycleOwner(), new Observer<Resource<Long>>() {
            @Override
            public void onChanged(Resource<Long> longResource) {
                mAInput.setEnabled(longResource.state != ResourceState.LOADING);
                mAInput.setEnabled(longResource.state != ResourceState.LOADING);
                if (longResource.state == ResourceState.SUCCESS) {
                    mResult.setText(String.format(Locale.getDefault(),"%d", longResource.data));
                } else if (longResource.state == ResourceState.ERROR) {
                    Toast.makeText(getContext(), "There is an error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAInput = view.findViewById(R.id.etA);
        mBInput = view.findViewById(R.id.etB);
        mResult = view.findViewById(R.id.tvResult);

        view.findViewById(R.id.btnCalculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                Pair<Long, Long> input = validateInput();
                if (input != null) {
                    mViewModel.beginCalculate(input.first, input.second);
                }
            }
        });
    }

    private void reset() {
        mResult.setText("");
    }

    private Pair<Long, Long> validateInput() {
        String aInputStr = mAInput.getText().toString();
        if (aInputStr.trim().isEmpty()) {
            mAInput.setError("It must be not empty");
            return null;
        } else {
            mAInput.setError(null);
        }

        String bInputStr = mBInput.getText().toString();
        if (bInputStr.trim().isEmpty()) {
            mBInput.setError("It must be not empty");
            return null;
        } else {
            mBInput.setError(null);
        }

        try {
            return Pair.create(Long.parseLong(aInputStr), Long.parseLong(bInputStr));
        } catch (Exception e) {
            return null;
        }
    }
}
