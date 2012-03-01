package org.okiju.pir.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Post {
    @XmlAttribute
    private String href;
    @XmlAttribute
    private Date time;
    @XmlAttribute
    private String description;
    @XmlAttribute
    private String extended;
    @XmlAttribute
    private String tag;
    @XmlAttribute
    private String hash;
    @XmlAttribute
    private String shared;

    public String getHref() {
        return href;
    }

    public Date getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getExtended() {
        return extended;
    }

    public String getTag() {
        return tag;
    }

    public String getHash() {
        return hash;
    }

    public String getShared() {
        return shared;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Post [href=");
        builder.append(href);
        builder.append(", time=");
        builder.append(time);
        builder.append(", description=");
        builder.append(description);
        builder.append(", extended=");
        builder.append(extended);
        builder.append(", tag=");
        builder.append(tag);
        builder.append(", hash=");
        builder.append(hash);
        builder.append(", shared=");
        builder.append(shared);
        builder.append("]");
        return builder.toString();
    }

}
