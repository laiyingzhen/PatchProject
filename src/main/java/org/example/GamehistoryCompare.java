package org.example;

import org.example.dto.GameHistoryVO;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GamehistoryCompare {
    public static void main(String[] args) {
        // 輸入檔
        String rsgErrorBatch ="C:\\document\\log\\rsg_batch_error.csv";
        String rsgBatchMySQL = "C:\\document\\log\\rsg_batch_mysql.csv";
        File rsgError = new File(rsgErrorBatch);
        File rsgCorrect = new File(rsgBatchMySQL);
        Map<String, GameHistoryVO> correctDataMap = new HashMap<>();
        // 輸出檔
        String compareFileName = "C:\\document\\log\\RsgCompareResult.txt";
        File compareResult = new File(compareFileName);
        try {
            FileWriter output = new FileWriter(compareResult); // A stream that connects to the text file
            BufferedWriter writer = new BufferedWriter(output);

            BufferedReader reader = new BufferedReader(new FileReader(rsgError));
            String currentLine;
            while((currentLine = reader.readLine()) != null) {
                String[] csvData = currentLine.split(",");
                String betAmount = csvData[1];
                String betTime = csvData[2];
                String gameName = csvData[3];
                String txid = csvData[4];
                String validBetAmount = csvData[5];
                String win = csvData[6];
                String winLose = csvData[7];
                GameHistoryVO dataVo = new GameHistoryVO();
                dataVo.setBetAmount(betAmount);
                dataVo.setValidBetAmount(validBetAmount);
                dataVo.setTxid(txid);
                dataVo.setGameName(gameName);
                dataVo.setWin(win);
                dataVo.setWinLose(winLose);
                dataVo.setBetTime(betTime);
                correctDataMap.put(txid,dataVo);
            }
            BufferedReader mysqlFileReader = new BufferedReader(new FileReader(rsgCorrect));
            String mysqlLine;
            while((mysqlLine = mysqlFileReader.readLine()) != null) {
                String[] mysqlCsvData = mysqlLine.split(",");
                String keyTxid = mysqlCsvData[3];
                if(correctDataMap.containsKey(keyTxid)){
                    System.out.println("資料已存在:"+keyTxid);
                }else{
                    writer.write(mysqlLine + "\n");
                }
            }
            reader.close();
            mysqlFileReader.close();
            writer.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
