package com.example.baidMarcinos.kolkoikrzyzyk.models;

import android.widget.Button;
import android.widget.Toast;

import com.example.baidMarcinos.kolkoikrzyzyk.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.baidMarcinos.kolkoikrzyzyk.TicTacToe.TicTacToeInteractor.game_Room_Name;

/**
 * Created by MARCIN on 2017-06-17.
 */


public class TicTacToeOnline {




    private Button[] ticTacToeButtons = new Button[9];
    String sign;
    String lastMover;
    String endRoundText;
    static String playerMove;

    public Button[] getTicTacToeButtons() {
        return ticTacToeButtons;
    }


    public void setTicTacToeButtons(Button[] ticTacToeButtons) {
        this.ticTacToeButtons = ticTacToeButtons;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getLastMover() {
        return lastMover;
    }

    public void setLastMover(String lastMover) {
        this.lastMover = lastMover;
    }

    public String getEndRoundText() {
        return endRoundText;
    }

    public void setEndRoundText(String endRoundText) {
        this.endRoundText = endRoundText;
    }

    public String getPlayerMove() {
        return playerMove;
    }

    public void setPlayerMove(String playerMove) {
        this.playerMove = playerMove;
    }

    public int countNotEmptyBtn() {
        int countButtons = 0;
        for (int i = 0; i < ticTacToeButtons.length; i++) {
            if (!ticTacToeButtons[i].getText().equals(""))
                countButtons++;
        }
        return countButtons;
    }

    public void randFirstMove(String firstPlayerName, String secondPlayerName){

            if(firstPlayerName.compareTo(secondPlayerName)>0)
                setPlayerMove(firstPlayerName);
            else
                setPlayerMove(secondPlayerName);

    }

    public String setPlayerSign(){
        if(countNotEmptyBtn()%2==0) {
            this.sign = "X";

        }
        else if(countNotEmptyBtn()%2==1) {
            this.sign = "O";
        }
        return sign;
    }

    public Boolean Draw(){

        return countNotEmptyBtn() == 9;
    }

    public Boolean judge(String sign){

        //Horizontal
        return (ticTacToeButtons[0].getText().equals(ticTacToeButtons[1].getText()) && ticTacToeButtons[1].getText().equals(ticTacToeButtons[2].getText()) && ticTacToeButtons[2].getText().equals(sign))
                || (ticTacToeButtons[3].getText().equals(ticTacToeButtons[4].getText()) && ticTacToeButtons[4].getText().equals(ticTacToeButtons[5].getText()) && ticTacToeButtons[5].getText().equals(sign))
                || (ticTacToeButtons[6].getText().equals(ticTacToeButtons[7].getText()) && ticTacToeButtons[7].getText().equals(ticTacToeButtons[8].getText()) && ticTacToeButtons[8].getText().equals(sign))
                //Vertical
                || (ticTacToeButtons[0].getText().equals(ticTacToeButtons[3].getText()) && ticTacToeButtons[3].getText().equals(ticTacToeButtons[6].getText()) && ticTacToeButtons[6].getText().equals(sign))
                || (ticTacToeButtons[1].getText().equals(ticTacToeButtons[4].getText()) && ticTacToeButtons[4].getText().equals(ticTacToeButtons[7].getText()) && ticTacToeButtons[7].getText().equals(sign))
                || (ticTacToeButtons[2].getText().equals(ticTacToeButtons[5].getText()) && ticTacToeButtons[5].getText().equals(ticTacToeButtons[8].getText()) && ticTacToeButtons[8].getText().equals(sign))
                //diagonally
                || (ticTacToeButtons[0].getText().equals(ticTacToeButtons[4].getText()) && ticTacToeButtons[4].getText().equals(ticTacToeButtons[8].getText()) && ticTacToeButtons[8].getText().equals(sign))
                || (ticTacToeButtons[2].getText().equals(ticTacToeButtons[4].getText()) && ticTacToeButtons[4].getText().equals(ticTacToeButtons[6].getText()) && ticTacToeButtons[6].getText().equals(sign));

    }

    private void clearButtons(){
        for(int i=0; i<ticTacToeButtons.length;i++){
            ticTacToeButtons[i].setText("");
        }
    }

    public void resetButtons(String id_game_room){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(Constants.ARG_GAME_ROOMS).child(game_Room_Name).removeValue();
        clearButtons();

    }

    public Boolean endRoundConditions(TicTacToe ticTacToe){
        if(judge(ticTacToe.playerSign)) {
            /* error getApplicationcontext is null */
            setEndRoundText("Wygrał " + ticTacToe.sender);
            return true;
        }
        else if(Draw()) {
            setEndRoundText("Remis!");
            return true;
        }
        return false;
    }
}
