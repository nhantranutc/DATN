package application.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "dbo_accessary")
public class Accessary {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "accessary_id")
    @Id
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "alias")
    private String alias;

    @Column(name = "accessary_type_id" , insertable = false, updatable = false)
    private int accessaryTypeId;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "accessary_type_id")
    private AccessaryType accessaryType;

    @Column(name = "price")
    private Double price;

    @Column(name = "original_price")
    private Double originalPrice;

    @Column(name = "promotion_price")
    private Double promotionPrice;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "warranty")
    private int warranty;

    @Column(name = "image")
    private String image;

    @Column(name = "origin")
    private String origin;

    @Column(name = "is_vat")
    private int isVAT;

    @Column(name = "is_delete")
    private int isDelete;

    @Column(name = "description")
    private String description;
}
