package net.in.pSwitch.utility;

import java.util.Random;

public class PasswordGenerator {

    private static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUM = "0123456789";
    private static final String SPL_CHARS = "!@#$%^&*_=+-/";

    private static final int NO_OF_CAPS_ALPHA = 1;
    private static final int NO_OF_DIGITS = 1;
    private static final int NO_OF_SPL_CHARS = 1;
    private static final int MIN_LEN = 8;
    private static final int MAX_LEN = 12;

    public static String generatePassword() {
        Random rnd = new Random();
        int len = rnd.nextInt(MAX_LEN - MIN_LEN + 1) + MIN_LEN;
        char[] pswd = new char[len];
        int index = 0;
        for (int i = 0; i < NO_OF_CAPS_ALPHA; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = ALPHA_CAPS.charAt(rnd.nextInt(ALPHA_CAPS.length()));
        }
        for (int i = 0; i < NO_OF_DIGITS; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = NUM.charAt(rnd.nextInt(NUM.length()));
        }
        for (int i = 0; i < NO_OF_SPL_CHARS; i++) {
            index = getNextIndex(rnd, len, pswd);
            pswd[index] = SPL_CHARS.charAt(rnd.nextInt(SPL_CHARS.length()));
        }
        for (int i = 0; i < len; i++) {
            if (pswd[i] == 0) {
                pswd[i] = ALPHA.charAt(rnd.nextInt(ALPHA.length()));
            }
        }
        return String.valueOf(pswd);
    }

    private static int getNextIndex(Random rnd, int len, char[] pswd) {
        int index = rnd.nextInt(len);
        while (pswd[index = rnd.nextInt(len)] != 0) ;
        return index;
    }
}
