/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jerseyrest;

import com.model.Activity;
import com.model.User;
import com.repository.ActivityRepository;
import com.repository.ActivityRepositoryStub;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 *
 * @author oslysenko
 */
@Path("activities")
public class ActivityResource {

    private ActivityRepository activityRepository = new ActivityRepositoryStub();
    
    @DELETE
    @Path("{activityId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response delete(@PathParam("activityId") String activityId) {
        System.out.println(activityId);
        activityRepository.delete(activityId);
        return Response.ok().build();
    }
    
    
    @PUT
    @Path("{activityId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response update(Activity activity) {
        System.out.println(activity.getId());
        activity = activityRepository.update(activity);
        return Response.ok().entity(activity).build();
    }
    
    
    @POST
    @Path("activity")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Activity createActivity(Activity activity) {
        System.out.println(activity.getDescription());
        System.out.println(activity.getDuration());
        activityRepository.create(activity);
        return activity;
    }
    
    @POST
    @Path("activity")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Activity createActivityParams(MultivaluedMap<String, String> formParams) {
        System.out.println(formParams.getFirst("description"));
        System.out.println(formParams.getFirst("duration"));
        Activity activity = new Activity();
        activity.setDescription(formParams.getFirst("description"));
        activity.setDuration(Integer.parseInt(formParams.getFirst("duration")));
        
        activityRepository.create(activity);
        
        return activity;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Activity> getAllActivities() {
        return activityRepository.findAllActivities();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{activityId}")
    public Response getActivity(@PathParam("activityId") String activityId) {
        if(activityId == null || activityId.length() < 4) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        //System.out.println("Getting activity ID: " + activityId);
        
        Activity activity = activityRepository.findActivity(activityId);
        
        if(activity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        return Response.ok().entity(activity).build(); //activityRepository.findActivity(activityId);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{activityId}/user")
    public User getActivityUser(@PathParam("activityId") String activityId) {
        return activityRepository.findActivity(activityId).getUser();
    }

}
