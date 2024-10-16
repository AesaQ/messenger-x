package ru.aesaq.messengerx.recommendation_service.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

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
    @Column(name = "remembering_time")
    private int rememberingTime;

    public int getRememberingTime() {
        return rememberingTime;
    }

    public void setRememberingTime(int rememberingTime) {
        this.rememberingTime = rememberingTime;
    }

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

    public void setEbbLevel(int level) {

        this.ebbLevel = level;

        LocalDateTime nextRepeatDate;
        switch (level) {
            case 0:
                setNextEbbRepeat("1970-01-01T00:00:00");
            case 1:
                nextRepeatDate = LocalDateTime.now().plusMinutes(30);
                setNextEbbRepeat(nextRepeatDate.toString());
            case 2:
                nextRepeatDate = LocalDateTime.now().plusDays(1);
                setNextEbbRepeat(nextRepeatDate.toString());
            case 3:
                nextRepeatDate = LocalDateTime.now().plusDays(3);
                setNextEbbRepeat(nextRepeatDate.toString());
            case 4:
                nextRepeatDate = LocalDateTime.now().plusDays(7);
                setNextEbbRepeat(nextRepeatDate.toString());
            case 5:
                nextRepeatDate = LocalDateTime.now().plusDays(14);
                setNextEbbRepeat(nextRepeatDate.toString());
            case 6:
                nextRepeatDate = LocalDateTime.now().plusDays(30);
                setNextEbbRepeat(nextRepeatDate.toString());
        }
    }
}
