package in.zeta.academy.capstone.plas.dto;

import lombok.Data;

@Data
public class EmiRequestDto {
    private double amount;
    private double annualRate;
    private int tenureMonths;
}