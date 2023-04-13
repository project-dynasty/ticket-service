package com.projectdynasty.ticket.controller;

import com.projectdynasty.ticket.payload.Project;
import com.projectdynasty.ticket.payload.Ticket;
import com.projectdynasty.ticket.security.RequestDetails;
import de.alexanderwodarz.code.web.StatusCode;
import de.alexanderwodarz.code.web.rest.ResponseData;
import de.alexanderwodarz.code.web.rest.annotation.PathVariable;
import de.alexanderwodarz.code.web.rest.annotation.RestController;
import de.alexanderwodarz.code.web.rest.annotation.RestRequest;
import de.alexanderwodarz.code.web.rest.authentication.AuthenticationManager;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;

@RestController(path = "/project", produces = MediaType.APPLICATION_JSON)
public class ProjectController {

    @RestRequest(method = "GET")
    public static ResponseData getProjects() {
        RequestDetails details = (RequestDetails) AuthenticationManager.getAuthentication();
        JSONArray result = new JSONArray();
        for (Project project : Project.getForRequest(details)) {
            JSONObject tmp = new JSONObject();
            tmp.put("name", project.getName());
            tmp.put("lead", project.getLead());
            tmp.put("key", project.getProjectKey());
            tmp.put("icon", project.getIcon());
            result.put(tmp);
        }
        return new ResponseData(result.toString(), StatusCode.OK);
    }

    @RestRequest(path = "/{projectId}", method = "GET")
    public static ResponseData getProjectDetails(@PathVariable("projectId") String projectId) {
        Project project = Project.getById(Integer.parseInt(projectId));
        if (project == null)
            return new ResponseData("{}", StatusCode.FORBIDDEN);
        RequestDetails details = (RequestDetails) AuthenticationManager.getAuthentication();
        if (project.getPermission().length() > 0 && !details.hasPermission(project.getPermission()))
            return new ResponseData("{}", StatusCode.FORBIDDEN);
        JSONObject result = new JSONObject();
        result.put("name", project.getName());
        result.put("lead", project.getLead());
        result.put("key", project.getProjectKey());
        result.put("description", project.getDescription());
        result.put("icon", project.getIcon());
        return new ResponseData(result.toString(), StatusCode.OK);
    }

    @RestRequest(path = "/{projectId}/tickets", method = "GET")
    public static ResponseData getProjectTickets(@PathVariable("projectId") String projectId) {
        Project project = Project.getById(Integer.parseInt(projectId));
        if (project == null)
            return new ResponseData("{}", StatusCode.FORBIDDEN);
        RequestDetails details = (RequestDetails) AuthenticationManager.getAuthentication();
        if (project.getPermission().length() > 0 && !details.hasPermission(project.getPermission()))
            return new ResponseData("{}", StatusCode.FORBIDDEN);
        JSONArray tickets = new JSONArray();
        for (Ticket ticket : project.getTickets()) {
            JSONObject tmp = new JSONObject();
            tmp.put("name", ticket.getName());
            tmp.put("created", ticket.getCreated());
            tmp.put("reporter", ticket.getReporter());
            tmp.put("assignee", ticket.getAssignee());
            tmp.put("deadline", ticket.getDeadline());
            tmp.put("effort", ticket.getEffort());
            tmp.put("id", project.getProjectKey()+"-"+ticket.getId());
            tmp.put("lastUpdate", ticket.getLastUpdate());
            tmp.put("repository", ticket.getRepository());
            tmp.put("priority", ticket.getPriority());
            tickets.put(tmp);
        }
        return new ResponseData(tickets.toString(), StatusCode.OK);
    }

}
