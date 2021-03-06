package com.am.my_feed.profile;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.am.my_feed.R;
import com.am.my_feed.databinding.FragmentProfileBinding;
import com.am.my_feed.settings.SettingsActivity;
import com.am.my_feed.util.BaseFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends BaseFragment {
    private static final String ARG_PARAM2 = "param2";

    private FirebaseUser mFirebaseUser;
    private FragmentProfileBinding mBinding;
    private String mTitleParam;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitleParam = getArguments().getString(ARG_TITLE);
            onFragmentInteraction(mTitleParam);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        if (mFirebaseUser != null) {

        if (mFirebaseUser.getPhotoUrl() != null) {
            Glide.with(getContext()).load(mFirebaseUser.getPhotoUrl() + "?sz=500").apply(RequestOptions.circleCropTransform())
                    .into(mBinding.userImageView);

        } else {
            Glide.with(getContext()).load(R.drawable.ic_profile).apply(RequestOptions.circleCropTransform())
                    .into(mBinding.userImageView);

        }
        mBinding.setUser(mFirebaseUser);
        mBinding.executePendingBindings();
            mBinding.settingsImageView.setOnClickListener(v -> {
                Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(settingsIntent);
            });
        }

        return mBinding.getRoot();

    }

    public void onFragmentInteraction(String title) {
        if (mListener != null) {
            mListener.onFragmentInteraction(title);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String title);
    }
}
