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
public class Customer {
    
    @Id
    @GeneratedValue
    private Integer customerId;

    @Column(nullable = false)
    private String firstname;
    
    @Column(nullable = false)
    private String surname;
    
    @Column(nullable = false)
    private Boolean active;
    
    @Column
    private Long lastActivated;
    
    @Column
    private Integer yearOfBirth;
    
    public Customer() {
        customerId = null;
        firstname = null;
        surname = null;
        active = false;
        lastActivated = null;
        yearOfBirth = null;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getLastActivated() {
        return lastActivated;
    }

    public void setLastActivated(Long lastActivated) {
        this.lastActivated = lastActivated;
    }

    public Integer getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(Integer yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
