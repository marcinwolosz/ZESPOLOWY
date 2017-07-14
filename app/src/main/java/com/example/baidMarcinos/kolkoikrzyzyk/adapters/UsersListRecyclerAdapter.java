package com.example.baidMarcinos.kolkoikrzyzyk.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baidMarcinos.kolkoikrzyzyk.ImageLoadTask;
import com.example.baidMarcinos.kolkoikrzyzyk.R;
import com.example.baidMarcinos.kolkoikrzyzyk.models.User;

import java.util.List;

/**
 * Created by MARCIN on 2017-05-27.
 */


public class UsersListRecyclerAdapter extends RecyclerView.Adapter<UsersListRecyclerAdapter.ViewHolder> {
    private List<User> mUsers;

    public UsersListRecyclerAdapter(List<User> users) {
        this.mUsers = users;
    }

    public void add(User user) {
        mUsers.add(user);
        notifyItemInserted(mUsers.size() - 1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_user_listing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = mUsers.get(position);

        holder.txtUsername.setText(user.getAppDisplayName());

        new ImageLoadTask(user.getProfilePictureUrl(), holder.profilePicture).execute();
    }

    @Override
    public int getItemCount() {
        if (mUsers != null) {
            return mUsers.size();
        }
        return 0;
    }

    public User getUser(int position) {
        return mUsers.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtUsername;
        private ImageView profilePicture;

        ViewHolder(View itemView) {
            super(itemView);
            profilePicture = (ImageView) itemView.findViewById(R.id.profile_picture);
            txtUsername = (TextView) itemView.findViewById(R.id.text_view_username);
        }
    }
}
