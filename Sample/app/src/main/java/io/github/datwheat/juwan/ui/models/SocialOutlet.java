package io.github.datwheat.juwan.ui.models;

import android.net.Uri;

public class SocialOutlet {
    private String name;
    private Uri link;
    private int iconResourceId;

    public SocialOutlet(String name, Uri link, int iconResourceId) {
        this.name = name;
        this.link = link;
        this.iconResourceId = iconResourceId;
    }

    public String getName() {
        return name;
    }

    public Uri getLink() {
        return link;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }
}
