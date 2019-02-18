package springproject.project.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
    private final String logFile = "log.txt";
    private PrintWriter writer;
    private static Logger logger = null;


    private Logger() {
        try {
            FileWriter fw = new FileWriter(logFile);
            writer = new PrintWriter(fw, true);
        } catch (IOException e) {}
    }

    public static synchronized Logger getInstance(){
        if(logger == null)
            logger = new Logger();
        return logger;
    }

    public void logCreate (String msg) {
        writer.println("##############");
        writer.println("Creating: " + msg);
        writer.println("##############");
    }

    public void logDelete(String msg) {
        writer.println("##############");
        writer.println("Deleting: " + msg);
        writer.println("##############");
    }

    public void log(String msg) {
        writer.println("##############");
        writer.println(msg);
        writer.println("##############");
    }
}
