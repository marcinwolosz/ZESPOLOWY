package com.example.baidMarcinos.kolkoikrzyzyk.fragment;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baidMarcinos.kolkoikrzyzyk.Constants;
import com.example.baidMarcinos.kolkoikrzyzyk.R;
import com.example.baidMarcinos.kolkoikrzyzyk.TicTacToe.TicTacToeContract;
import com.example.baidMarcinos.kolkoikrzyzyk.TicTacToe.TicTacToePresenter;
import com.example.baidMarcinos.kolkoikrzyzyk.adapters.TicTacToeRecyclerAdapter;
import com.example.baidMarcinos.kolkoikrzyzyk.chat.ChatPresenter;
import com.example.baidMarcinos.kolkoikrzyzyk.models.TicTacToe;
import com.example.baidMarcinos.kolkoikrzyzyk.models.TicTacToeOnline;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.baidMarcinos.kolkoikrzyzyk.TicTacToe.TicTacToeInteractor.game_Room_Name;

/**
 * Created by MARCIN on 2017-05-28.
 */


public class GameRoomTicTacToeFragment extends android.app.Fragment implements TicTacToeContract.View{


    List<TicTacToe> ticTacToeList;

    Context myContext;

    TextView playerTurn;

    View viewButton;

    TicTacToeOnline ticTacToeModel;
    int firstMover;

    private long mLastClickTime = 0;

    int[] buttonsId = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                       R.id.button5, R.id.button6, R.id.button7, R.id.button8};

    private TicTacToeRecyclerAdapter mTicTacToeRecyclerAdapter;

    private TicTacToePresenter mTicTacToePresenter;

    public static GameRoomTicTacToeFragment newInstance(String receiver,
                                                 String receiverUid,
                                                 String firebaseToken) {
        Bundle args = new Bundle();
        args.putString(Constants.ARG_RECEIVER, receiver);
        args.putString(Constants.ARG_RECEIVER_UID, receiverUid);
        args.putString(Constants.ARG_FIREBASE_TOKEN, firebaseToken);
        GameRoomTicTacToeFragment fragment = new GameRoomTicTacToeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_game_room, container, false);
        bindViews(fragmentView);
        return fragmentView;
    }

    private void setIdButtons(View view){
        ticTacToeModel = new TicTacToeOnline();
        for(int i =0; i<ticTacToeModel.getTicTacToeButtons().length;i++) {
            ticTacToeModel.getTicTacToeButtons()[i] = (Button) view.findViewById(buttonsId[i]);
        }
    }

    private void addListenersToGameButtons(){
        for (final Button b:ticTacToeModel.getTicTacToeButtons()) {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    sendButton(b);
                }
            });
        }
    }

    private void bindViews(View view) {

        myContext = getActivity();

        this.viewButton = view;
        TextView oponentName = (TextView) view.findViewById(R.id.oponentName);
        playerTurn = (TextView) view.findViewById(R.id.playerTurn);

        oponentName.setText(oponentName.getText() + getArguments().getString(Constants.ARG_RECEIVER));
        setIdButtons(view);

        addListenersToGameButtons();

        ticTacToeModel.randFirstMove(FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                getArguments().getString(Constants.ARG_RECEIVER));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {

        mTicTacToePresenter = new TicTacToePresenter(this, FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                                            getArguments().getString(Constants.ARG_RECEIVER_UID), myContext);
        mTicTacToePresenter.getMessage(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                getArguments().getString(Constants.ARG_RECEIVER_UID));


        playerTurn.setText("Start as a first Player");

    }

    public void setSign(String sign){
        ticTacToeModel.setSign(sign);
    }


    private void sendButton(Button button){
        String buttonID = button.getId()+"";
        String playerSign = ticTacToeModel.setPlayerSign();
        String receiver = getArguments().getString(Constants.ARG_RECEIVER);
        String receiverUid = getArguments().getString(Constants.ARG_RECEIVER_UID);
        String sender = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        String senderUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String receiverFirebaseToken = getArguments().getString(Constants.ARG_FIREBASE_TOKEN);

        TicTacToe ticTacToe = new TicTacToe(sender,
                receiver,
                senderUid,
                receiverUid,
                buttonID,
                playerSign,
                System.currentTimeMillis()
        );


        if((ticTacToeModel.countNotEmptyBtn()==0 && ticTacToeModel.getPlayerMove() == null) ||
                (ticTacToeModel.countNotEmptyBtn()>0 && ticTacToe.sender.equals(ticTacToeModel.getPlayerMove()) && button.getText().equals("")))
            mTicTacToePresenter.sendMessage(getActivity().getApplicationContext(),
                    ticTacToe, receiverFirebaseToken);

    }

    //   @Override
    public void onSendMessageSuccess() {
    }

    //   @Override
    public void onSendMessageFailure(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    //   @Override
    public void onGetMessagesSuccess(TicTacToe ticTacToe) {
        super.onResume();
        this.onCreate(null);
        if (mTicTacToeRecyclerAdapter == null) {
            mTicTacToeRecyclerAdapter = new TicTacToeRecyclerAdapter(new ArrayList<TicTacToe>());

            ticTacToeList = mTicTacToeRecyclerAdapter.getTicTacToe();
        }

        mTicTacToeRecyclerAdapter.add(ticTacToe);

        if(ticTacToe.buttonID!=null)
        for(int i=0 ;i < ticTacToeModel.getTicTacToeButtons().length; i++)
            if(ticTacToe.buttonID.equals(ticTacToeModel.getTicTacToeButtons()[i].getId()+"")){
                ticTacToeModel.getTicTacToeButtons()[i].setText(ticTacToe.playerSign);
            }


        ticTacToeModel.setPlayerMove(ticTacToe.receiver);

        ticTacToeModel.setPlayerMove(ticTacToe.receiver);
        if(ticTacToeModel.countNotEmptyBtn()>0 && ticTacToeModel.getPlayerMove()==null)
            ticTacToeModel.setPlayerMove(playerTurn.getText().toString().substring(5));

        if(ticTacToeModel.getPlayerMove()!=null)
            playerTurn.setText("Turn " + ticTacToeModel.getPlayerMove());



        if(ticTacToeModel.endRoundConditions(ticTacToe)){
            Toast.makeText(myContext, ticTacToeModel.getEndRoundText(), Toast.LENGTH_LONG).show();
            ticTacToeModel.resetButtons(ticTacToe.senderUid+"_"+ticTacToe.receiverUid);
            refresh();
        }

    }

    public void refresh(){          //refresh is onClick name given to the button
        try {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //    @Override
    public void onGetMessagesFailure(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.onCreate(null);
    }

}
