package com.projectdynasty.ticket.payload;

import com.projectdynasty.ticket.TicketService;
import com.projectdynasty.ticket.model.ProjectData;
import com.projectdynasty.ticket.security.RequestDetails;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Project {

    private final ProjectData data;

    public static List<Project> getForRequest(RequestDetails details) {
        return getAll().stream().filter(p -> p.getPermission().length() == 0 || details.hasPermission(p.getPermission())).collect(Collectors.toList());
    }

    public static Project getById(int id) {
        ProjectData data = (ProjectData) TicketService.DATABASE.getTable(ProjectData.class).query().addParameter("id", id).executeOne();
        if (data == null)
            return null;
        return new Project(data);
    }

    public static List<Project> getAll() {
        List<Project> projects = new ArrayList<>();
        for (Object o : TicketService.DATABASE.getTable(ProjectData.class).query().executeMany()) {
            projects.add(new Project((ProjectData) o));
        }
        return projects;
    }

    public List<Ticket> getTickets() {
        return Ticket.getFromProjectId(getId());
    }

    public int getId() {
        return data.id;
    }

    public String getName() {
        return data.name;
    }

    public String getIcon() {
        return data.icon;
    }

    public String getPermission() {
        return data.permission;
    }

    public long getLead() {
        return data.lead;
    }

    public String getDescription() {
        return data.description;
    }

    public String getProjectKey() {
        return data.projectKey;
    }

}
