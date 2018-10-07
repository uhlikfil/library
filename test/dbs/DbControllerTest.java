/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author W8
 */
public class DbControllerTest {
    
    public DbControllerTest() {
    }
    
    DbController controller;
    Book b;
    Customer c;
    
    @Before
    public void setUp() {
        controller = new DbController();
    }
    
    @After
    public void tearDown() {
        controller.exit();
    }

    @Test
    public void testFindBookById() {
        System.out.println("testing FindBookById");
        
        Book b1 = controller.findBookById(1);
        
        assertEquals("Rozmarné léto", b1.getTitle());
    }

    @Test
    public void testFindCustomerById() {
        System.out.println("testing FindCustomerById");
        
        Customer c1 = controller.findCustomerById(451);
        
        assertEquals("Alexandra", c1.getFirstname());
    }

    @Test
    public void testFindLoanById() {
        System.out.println("testing findLoanById");
        
        CustomerBook cb = controller.findLoanById(452);
        
        assertEquals("Alexandra", cb.getFirstname());
        assertEquals("Spalovač mrtvol", cb.getTitle());
    }

    @Test
    public void testEditBookById() {
        System.out.println("testing editBookById");
        
        b = controller.findBookById(1);
        String oldTitle = b.getTitle();
        String oldAuthor = b.getAuthor();
        Boolean oldRestriction = b.getAgeRestricted();
        
        assertEquals("Rozmarné léto", b.getTitle());
        
        controller.editBookById(b.getBookId(), "Rogalo", "Nagáno", true);
        
        assertEquals("Rogalo", b.getTitle());
        assertEquals("Nagáno", b.getAuthor());
        assertEquals(true, b.getAgeRestricted());
        
        // get it back to normal
        controller.editBookById(b.getBookId(), oldTitle, oldAuthor, oldRestriction);
    }

    @Test
    public void testEditCustomerById() {
        System.out.println("testing editCustomerById");
        
        c = controller.findCustomerById(451);
        String oldFirstname = c.getFirstname();
        String oldSurname = c.getSurname();
        
        assertEquals("Alexandra", c.getFirstname());
        
        controller.editCustomerById(c.getCustomerId(), "Kryštof", "Huja");
        
        assertEquals("Kryštof", c.getFirstname());
        assertEquals("Huja", c.getSurname());
        
        controller.editCustomerById(c.getCustomerId(), oldFirstname, oldSurname);
    }
    
    @Test
    public void testCheckInput() {
        String badStr = "     ";
        String badStr2 = "Ah";
        String goodStr = "Pepa";
        String goodStr2 = "Tak";
        
        assertEquals(false, controller.checkInput(badStr));
        assertEquals(false, controller.checkInput(badStr2));
        assertEquals(true, controller.checkInput(goodStr));
        assertEquals(true, controller.checkInput(goodStr2));
    }
    
    @Test
    public void testIsInteger() {
        String badInt = "asdf21";
        String goodInt = "12";
        
        assertEquals(false, controller.isInteger(badInt));
        assertEquals(true, controller.isInteger(goodInt));
    }
    
    @Test
    public void activateCustomerById() {
        c = controller.findCustomerById(451);
        c.setActive(Boolean.FALSE);
        
        assertEquals(false, c.getActive());
        
        controller.activateCustomerById(c.getCustomerId());
        
        assertEquals(true, c.getActive());
    }
    
    @Test
    public void testDeactivateCustomers() {
        c = controller.findCustomerById(451);
        controller.activateCustomerById(c.getCustomerId());
        assertEquals(true, c.getActive());
        
        // deactivate but it's 30 days and 9 milliseconds later :)
        controller.deactivateCustomers(System.currentTimeMillis() + Long.parseLong("2592000009"));
        
        assertEquals(false, c.getActive());
    }
}
