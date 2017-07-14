package com.example.baidMarcinos.kolkoikrzyzyk.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.baidMarcinos.kolkoikrzyzyk.R;
import com.example.baidMarcinos.kolkoikrzyzyk.models.TicTacToe;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * Created by MARCIN on 2017-05-28.
 */

public class TicTacToeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //private static final int VIEW_TYPE_ME = 1;
    //private static final int VIEW_TYPE_OTHER = 2;

    private List<TicTacToe> mTicTacToe;

    public TicTacToeRecyclerAdapter(List<TicTacToe> chats) {
        mTicTacToe = chats;
    }

    public void add(TicTacToe ticTacToe) {
        mTicTacToe.add(ticTacToe);
        notifyDataSetChanged();
        //  notifyItemInserted(mTicTacToe.size() - 1);
    }

    public List<TicTacToe> getTicTacToe(/*View v*/){
        /*for (TicTacToe button: mTicTacToe
             ) {
            Toast.makeText(v.getContext(), button.message, Toast.LENGTH_LONG).show();

        }*/
        return mTicTacToe;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;

        View viewChatMine = layoutInflater.inflate(R.layout.fragment_game_room, parent, false);
        viewHolder = new TicTacToeRecyclerAdapter.MyChatViewHolder(viewChatMine);
        //       break;
        //   case VIEW_TYPE_OTHER:
        //       View viewChatOther = layoutInflater.inflate(R.layout.fragment_chat, parent, false);
        //       viewHolder = new TicTacToeRecyclerAdapter.OtherChatViewHolder(viewChatOther);
        //       break;
        //}


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (TextUtils.equals(mTicTacToe.get(position).senderUid,
                FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            configureMyChatViewHolder((TicTacToeRecyclerAdapter.MyChatViewHolder) holder, position);
        }

    }



    private void configureMyChatViewHolder(TicTacToeRecyclerAdapter.MyChatViewHolder myChatViewHolder, int position) {
        TicTacToe ticTacToe = mTicTacToe.get(position);


        myChatViewHolder.setSign(ticTacToe.playerSign);

    }


    @Override
    public int getItemCount() {
        if (mTicTacToe != null) {
            return mTicTacToe.size();
        }
        return 0;
    }



    private static class MyChatViewHolder extends RecyclerView.ViewHolder {
        // private TextView txtChatMessage, txtUserAlphabet;
        private Button[] ticTacToeButtons = new Button[3];

        public MyChatViewHolder(View itemView) {
            super(itemView);
            // txtChatMessage = (TextView) itemView.findViewById(R.id.text_view_chat_message);
            // txtUserAlphabet = (TextView) itemView.findViewById(R.id.text_view_user_alphabet);
            ticTacToeButtons[0] = (Button) itemView.findViewById(R.id.button0);
            ticTacToeButtons[1] = (Button) itemView.findViewById(R.id.button1);
            ticTacToeButtons[2] = (Button) itemView.findViewById(R.id.button2);
        }

        public void setSign(String id){


            for(int i =0; i<ticTacToeButtons.length;i++){
                if((ticTacToeButtons[i].getId()+"").equals(id))
                    ticTacToeButtons[i].setText("X");
            }
        }
    }

    private static class OtherChatViewHolder extends RecyclerView.ViewHolder {
        private TextView txtChatMessage, txtUserAlphabet;

        public OtherChatViewHolder(View itemView) {
            super(itemView);
//            txtChatMessage = (TextView) itemView.findViewById(R.id.text_view_chat_message);
//            txtUserAlphabet = (TextView) itemView.findViewById(R.id.text_view_user_alphabet);
        }
    }
}
