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
    private String creator;
    @Column(name = "study")
    private String study;
    @Column(name = "answer")
    private String answer;
    @Column(name = "example1")
    private String example1;
    @Column(name = "example2")
    private String example2;
    @Column(name = "example3")
    private String example3;
    @Column(name = "memory_level")
    private int memoryLevel;
    @Column(name = "ebb_level")
    private int ebbLevel;
    @Column(name = "last_repeat")
    private String lastRepeat;
    @Column(name = "next_ebb_repeat")
    private String nextEbbRepeat;
    @Column(name = "repeat_count")
    private int repeatCount;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getStudy() {
        return study;
    }

    public void setStudy(String study) {
        this.study = study;
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

    public int getMemoryLevel() {
        return memoryLevel;
    }

    public void setMemoryLevel(int memoryLevel) {
        this.memoryLevel = memoryLevel;
    }

    public int getEbbLevel() {
        return ebbLevel;
    }

    public void setEbbLevel(int ebbLevel) {
        this.ebbLevel = ebbLevel;
    }

    public String getLastRepeat() {
        return lastRepeat;
    }

    public void setLastRepeat(String lastRepeat) {
        this.lastRepeat = lastRepeat;
    }

    public String getNextEbbRepeat() {
        return nextEbbRepeat;
    }

    public void setNextEbbRepeat(String nextEbbRepeat) {
        this.nextEbbRepeat = nextEbbRepeat;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }
}
