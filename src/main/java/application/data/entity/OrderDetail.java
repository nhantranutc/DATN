package application.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "dbo_order_detail")
public class OrderDetail {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_detail_id")
    @Id
    private int id;

    @Column(name = "order_id")
    private int orderId;

    @Column(name = "accessary_id")
    private int accessaryId;

    @Column(name = "service_id")
    private int serviceId;

    @Column(name = "amount")
    private int amount;

    @Column(name = "price")
    private double price;
}
