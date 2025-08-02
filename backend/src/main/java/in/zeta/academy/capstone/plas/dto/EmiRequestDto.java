package in.zeta.academy.capstone.plas.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

@Data
public class EmiRequestDto {
    @DecimalMin(value = "0.0", inclusive = false, message = "Principal amount must be greater than 0")
    private double amount;

    @DecimalMin(value = "0.0", inclusive = false, message = "Annual rate must be greater than 0")
    private double annualRate;

    @DecimalMin(value = "1", inclusive = true, message = "Tenure months must be at least 1")
    private int tenureMonths;
}