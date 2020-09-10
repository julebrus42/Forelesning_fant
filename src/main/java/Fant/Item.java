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
@Table(name = "Items")
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name = FIND_ALL_ITEMS, query = "select i from Item i")
public class Item implements Serializable {

    public static final String FIND_ALL_ITEMS = "Item.findAllItems";
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemid;

    private String item;
    private String description;
    private int price;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User itemOwner;
    
    @ManyToOne
    private User itemBuyer;

}

