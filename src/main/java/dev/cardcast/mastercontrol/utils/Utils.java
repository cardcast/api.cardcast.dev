package dev.cardcast.mastercontrol.utils;

import com.google.common.io.Resources;
import com.google.gson.Gson;
import lombok.Getter;

import java.io.IOException;
import java.nio.charset.Charset;

public class Utils {

    @Getter
    public static final Gson gson = new Gson();

    public static String readResource(final String fileName, Charset charset) throws IOException {
        return Resources.toString(Resources.getResource(fileName), charset);
    }
}
