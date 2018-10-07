/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbs;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author W8
 */
public class CustomerTest {
    
    public CustomerTest() {
    }
    
    private Customer c;
    
    @Before
    public void setUp() {
        c = new Customer();
        c.setCustomerId(1);
        c.setFirstname("Pepa");
        c.setSurname("Zdepa");
    }

    @Test
    public void testGetCustomerId() {
        System.out.println("testing getCustomerId");
        
        assertEquals((int) 1, (int) c.getCustomerId());
    }

    @Test
    public void testSetCustomerId() {
        System.out.println("testing setCustomerId");
        
        c.setCustomerId(2);
        
        assertEquals((int) 2, (int) c.getCustomerId());
    }
    
    @Test
    public void testGetFirstname() {
        System.out.println("testing getFirstname");
        
        assertEquals("Pepa", c.getFirstname());
    }

    @Test
    public void testSetFirstname() {
        System.out.println("testing setFirstname");
        
        c.setFirstname("Pepek");
        
        assertEquals("Pepek", c.getFirstname());
    }

    @Test
    public void testGetSurname() {
        System.out.println("testing getSurname");
        
        assertEquals("Zdepa", c.getSurname());
    }

    @Test
    public void testSetSurname() {
        System.out.println("testing setSurname");
        
        c.setSurname("Rogalo");
        
        assertEquals("Rogalo", c.getSurname());
    }
    
}
