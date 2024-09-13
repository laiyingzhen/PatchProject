package org.example.dto;

public class GameHistoryVO {
    private String betAmount;
    private String validBetAmount;
    private String win;
    private String winLose;
    private String betTime;
    private String gameName;
    private String txid;

    public String getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(String betAmount) {
        this.betAmount = betAmount;
    }

    public String getValidBetAmount() {
        return validBetAmount;
    }

    public void setValidBetAmount(String validBetAmount) {
        this.validBetAmount = validBetAmount;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public String getWinLose() {
        return winLose;
    }

    public void setWinLose(String winLose) {
        this.winLose = winLose;
    }

    public String getBetTime() {
        return betTime;
    }

    public void setBetTime(String betTime) {
        this.betTime = betTime;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }
}
