package com.example.baidMarcinos.kolkoikrzyzyk.getUsers;


import com.example.baidMarcinos.kolkoikrzyzyk.models.User;

import java.util.List;


public interface GetUsersContract {
    interface View {
        void onGetAllUsersSuccess(List<User> users);

        void onGetAllUsersFailure(String message);

    }

    interface Presenter {
        void getAllUsers();

    }

    interface Interactor {
        void getAllUsersFromFirebase();

        void getChatUsersFromFirebase();
    }

    interface OnGetAllUsersListener {
        void onGetAllUsersSuccess(List<User> users);

        void onGetAllUsersFailure(String message);
    }

}
