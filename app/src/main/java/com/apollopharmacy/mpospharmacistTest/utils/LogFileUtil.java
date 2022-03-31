package com.apollopharmacy.mpospharmacistTest.utils;

import android.content.Context;
import android.os.Environment;

import com.androidnetworking.utils.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LogFileUtil {
    public static File createdFileName = null;

    public static StringBuilder readLogs() {
        StringBuilder logBuilder = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec("logcat -d");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                logBuilder.append(line + "\n");
            }
        } catch (IOException e) {
        }
        return logBuilder;
    }

    public static void createLogFolder(Context context) {
        if (isExternalStorageWritable()) {
            File appDirectory = new File(Environment.getExternalStorageDirectory() + "/FolderName");
            File logFile = new File(appDirectory, "logcat.txt");
            StringBuilder log = null;

            // create app folder
            if (!appDirectory.exists()) {
                appDirectory.mkdir();
            }
            try {
                Process process = Runtime.getRuntime().exec("logcat -d");
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));

                log = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    log.append(line + "\n");
                }
                //Utils.printMessage("LogsUtil", "File Created ===::::::::=== File Name :: " + log.toString());
            } catch (IOException e) {
            }

            try {
                //to write logcat in text file
                FileOutputStream fOut = new FileOutputStream(logFile);
                OutputStreamWriter osw = new OutputStreamWriter(fOut);

                // Write the string to the file
                osw.write(log.toString());
                osw.flush();
                osw.close();
               // Utils.printMessage("LogsUtil", "File Inserted  :=========:::::::=========: ");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (isExternalStorageReadable()) {
            // only readable
        } else {
            // not accessible
        }
    }

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static File createBinaryFile(Context context) {
        File binFile = null;
        if (isExternalStorageWritable()) {
            File appDirectory = new File(Environment.getExternalStorageDirectory() + "/DALite");
            binFile = new File(appDirectory, "binLog.txt");

            // create app folder
            if (!appDirectory.exists()) {
                appDirectory.mkdir();
            }
            return binFile;
        }
        return binFile;
    }
}
