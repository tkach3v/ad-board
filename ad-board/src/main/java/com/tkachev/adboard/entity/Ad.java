package com.tkachev.adboard.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "ad")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AdStatus status;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "sale_date")
    private Date saleDate;

    @Column(name = "promotion")
    private Boolean promotion;

    @Column(name = "promotion_end_date")
    private Date promotionEndDate;

    @ToString.Exclude
    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL)
    private Set<Review> reviews;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ad ad = (Ad) o;
        return Objects.equals(id, ad.id) &&
                Objects.equals(title, ad.title) &&
                Objects.equals(description, ad.description) &&
                Objects.equals(price, ad.price) &&
                status == ad.status &&
                Objects.equals(owner, ad.owner) &&
                Objects.equals(creationDate, ad.creationDate) &&
                Objects.equals(promotion, ad.promotion) &&
                Objects.equals(promotionEndDate, ad.promotionEndDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, price, status, owner, creationDate, promotion, promotionEndDate);
    }
}
