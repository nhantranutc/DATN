package application.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity(name = "dbo_order")
public class Order {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    @Id
    private int id;

    @Column(name = "username")
    private String userName;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "price")
    private double price;

    @Column (name = "created_date")
    private Date createdDate;
}
