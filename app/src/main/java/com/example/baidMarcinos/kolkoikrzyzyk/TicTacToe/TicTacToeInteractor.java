package com.example.baidMarcinos.kolkoikrzyzyk.TicTacToe;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.baidMarcinos.kolkoikrzyzyk.Constants;
import com.example.baidMarcinos.kolkoikrzyzyk.models.TicTacToe;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.baidMarcinos.kolkoikrzyzyk.Constants.firstPlayerID;
import static com.example.baidMarcinos.kolkoikrzyzyk.Constants.secondPlayerID;

/**
 * Created by MARCIN on 2017-05-20.
 */


public class TicTacToeInteractor implements TicTacToeContract.Interactor {

        private static final String TAG = "ticTacToeInteractor";

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        public static String game_Room_Name;
        private TicTacToeContract.OnSendMessageListener mOnSendMessageListener;
        private TicTacToeContract.OnGetMessagesListener mOnGetMessagesListener;

        public TicTacToeInteractor(TicTacToeContract.OnSendMessageListener onSendMessageListener) {
                this.mOnSendMessageListener = onSendMessageListener;
        }

        public TicTacToeInteractor(TicTacToeContract.OnGetMessagesListener onGetMessagesListener) {
                this.mOnGetMessagesListener = onGetMessagesListener;
        }

        public TicTacToeInteractor(TicTacToeContract.OnSendMessageListener onSendMessageListener,
                                   TicTacToeContract.OnGetMessagesListener onGetMessagesListener, String sender, String receiver, Context context) {
                this.mOnSendMessageListener = onSendMessageListener;
                this.mOnGetMessagesListener = onGetMessagesListener;

                createGameRoom(sender, receiver, context);
        }

        private void createGameRoom(final String senderUid, final String receiverUid, final Context context){

                int compare = senderUid.compareTo(receiverUid);
                if(compare<0)
                    game_Room_Name = senderUid + "_" + receiverUid;
                else
                    game_Room_Name = receiverUid + "_" + senderUid;

                    databaseReference.child(Constants.ARG_GAME_ROOMS).child(game_Room_Name).child("Game Info").child("Status").setValue("connected");

                }





        public void changeSignAfterRound(String senderUid, String receiverUid){
            databaseReference.child(Constants.ARG_GAME_ROOMS).child(game_Room_Name).child("Players Sign").child(senderUid).setValue("X");
            databaseReference.child(Constants.ARG_GAME_ROOMS).child(game_Room_Name).child("Players Sign").child(receiverUid).setValue("O");

        }

        @Override
        public void sendMessageToFirebaseUser(final Context context, final TicTacToe ticTacToe, final String receiverFirebaseToken) {



                //final String finalRoom_type = game_Room_Name;
                databaseReference.child(Constants.ARG_GAME_ROOMS).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                                 if (dataSnapshot.hasChild(game_Room_Name)) {
                                        Log.e(TAG, "sendMessageToFirebaseUser: " + game_Room_Name + " exists");
                                        databaseReference.child(Constants.ARG_GAME_ROOMS).child(game_Room_Name).child(String.valueOf(ticTacToe.timestamp)).setValue(ticTacToe);
                                 } else {
                                        Log.e(TAG, "sendMessageToFirebaseUser: success");
                                        databaseReference.child(Constants.ARG_GAME_ROOMS).child(game_Room_Name).child(String.valueOf(ticTacToe.timestamp)).setValue(ticTacToe);
                                        getMessageFromFirebaseUser(ticTacToe.senderUid, ticTacToe.receiverUid);
                                 }

                                mOnSendMessageListener.onSendMessageSuccess();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                                mOnSendMessageListener.onSendMessageFailure("Unable to send message: " + databaseError.getMessage());
                        }
                });
        }

        public void setSignForPlayer(String playerUid){
                databaseReference.child(Constants.ARG_GAME_ROOMS).child(game_Room_Name).child("Players Sign").child(playerUid).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                                String sign = dataSnapshot.getValue(String.class);
                                mOnGetMessagesListener.setSign(sign);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                });

        }

        @Override
        public void getMessageFromFirebaseUser(String senderUid, String receiverUid) {

                databaseReference.child(Constants.ARG_GAME_ROOMS).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild(game_Room_Name)) {
                                Log.e(TAG, "getMessageFromFirebaseUser: " + game_Room_Name + " exists");
                                FirebaseDatabase.getInstance()
                                .getReference()
                                .child(Constants.ARG_GAME_ROOMS)
                                .child(game_Room_Name).addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                TicTacToe ticTacToe = dataSnapshot.getValue(TicTacToe.class);
                                                mOnGetMessagesListener.onGetMessagesSuccess(ticTacToe);
                                        }

                                        @Override
                                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                                        }

                                        @Override
                                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                                mOnGetMessagesListener.onGetMessagesFailure("Unable to get message: " + databaseError.getMessage());
                                        }
                                });
                                } else {
                                        Log.e(TAG, "getMessageFromFirebaseUser: no such room available");
                                }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                                mOnGetMessagesListener.onGetMessagesFailure("Unable to get message: " + databaseError.getMessage());
                        }
                });
        }
}
