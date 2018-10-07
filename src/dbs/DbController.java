/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbs;

import java.time.Year;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.*;
/**
 *
 * @author W8
 */
public class DbController {

    EntityManagerFactory emf;
    EntityManager em;
    EntityTransaction et;
    
    public DbController() {
        emf = Persistence.createEntityManagerFactory("DBSPU");
        em = emf.createEntityManager();
        et = em.getTransaction();
    }
    
    public void addBook(String title, String author, Boolean ageRestr, Integer quantity) {
        et.begin();
            Book b = new Book();
            b.setTitle(title);
            b.setAuthor(author);
            b.setAgeRestricted(ageRestr);
            b.setQuantity(quantity);
        em.persist(b);
        et.commit();
    }
    
    public Book findBookById(int id) {
        Book b = em.find(Book.class, id);
        return b;
    }
    
    public Customer findCustomerById(int id) {
        Customer c = em.find(Customer.class, id);
        return c;
    }
    
    public CustomerBook findLoanById(int id) {
        CustomerBook cb = em.find(CustomerBook.class, id);
        return cb;
    }
    
    public void editBookById(Integer id, String newTitle, String newAuthor, Boolean ageRestr) {
        et.begin();
            Book b = findBookById(id);
            if (b != null) {
                b.setTitle(newTitle);
                b.setAuthor(newAuthor);
                b.setAgeRestricted(ageRestr);
                em.merge(b);
            }
        et.commit();
    }
    
    public void removeBookById(Integer id) {
        et.begin();
            Book b = findBookById(id);
            if (b != null) {
                em.remove(b);
            }
        et.commit();
    }
    
    public boolean isBookLent(Book b) {
        List<CustomerBook> allLoans = getAllLoans();
        
        for (CustomerBook cb : allLoans) {
            if (cb.getBookId().equals(b.getBookId())) { return true; }
        }
        return false;
    }
    
    public void addCustomer(String firstname, String surname, Integer birthYear) {
        et.begin();
            Customer c = new Customer();
            c.setFirstname(firstname);
            c.setSurname(surname);
            c.setYearOfBirth(birthYear);
        em.persist(c);
        et.commit();
    }
    
    public void editCustomerById(Integer id, String newFirstname, String newSurname) {
        et.begin();
            Customer c = findCustomerById(id);
            if (c != null) {
                c.setFirstname(newFirstname);
                c.setSurname(newSurname);
                em.merge(c);
            }
        et.commit();
    }
    
    public void removeCustomerById(Integer id) {
        et.begin();
            Customer c = findCustomerById(id);
            if (c != null) {
                em.remove(c);
            }
        et.commit();
    }
    
    public boolean hasBook(Customer c) {
        List<CustomerBook> allLoans = getAllLoans();
        for (CustomerBook cb : allLoans) {
            if (cb.getCustomerId().equals(c.getCustomerId())) { return true; }
        }
        return false;
    }
    
    public void activateCustomerById(Integer id) {
        et.begin();
            Customer c = findCustomerById(id);
            if (c != null) {
                c.setActive(Boolean.TRUE);
                c.setLastActivated(System.currentTimeMillis());
            }
        et.commit();
    }
    
    public void lendBook(Integer customerId, Integer bookId) {
        et.begin();
            CustomerBook cb = new CustomerBook(customerId, bookId);
            Book b = findBookById(bookId);
            b.setQuantity(b.getQuantity()-1);
        em.persist(cb);
        et.commit();
    }
        
    public ObservableList<Book> getAllBooks() {
        TypedQuery<Book> q = em.createQuery(
            "SELECT b FROM Book AS b",
            Book.class
        );
        
        List<Book> books = q.getResultList();

        ObservableList<Book> booksObs = FXCollections.observableArrayList();
        try{
            Iterator it = books.iterator();
            while (it.hasNext()){
                booksObs.add((Book) it.next());
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            return booksObs;
        }
    }
    
    public void removeLoanById(int id) {
        et.begin();
            CustomerBook cb = findLoanById(id);
            if (cb != null) {
                Book b = findBookById(cb.getBookId());
                b.setQuantity(b.getQuantity()+1);
                em.remove(cb);
            }
        et.commit();
    }
    
    public ObservableList<Customer> getAllCustomers() {
        TypedQuery<Customer> q = em.createQuery(
            "SELECT c FROM Customer AS c",
            Customer.class
        );
        
        List<Customer> customers = q.getResultList();

        ObservableList<Customer> cusObs = FXCollections.observableArrayList();
        try{
            Iterator it = customers.iterator();
            while (it.hasNext()){
                cusObs.add((Customer) it.next());
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            return cusObs;
        }
    }
    
    public ObservableList<CustomerBook> getAllLoans() {
        TypedQuery<CustomerBook> q = em.createQuery(
            "SELECT cb FROM CustomerBook AS cb",
            CustomerBook.class
        );
        
        List<CustomerBook> loans = q.getResultList();

        ObservableList<CustomerBook> loansObs = FXCollections.observableArrayList();
        try{
            Iterator it = loans.iterator();
            while (it.hasNext()){
                loansObs.add((CustomerBook) it.next());
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            return loansObs;
        }
    }
        
    public void exit() {
        em.close();
        emf.close();
    }
    
    public boolean checkInput(String str) {
        return (str != null && str.trim().length() >= 3);
    }
    
    public boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public void deactivateCustomers(long now) {
        List<Customer> customers = getAllCustomers();
        
        for (Customer c : customers) {
            if (c.getLastActivated() != null && c.getLastActivated() + Long.parseLong("2592000000") < now) {
                c.setActive(Boolean.FALSE);
            }
        }
    }
    
    public boolean canBorrow(Customer c, Book b) {
        if (b.getAgeRestricted()) {
            int currYear = Year.now().getValue();
            boolean oldEnough = currYear - c.getYearOfBirth() >= 18;
            return oldEnough && b.getQuantity() > 0;
        } else {
            return b.getQuantity() > 0;
        }
        
    }
}
