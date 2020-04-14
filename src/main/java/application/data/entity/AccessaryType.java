package application.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "dbo_accessary_type")
public class AccessaryType {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "accessary_type_id")
    @Id
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "alias")
    private String alias;

    @Column(name = "description")
    private String description;

    @Column(name = "display_order")
    private int displayOrder;

    @Column(name = "image")
    private String image;

    @Column(name = "is_active")
    private int isActive;

    @Column(name = "is_delete")
    private int isDelete;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accessaryType")
    private List<Accessary> accessaryList = new ArrayList<>();
}
