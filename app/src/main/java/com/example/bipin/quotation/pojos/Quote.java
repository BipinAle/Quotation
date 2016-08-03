package com.example.bipin.quotation.pojos;

import java.util.ArrayList;

/**
 * Created by bipin on 7/28/16.
 */
public class Quote {
    private  String groupCode,content,submittedBy,pusblishStatus,dateCreated,id;

    public Quote(String groupCode, String content, String submittedBy, String pusblishStatus, String dateCreated, String id) {
        this.groupCode = groupCode;
        this.content = content;
        this.submittedBy = submittedBy;
        this.pusblishStatus = pusblishStatus;
        this.dateCreated = dateCreated;
        this.id = id;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public String getContent() {
        return content;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public String getPusblishStatus() {
        return pusblishStatus;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getId() {
        return id;
    }
}
