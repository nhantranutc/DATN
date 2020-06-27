package application.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity(name = "dbo_appointment_book")
public class AppointmentBook {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "appointment_book_id")
    @Id
    private int id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "appointment_date")
    private Date appointmentDate;

    @Column(name = "vehicle_brand")
    private String vehicleBrand;

    @Column(name = "content")
    private String content;

    @Column(name = "action_type")
    private String actionType;

    @Column(name = "email")
    private String email;

    @Column(name = "status_id" , insertable = false, updatable = false)
    private int statusId;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private Status status;
}
