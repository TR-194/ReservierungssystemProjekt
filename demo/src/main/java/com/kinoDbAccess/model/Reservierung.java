package com.kinoDbAccess.model;

import java.util.List;

public class Reservierung {
    private long id;
    private long auffuehrungId;
    private List<Long> platzIds;
    private Status status;
    private String email;

    public Reservierung(long id, long auffuehrungId, List<Long> platzIds, Status status, String email) {
        this.id = id;
        this.auffuehrungId = auffuehrungId;
        this.platzIds = platzIds;
        this.status = status;
        this.email = email;
    }

    public Reservierung(long auffuehrungId, List<Long> platzIds, Status status, String email) {
        this.auffuehrungId = auffuehrungId;
        this.platzIds = platzIds;
        this.status = status;
        this.email = email;
    }

    public Reservierung(long id, long auffuehrungId, Status status, String email) {
        this.id = id;
        this.auffuehrungId = auffuehrungId;
        this.status = status;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAuffuehrungId() {
        return auffuehrungId;
    }

    public void setAuffuehrungId(long auffuehrungId) {
        this.auffuehrungId = auffuehrungId;
    }

    public List<Long> getPlatzIds() {
        return platzIds;
    }

    public void setPlatzIds(List<Long> platzIds) {
        this.platzIds = platzIds;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Reservierung{" +
                "id=" + id +
                ", auffuehrungId=" + auffuehrungId +
                ", platzIds=" + platzIds +
                ", status=" + status +
                ", email='" + email + '\'' +
                '}';
    }
}
