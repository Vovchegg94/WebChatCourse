package ru.netology;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class Logger {
    static java.util.logging.Logger LOGGER;
    public static void writeToLog(String message) {
        try {
            LOGGER.log(Level.INFO, message);

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "что-то пошло не так", e);
        }
    }


    static {
        try (FileInputStream ins = new FileInputStream("C:/Users/79056/IdeaProjects/WebChatCourse/Server/src/main/java/ru/netology/log.config")) {
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = java.util.logging.Logger.getLogger(Main.class.getName());
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }

    }
}




