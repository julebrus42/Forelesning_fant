/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fant;

import no.ntnu.tollefsen.auth.Group;
import javax.annotation.security.DeclareRoles;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Path;

/**
 *
 * @author danie
 */

@Path("service")
@Stateless
@DeclareRoles({Group.USER})
public class FantService {
    
    @PersistenceContext
    EntityManager em;
    
    


    
}
