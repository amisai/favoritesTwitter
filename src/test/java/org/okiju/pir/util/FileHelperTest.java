package org.okiju.pir.util;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.okiju.pir.model.Entry;
import org.testng.annotations.Test;

public class FileHelperTest {
    @Test
    public void shouldCreateAFileWithInfo() {
        List<Entry> data = new ArrayList<Entry>();
        data.add(new Entry("data"));
        String prefix = "./target/prefix";
        File file = new File(FileHelper.generateFilename(prefix));
        file.delete();
        FileHelper.writeCollectionInDatedFile(prefix, data);
        assertTrue(file.exists(), "file doesn't exist");
    }
}
