package org.okiju.pir.model;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement
public class Posts {
    @XmlElement
    private List<Post> post;
    @XmlAttribute
    private String user;
    @XmlAttribute
    private Date dt;

    @XmlTransient
    public List<Post> getPosts() {
        return post;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Posts [post=");
        builder.append(post);
        builder.append(", user=");
        builder.append(user);
        builder.append(", dt=");
        builder.append(dt);
        builder.append("]");
        return builder.toString();
    }

}
