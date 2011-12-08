package org.okiju.pir.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileHelper {

    private static transient Logger logger = LoggerFactory.getLogger(FileHelper.class);

    public static void writeCollectionInDatedFile(String prefix, List<String> twits) {
        String filename = generateFilename(prefix);
        File file = new File(filename);

        try {
            FileUtils.writeLines(file, twits);
        } catch (IOException e) {
            logger.error("Error while writing dated file", e);
        }
    }

    public static String generateFilename(String prefix) {
        String DATE_FORMAT = "dd_MM_yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

        String filename = prefix + "_" + sdf.format(new Date()) + ".txt";
        return filename;
    }
}
