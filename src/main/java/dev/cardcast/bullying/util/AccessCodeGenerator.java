package dev.cardcast.bullying.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AccessCodeGenerator {

    private static List<String> previouslyGeneratedCodes = new ArrayList<String>();
    private static Random random = new Random();

    public static String generate() {
        while (true) {
            String generatedCode = String.format("%05d", random.nextInt(100000));
            if (!previouslyGeneratedCodes.contains(generatedCode)) {
                previouslyGeneratedCodes.add(generatedCode);
                return generatedCode;
            }
        }
    }
}
