package org.okiju.pir.generator;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.idtmatter.insta4j.client.FullInstaClient;
import com.idtmatter.insta4j.jaxb.InstaRecordBean;

public class InstapaperGenerator implements Generator {
    private boolean archiveBookmark;
    private String folder;
    private FullInstaClient client;
    private String limit;

    public InstapaperGenerator(Properties props, String folder, boolean archiveBookmark) {
        this.folder = folder;
        this.archiveBookmark = archiveBookmark;
        this.limit="";
        String username = props.getProperty("instapaper.username");
        String password = props.getProperty("instapaper.password");
        this.client = FullInstaClient.create(username, password);

    }

    public InstapaperGenerator(Properties props, String folder, boolean archiveBookmark, String limit) {
        this(props, folder, archiveBookmark);
        this.limit = limit;
    }

    public Set<String> generate() {
        Set<String> result = new HashSet<String>();
        List<InstaRecordBean> folders = client.listFolders();
        for (InstaRecordBean instafolder : folders) {
            if (folder.equals(instafolder.title)) {
                List<InstaRecordBean> bookmarks = client.listBookmarks(limit, instafolder.folder_id, "");
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
