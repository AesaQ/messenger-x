package ru.AesaQ.messenger_x.learning_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "creator")
    private String creater;
    @Column(name = "under-study")
    private String underStudy;
    @Column(name = "answer")
    private String answer;
    @Column(name = "example1")
    private String example1;
    @Column(name = "example2")
    private String example2;
    @Column(name = "example3")
    private String example3;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getUnderStudy() {
        return underStudy;
    }

    public void setUnderStudy(String underStudy) {
        this.underStudy = underStudy;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getExample1() {
        return example1;
    }

    public void setExample1(String example1) {
        this.example1 = example1;
    }

    public String getExample2() {
        return example2;
    }

    public void setExample2(String example2) {
        this.example2 = example2;
    }

    public String getExample3() {
        return example3;
    }

    public void setExample3(String example3) {
        this.example3 = example3;
    }
}
