package com.fro.qualitativescoutingapp2025;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Calendar;
import java.util.Objects;

public class ExceptionHandler implements UncaughtExceptionHandler {

    private UncaughtExceptionHandler defaultUEH;
    // Finds the folder that the file will get saved to
    String pathToExternalStorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
    File dir = new File(pathToExternalStorage + "/" + "Errors-"+Values.year);

    public ExceptionHandler() {
        //Gets default exception handler that's executed when uncaught exception terminates a thread (when error)
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
    }

    public void uncaughtException(Thread t, Throwable e) {

        // Write a printable representation of this Throwable
        final Writer stringBuffSync = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringBuffSync);
        e.printStackTrace(printWriter);
        String stacktrace = stringBuffSync.toString();
        printWriter.close();
        writeToFile(stacktrace);

        // Prevents any code getting executed
        defaultUEH.uncaughtException(t, e);
    }

    private void writeToFile(String currentStacktrace) {
        try {
            // Gets the file name
            Calendar calendar = Calendar.getInstance();
            String filename = Values.year+"_ERROR_LOG_" + calendar.getTimeInMillis() + ".txt";

            // Write the file into the folder
            File reportFile = new File(dir, filename);
            FileWriter fileWriter = new FileWriter(reportFile);
            fileWriter.append("This error occurred on ")
                    .append(String.valueOf(calendar.getTime()))
                    .append("\n\n")
                    .append(currentStacktrace)
                    .flush();
            fileWriter.close();

        } catch (Exception e) {
            Log.e("ExceptionHandler", Objects.requireNonNull(e.getMessage()));
        }
    }

}