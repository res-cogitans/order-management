package org.programmers.ordermanagementsystem.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@EqualsAndHashCode
public class Age {

    public Age(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Getter
    private LocalDate birthDate;

    public boolean isMinorAge() {
        return (getKoreanYearAge() < 20);
    }

    private Integer getKoreanAge() {
        return LocalDate.now().getYear() - birthDate.getYear();
    }

    private Integer getKoreanYearAge() {
        return LocalDate.now().getYear() - birthDate.getYear() -1;
    }

    private Integer getInternationalAge() {
        if (isBirthDayPassedInThisYear()) {
            return LocalDate.now().getYear() - birthDate.getYear();
        }
        return LocalDate.now().getYear() - birthDate.getYear() -1;
    }

    private boolean isBirthDayPassedInThisYear() {
        return LocalDate.now().getDayOfYear() <= birthDate.getDayOfYear();
    }

    @Override
    public String toString() {
        return getKoreanYearAge().toString();
    }
}
