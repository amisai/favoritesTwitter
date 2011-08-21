package org.okiju.favoritestwitter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FileHelper {
    public static void writeCollectionInDatedFile(String prefix, List<String> twits) {
        String filename = generateFilename(prefix);
        File file = new File(filename);

        try {
            FileUtils.writeLines(file, twits);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateFilename(String prefix) {
        String DATE_FORMAT = "dd_MM_yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

        String filename = prefix + "_" + sdf.format(new Date()) + ".txt";
        return filename;
    }
}
