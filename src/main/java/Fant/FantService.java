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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import no.ntnu.tollefsen.auth.AuthenticationService;

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
    @RolesAllowed({Group.USER})
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getAllItems() {
        return em.createNamedQuery(Item.FIND_ALL_ITEMS, Item.class).getResultList();
    }   
}
