package com.example.baidMarcinos.kolkoikrzyzyk.getUsers;

import com.example.baidMarcinos.kolkoikrzyzyk.models.User;

import java.util.List;

public class GetUsersPresenter implements GetUsersContract.Presenter, GetUsersContract.OnGetAllUsersListener {
    private GetUsersContract.View mView;
    private GetUsersInteractor mGetUsersInteractor;

    public GetUsersPresenter(GetUsersContract.View view) {
        this.mView = view;
        mGetUsersInteractor = new GetUsersInteractor(this);
    }

    @Override
    public void getAllUsers() {
        mGetUsersInteractor.getAllUsersFromFirebase();
    }

    @Override
    public void onGetAllUsersSuccess(List<User> users) {
        mView.onGetAllUsersSuccess(users);
    }

    @Override
    public void onGetAllUsersFailure(String message) {
        mView.onGetAllUsersFailure(message);
    }
}