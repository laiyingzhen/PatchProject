package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static final String searchGroup1 = "[PG - removeDuplicateData] insert game history count::";
    public static final String searchGroup2 = "[PG - removeDuplicateData] skip Not Exist Account Count::";
    public static final String searchGroup3 = "c.s.thirdpartyapi.dao.GameHistoryDAO - addEGamesGameHistoryReWrite filterList size::";
    public static final String searchGroup4 = "[PG流程] EGAME注單筆數:";
    public static final String searchGroup5 = "[MongoDB][JOB流程]注單金額為0，不寫入資料庫, 總筆數:";
    public static final String searchGroup6 = "[MongoDB][MongoBaseDAO.batchInsert]insert count:";
    public static final String searchGroup7 = "[MongoDB][MongoBaseDAO.batchInsert]insert ignore count:";
    public static final String searchGroup8 = "[MongoDB][JOB流程]MongoDB注單新增筆數:";
    public static Map<String, Integer> countMap = new HashMap<>();
    public static List<String> errorDetails = new ArrayList<>();

    private static boolean isMatchString(String line) {
        if (line.indexOf(searchGroup1) > 0) return true;
        if (line.indexOf(searchGroup2) > 0) return true;
        if (line.indexOf(searchGroup3) > 0) return true;
        if (line.indexOf(searchGroup4) > 0) return true;
        if (line.indexOf(searchGroup5) > 0) return true;
        if (line.indexOf(searchGroup6) > 0) return true;
        if (line.indexOf(searchGroup7) > 0) return true;
        if (line.indexOf(searchGroup8) > 0) return true;
        return false;
    }

    private static boolean isGroupStart(String line) {
        if (line.indexOf(searchGroup1) > 0) return true;
        return false;
    }

    private static boolean isGroupEnd(String line) {
        if (line.indexOf(searchGroup8) > 0) return true;
        return false;
    }

    public static void calCountFromLog(String line) {

        if (line.indexOf(searchGroup1) > 0) {
            String[] group1 = line.split("::");
            if (countMap.get("totalCount") != null) {
                countMap.put("totalCount", countMap.get("totalCount") + Integer.parseInt(group1[1]));
            } else {
                countMap.put("totalCount", Integer.parseInt(group1[1]));
            }
        }
        if (line.indexOf(searchGroup2) > 0) {

        }
        if (line.indexOf(searchGroup3) > 0) {
            String[] group1 = line.split("::");
            if (countMap.get("insertedCount") != null) {
                countMap.put("insertedCount", countMap.get("insertedCount") + Integer.parseInt(group1[1]));
            } else {
                countMap.put("insertedCount", Integer.parseInt(group1[1]));
            }
        }
        if (line.indexOf(searchGroup4) > 0) {

        }
        if (line.indexOf(searchGroup5) > 0) {
            int loc1 = line.indexOf("總筆數");
            String mongoCountStr = line.substring(loc1);
            String[] splitCount = mongoCountStr.split(",");
            String[] splitTotal = splitCount[0].split(":");
            String[] splitIgnore = splitCount[1].split(":");
            int total = Integer.parseInt(splitTotal[1]);
            int ignore = Integer.parseInt(splitIgnore[1]);
            int inserted = total - ignore;
            if (countMap.get("mongoInsertedCount") != null) {
                countMap.put("mongoTotalCount", countMap.get("mongoTotalCount") + total);
                countMap.put("mongoIgnoreCount", countMap.get("mongoIgnoreCount") + ignore);
                countMap.put("mongoInsertedCount", countMap.get("mongoInsertedCount") + inserted);
            } else {
                countMap.put("mongoTotalCount", total);
                countMap.put("mongoIgnoreCount", ignore);
                countMap.put("mongoInsertedCount", inserted);
            }
        }
        if (line.indexOf(searchGroup6) > 0) {
            String[] mongoInsertedStr = line.split(":");
            int dbInserted = Integer.parseInt(mongoInsertedStr[1]);
            if (countMap.get("mongoDBInsertedCount") != null) {
                countMap.put("mongoDBInsertedCount", countMap.get("mongoDBInsertedCount") + dbInserted);
            } else {
                countMap.put("mongoDBInsertedCount", dbInserted);
            }
        }
        if (line.indexOf(searchGroup7) > 0) {
            String[] mongoIgnoreStr = line.split(":");
            int dbIgnored = Integer.parseInt(mongoIgnoreStr[1]);
            if (countMap.get("mongoDBInsertedCount") != null) {
                countMap.put("mongoDBInsertedCount", countMap.get("mongoDBInsertedCount") + dbIgnored);
            } else {
                countMap.put("mongoDBInsertedCount", dbIgnored);
            }
        }
    }

    public static void main(String[] args) {

        // 輸入檔
        String lo = "C:\\document\\log\\npf-thirdApiV1_log.2024-07-10-00_0.log";
        File logFile = new File(lo);
        // 輸出檔
        String parseResultStr = "C:\\document\\log\\PGParseResult.txt";
        File parseResult = new File(parseResultStr);

        boolean inGroup = false;
        try {
            // 輸出檔
            FileWriter output = new FileWriter(parseResult); // A stream that connects to the text file
            BufferedWriter writer = new BufferedWriter(output);

            BufferedReader reader = new BufferedReader(new FileReader(logFile));
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (isMatchString(currentLine)) {
                    if (isGroupStart(currentLine)) {
                        inGroup = true;
                    }
                    if (inGroup) {
                        calCountFromLog(currentLine);
                        writer.write(currentLine + "\n");
                    }
                    if (isGroupEnd(currentLine)) {
                        inGroup = false;
                    }
                }
            }
            int totalCount = countMap.get("totalCount");
            int insertedCount = countMap.get("insertedCount");
            int mongoTotal = countMap.get("mongoTotalCount");
            int mongoInserted = countMap.get("mongoInsertedCount");
            int mongoIgnore = countMap.get("mongoIgnoreCount");
            writer.write("總筆數：" + totalCount + "\n");
            writer.write("新增筆數：" + insertedCount + "\n");
            writer.write("Mongo總筆數：" + mongoTotal + "\n");
            writer.write("Mongo新增筆數：" + mongoInserted + "\n");
            writer.write("Mongo跳過筆數：" + mongoIgnore + "\n");
            for (String errorDetail : errorDetails) {
                writer.write("注意：筆數不一致：" + errorDetail + "\n");
            }
            reader.close();
            writer.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}