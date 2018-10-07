/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbs;

import javax.persistence.*;
/**
 *
 * @author W8
 */
@Entity
@Table
public class Book {
    
    @Id
    @GeneratedValue
    private Integer bookId;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String author;
        
    @Column(nullable = false)
    private Boolean ageRestricted;
    
    @Column(nullable = false)
    private Integer quantity;

    public Book() {
        bookId = null;
        title = null;
        author = null;
        ageRestricted = null;
        quantity = null;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }  

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getAgeRestricted() {
        return ageRestricted;
    }

    public void setAgeRestricted(Boolean ageRestricted) {
        this.ageRestricted = ageRestricted;
    }
}
