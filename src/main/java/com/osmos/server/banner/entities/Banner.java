package com.osmos.server.banner.entities;

import com.osmos.server.schema.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "banner")
public class Banner extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "img_name")
    private String imgName;

    @Column(name = "details_link")
    private String detailsLink;

}
