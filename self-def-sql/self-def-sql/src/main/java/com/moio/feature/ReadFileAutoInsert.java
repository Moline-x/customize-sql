package com.moio.feature;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @description new feature for reading file auto insert sql.
 *
 * @author molinchang
 */
public class ReadFileAutoInsert {

    public static void main(String[] args) {
        try {
            String readString = Files.readString(Path.of("C:\\Users\\molinchang\\Documents\\job\\4_IMP\\TRAINING_MATERIAL\\LIVE_SUPPORT\\LIVE_CHECKING_SQL.sql"));
            if (readString.contains("--")) {
                int indexPrefix = readString.indexOf("--");
                int indexSuffix = readString.indexOf("    ");
                String s = readString.replaceAll("^--$   ", "");
                System.out.println(s);
            }
            System.out.println(readString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
