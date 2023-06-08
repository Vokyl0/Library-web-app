package com.myname.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "books", schema = "public")
public class Book {
    @Id
    @Column(name = "book_id")
    private int id;
    private String title;
    private String author;
    private double price;
    private String genre;
    private String content;
    @Column(name = "release_year")
    private int releaseYear;
}
