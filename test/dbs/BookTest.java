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
public class BookTest {
    
    public BookTest() {
    }
    
    private Book b;

    @Before
    public void setUp() {
        b = new Book();
        b.setBookId(1);
        b.setTitle("Rogalo");
        b.setAuthor("Nagáno");
    }

    @Test
    public void testGetBookId() {
        System.out.println("testing getBookId");
        
        assertEquals((int) 1, (int) b.getBookId());
    }

    @Test
    public void testSetBookId() {
        System.out.println("testing setBookId");
        
        b.setBookId(2);
        
        assertEquals((int) 2, (int) b.getBookId());
    }

    @Test
    public void testGetTitle() {
        System.out.println("testing getTitle");
        
        assertEquals("Rogalo", b.getTitle());
    }

    @Test
    public void testSetTitle() {
        System.out.println("testing setTitle");
        
        b.setTitle("Koloběžka");
        
        assertEquals("Koloběžka", b.getTitle());
    }

    @Test
    public void testGetAuthor() {
        System.out.println("testing getAuthor");
        
        assertEquals("Nagáno", b.getAuthor());
    }

    @Test
    public void testSetAuthor() {
        System.out.println("testing setAuthor");
        
        b.setAuthor("Kryštof Huja");
        
        assertEquals("Kryštof Huja", b.getAuthor());
    }
    
}
