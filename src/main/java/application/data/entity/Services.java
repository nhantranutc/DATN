package application.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "dbo_service")
public class Services {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "service_id")
    @Id
    private int id;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "description")
    private String description;

    @Column(name = "content")
    private String content;

    @Column(name = "service_type_id")
    private int serviceTypeId;

    @Column(name = "image")
    private String image;

    @Column(name = "is_delete")
    private int isDelete;
}
