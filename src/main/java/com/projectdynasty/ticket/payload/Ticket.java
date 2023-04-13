package com.projectdynasty.ticket.payload;

import com.projectdynasty.ticket.TicketService;
import com.projectdynasty.ticket.model.TicketData;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class Ticket {

    private final TicketData data;

    public static Ticket getById(int id) {
        TicketData data = (TicketData) TicketService.DATABASE.getTable(TicketData.class).query().addParameter("id", id).executeOne();
        if (data == null)
            return null;
        return new Ticket(data);
    }

    public static List<Ticket> getFromProjectId(int projectId){
        List<Ticket> tickets = new ArrayList<>();
        for (Object id : TicketService.DATABASE.getTable(TicketData.class).query().addParameter("projectId", projectId).executeMany()) {
            tickets.add(new Ticket((TicketData) id));
        }
        return tickets;
    }

    public int getId() {
        return data.id;
    }

    public long getAssignee() {
        return data.assignee;
    }

    public Ticket setAssignee(long userId) {
        update("assignee", userId);
        data.assignee = userId;
        return this;
    }

    public long getReporter() {
        return data.reporter;
    }

    public Ticket setReporter(long userId) {
        update("reporter", userId);
        data.reporter = userId;
        return this;
    }

    public long getDeadline() {
        return data.deadline;
    }

    public Ticket setDeadline(long deadline) {
        update("deadline", deadline);
        data.deadline = deadline;
        return this;
    }

    public String getName() {
        return data.name;
    }

    public Ticket setName(String name) {
        update("name", name);
        data.name = name;
        return this;
    }

    public String getDescription() {
        return data.description;
    }

    public Ticket setDescription(String description) {
        update("description", description);
        data.description = description;
        return this;
    }

    public int getStatus() {
        return data.status;
    }

    public Ticket setStatus(int status) {
        update("status", status);
        data.status = status;
        return this;
    }

    public long getCreated() {
        return data.created;
    }

    public String getRepository() {
        return data.repository;
    }

    public Ticket setRepository(String repository) {
        update("repository", repository);
        data.repository = repository;
        return this;
    }

    public int getPriority() {
        return data.priority;
    }
    public Ticket setPriority(int priority) {
        update("priority", priority);
        data.priority = priority;
        return this;
    }


    public int getEffort() {
        return data.effort;
    }
    public Ticket setEffort(int effort) {
        update("effort", effort);
        data.effort = effort;
        return this;
    }


    public long getLastUpdate() {
        return data.lastUpdate;
    }

    public boolean isDeleted() {
        return data.deleted;
    }
    public Ticket setDeleted(boolean deleted) {
        update("deleted", deleted ? 1 : 0);
        data.deleted = deleted;
        return this;
    }


    private void update(String column, Object value) {
        data.lastUpdate = System.currentTimeMillis() / 1000;
        TicketService.DATABASE.update("UPDATE ticket_data SET `" + column + "`='" + value + "', lastUpdate='" + data.lastUpdate + "' WHERE id='" + getId() + "';", null);
    }

}
