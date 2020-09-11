/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fant;

import java.util.List;
import no.ntnu.tollefsen.auth.Group;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import no.ntnu.tollefsen.auth.AuthenticationService;
import no.ntnu.tollefsen.auth.User;
/**
 *
 * @author danie
 */

@Path("service")
@Stateless
@DeclareRoles({Group.USER})
public class FantService {
    
    
    @Inject
    AuthenticationService authenticationService;
    
    @Inject MailService mailService;
    
    @Context
    SecurityContext securityContext;
    
    @PersistenceContext
    EntityManager em;
    
    /**
     *
     * @return
     */
    @GET
    @Path("items")
    public List<Item> getAllItems() {
        return em.createNamedQuery(Item.FIND_ALL_ITEMS, Item.class).getResultList();
    }
    
    @POST
    @Path("additem")
    @RolesAllowed({Group.USER})
    public Response addItem(
            @FormParam("item") String item,
            @FormParam("description") String description,
            @FormParam("price") int price) {

        User user = this.getCurrentUser();
        Item newItem = new Item();

        newItem.setItemOwner(user);
        newItem.setItem(item);
        newItem.setDescription(description);
        newItem.setPrice(price);

        em.persist(newItem);

        return Response.ok().build();
    }
    
    private User getCurrentUser() {
        return em.find(User.class, securityContext.getUserPrincipal().getName());
    }
    
    @DELETE
    @Path("delete")
    @RolesAllowed({Group.USER})
    public Response deleteItem (
            @QueryParam("itemid") long itemid) {
        Item item = em.find(Item.class, itemid);
        User user = getCurrentUser();
        
        if (item.getItemOwner().getUserid().equals(user.getUserid())) {
            if (item != null) {
                    em.remove(item);
                    return Response.ok(item).build();
                } else {
            return Response.ok("No item was found that could be deleted").build(); }
         
        } else {
            return Response.ok("The account logged in does not match the items owner. Please try logging in with another account!").build();
        }
        
    }
    
//    @GET
//    @Path("send")
//    public Response send() {
//        mailService.sendEmail("daniel.iversen@live.no", "test", "test");
//        return Response.ok().build();
//    }
    
    @PUT
    @Path("buy")
    @RolesAllowed({Group.USER})
    public Response buyItem (
            @QueryParam("itemid") long itemid) {
        Item item = em.find(Item.class, itemid);
        User buyer = getCurrentUser();
        
        if (item != null) {
            if (item.getItemOwner().getUserid().equals(buyer.getUserid())) {
                return Response.ok("You can not buy your own item").build();
            } else {
                if(item.getItemBuyer() == null) {
                    item.setItemBuyer(buyer);
                    mailService.sendEmail(item.getItemOwner().getEmail(), "Your Item has been sold!", "The item was bought by " + item.getItemBuyer().getUserid());
                    return Response.ok("The item is now bought").build();
                } else {
                    return Response.ok("The item is already bought").build();
                }
            }
        }
        
        return Response.ok("OPS!!! Something went wrong").build();
    }   
    
}
