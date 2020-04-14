package application.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity(name = "dbo_news")
public class News {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "news_id")
    @Id
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "main_image")
    private String mainImage;

    @Column(name = "content")
    private String content;

    @Column(name = "short_desc")
    private String shortDesc;

    @Column(name = "author")
    private String author;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "is_hot")
    private int isHot;
}
