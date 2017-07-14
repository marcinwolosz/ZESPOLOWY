package com.example.baidMarcinos.kolkoikrzyzyk.TicTacToe;

import android.content.Context;

import com.example.baidMarcinos.kolkoikrzyzyk.models.TicTacToe;

/**
 * Created by MARCIN on 2017-05-20.
 */

public class TicTacToeContract {

    public interface View {

        void setSign(String sign);

        void onSendMessageSuccess();

        void onSendMessageFailure(String message);

        void onGetMessagesSuccess(TicTacToe ticTacToe);

        void onGetMessagesFailure(String message);
    }

    interface Presenter {
        void sendMessage(Context context, TicTacToe ticTacToe, String receiverFirebaseToken);

        void getMessage(String senderUid, String receiverUid);
    }

    interface Interactor {
        void sendMessageToFirebaseUser(Context context, TicTacToe ticTacToe, String receiverFirebaseToken);

        void getMessageFromFirebaseUser(String senderUid, String receiverUid);
    }

    interface OnSendMessageListener {

        void onSendMessageSuccess();

        void onSendMessageFailure(String message);
    }

    interface OnGetMessagesListener {
        void setSign(String sign);

        void onGetMessagesSuccess(TicTacToe ticTacToe);

        void onGetMessagesFailure(String message);
    }
}
