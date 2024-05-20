package org.example.model;

import javax.persistence.*;

@Entity
@Table(name="CUSTOMER")
public class Customer {


    /// Birebir İlişkinin kurulduğu alan
    @OneToOne(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //cascade hangi taraftaysa o etkilenirse onetoone ilişkisi olan etkilenir
    // diğerinde cascade yoksa onda bir değişiklik olduğunda diğeri etkilenmez
    //mappleyip karşıdaki sınıfta ne olursa hepsinden etkileniyor(All diyor çünkü)
    private CustomerDetail customerDetail;
    /// ---------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID", nullable = false)
    private int customerId;

    @Column(name = "FIRST_NAME", length = 65, nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", length = 75)
    private String lastName;

    public Customer() {
    }

    public Customer(int customerId, String firstName, String lastName) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public CustomerDetail getCustomerDetail() {
        return customerDetail;
    }

    public void setCustomerDetail(CustomerDetail customerDetail) {
        this.customerDetail = customerDetail;
    }

}
