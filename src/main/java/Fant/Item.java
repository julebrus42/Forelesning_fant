/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fant;

import static Fant.Item.FIND_ALL_ITEMS;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import no.ntnu.tollefsen.auth.User;

/**
 *
 * @author danie
 */

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = FIND_ALL_ITEMS, query = "select i from items")
public class Item implements Serializable {
    
    public static final String FIND_ALL_ITEMS = "ITEM.findAllItems";
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String title;
    private String description;
    private String price;
    
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private User itemsOwner;
    
    @ManyToOne
    private User itemBuyer;
    
    
    
}
