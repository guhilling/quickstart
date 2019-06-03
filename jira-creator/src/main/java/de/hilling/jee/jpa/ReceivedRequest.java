package de.hilling.jee.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class ReceivedRequest {

    @GeneratedValue
    @Id
    private long id;
    private String type;

    private String project;
    private String description;
    private String summary;
    private LocalDateTime requestedAt;
    private RequestState state = RequestState.FAILED;

    @Override
    public String toString() {
        return String.format("request with id %d and content %s", id, summary);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String title) {
        this.type = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String content) {
        this.summary = content;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

}
