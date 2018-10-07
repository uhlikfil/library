/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author W8
 */
@Table
@Entity
public class CustomerBook {
    
    @Id
    @GeneratedValue
    private Integer loanId;
    
    @Column(nullable = false)
    private Integer customerId;
    
    @Column(nullable = false)
    private Integer bookId;

    @Transient
    private DbController controller;
    
    public CustomerBook() {
        controller = new DbController();
        loanId = null;
        
        customerId = null;
        bookId = null;

    }
    
    public CustomerBook(Integer customerId, Integer bookId) {
        controller = new DbController();
        loanId = null;

        this.customerId = customerId;
        
        this.bookId = bookId;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }
    
    public Integer getCustomerId() {
        return customerId;
    }

    public String getFirstname() {
        return controller.findCustomerById(customerId).getFirstname();
    }

    public String getSurname() {
        return controller.findCustomerById(customerId).getSurname();
    }

    public Integer getBookId() {
        return bookId;
    }

    public String getTitle() {
        return controller.findBookById(bookId).getTitle();
    }
    
    public String getAuthor() {
        return controller.findBookById(bookId).getAuthor();
    }

    public void setCustomerId(Integer id) {
        this.customerId = id;
    }

    public void setBookId(Integer id) {
        this.bookId = id;
    }
    
}
