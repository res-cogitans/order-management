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

    public int getKoreanAge() {
        return LocalDate.now().getYear() - birthDate.getYear();
    }

    public int getKoreanYearAge() {
        return LocalDate.now().getYear() - birthDate.getYear() -1;
    }

    public int getInternationalAge() {
        if (isBirthDayPassedInThisYear()) {
            return LocalDate.now().getYear() - birthDate.getYear();
        }
        return LocalDate.now().getYear() - birthDate.getYear() -1;
    }

    private boolean isBirthDayPassedInThisYear() {
        return LocalDate.now().getDayOfYear() <= birthDate.getDayOfYear();
    }
}
