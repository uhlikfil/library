/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbs;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author W8
 */
public class CustomerBookTest {
    
    public CustomerBookTest() {
    }
    
    private CustomerBook cb;
    
    @Before
    public void setUp() {
        Customer c = new Customer();
        c.setFirstname("Pepa");
        c.setSurname("Zdepa");
        
        Book b = new Book();
        b.setTitle("Rogalo");
        b.setAuthor("Nagáno");
        
        cb = new CustomerBook(c.getCustomerId(), b.getBookId());
        
        cb.setLoanId(1);
    }

    @Test
    public void testGetLoanId() {
        System.out.println("testing getLoanId");
                
        assertEquals((int) 1, (int) cb.getLoanId());
    }

    @Test
    public void testSetLoanId() {
        System.out.println("testing setLoanId");
        
        cb.setLoanId(2);
        
        assertEquals((int) 2, (int) cb.getLoanId());
    } 
    
    @Test
    public void testGetFirstname() {
        System.out.println("testing getFirstname");
        
        assertEquals("Pepa", cb.getFirstname());
    }
}
