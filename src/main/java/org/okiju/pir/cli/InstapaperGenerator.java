package org.okiju.pir.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.okiju.pir.generator.Generator;

import com.idtmatter.insta4j.client.FullInstaClient;
import com.idtmatter.insta4j.jaxb.InstaRecordBean;

public class InstapaperGenerator implements Generator {
    private boolean archiveBookmark;
    private String folder;
    private FullInstaClient client;

    public InstapaperGenerator(Properties props, String folder, boolean archiveBookmark) {
        this.folder = folder;
        this.archiveBookmark = archiveBookmark;
        String username = props.getProperty("username");
        String password = props.getProperty("password");
        this.client = FullInstaClient.create(username, password);

    }

    public List<String> generate() {
        List<String> result = new ArrayList<String>();
        List<InstaRecordBean> folders = client.listFolders();
        for (InstaRecordBean instafolder : folders) {
            if (folder.equals(instafolder.title)) {
                List<InstaRecordBean> bookmarks = client.listBookmarks("", instafolder.folder_id, "");
                for (InstaRecordBean bookmark : bookmarks) {
                    if ("bookmark".equals(bookmark.type)) {
                        System.out.println("found bookmark: " + bookmark);
                        result.add(bookmark.url);
                        if (archiveBookmark) {
                            client.archiveBookmark(bookmark.bookmark_id);
                        }
                    }
                }
            }
        }
        return result;
    }
}
