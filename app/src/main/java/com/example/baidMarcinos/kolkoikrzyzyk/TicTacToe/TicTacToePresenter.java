package com.example.baidMarcinos.kolkoikrzyzyk.TicTacToe;

import android.content.Context;

import com.example.baidMarcinos.kolkoikrzyzyk.models.TicTacToe;


/**
 * Created by MARCIN on 2017-05-20.
 */

public class TicTacToePresenter implements TicTacToeContract.Presenter, TicTacToeContract.OnSendMessageListener,

        TicTacToeContract.OnGetMessagesListener {
    private TicTacToeContract.View mView;
    private TicTacToeInteractor mTicTacToeInteractor;

    public TicTacToePresenter(TicTacToeContract.View view, String senderUid, String receiverUid, Context context) {
        this.mView = view;
        mTicTacToeInteractor = new TicTacToeInteractor(this, this, senderUid, receiverUid, context);
    }



    @Override
    public void sendMessage(Context context, TicTacToe ticTacToe, String receiverFirebaseToken) {
        mTicTacToeInteractor.sendMessageToFirebaseUser(context, ticTacToe, receiverFirebaseToken);
    }

    @Override
    public void getMessage(String senderUid, String receiverUid) {
        mTicTacToeInteractor.getMessageFromFirebaseUser(senderUid, receiverUid);
    }

    @Override
    public void onSendMessageSuccess() {
        mView.onSendMessageSuccess();
    }

    @Override
    public void onSendMessageFailure(String message) {
        mView.onSendMessageFailure(message);
    }

    @Override
    public void setSign(String sign) {
        mView.setSign(sign);
    }

    @Override
    public void onGetMessagesSuccess(TicTacToe ticTacToe) {
        mView.onGetMessagesSuccess(ticTacToe);
    }

    @Override
    public void onGetMessagesFailure(String message) {
        mView.onGetMessagesFailure(message);
    }
}
