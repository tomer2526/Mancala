package com.point.mancala.Animations;

public class Animations{
    public static boolean inTransition = false;


    // print log message
    // importance: the level of importance of this log.
    // if it not that important to print this log (like show that a hole is highlighted) it will be a higher number
    // 1 = the most important and 3 = is not that important
    public static void logAction(String message, int importance)
    {
        if(importance < 3)
        {
            // print just yhe important log
            System.out.println(message);
        }
    }
}
