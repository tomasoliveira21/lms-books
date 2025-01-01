package pt.psoft.g1.psoftg1.genremanagement.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "A Genre and its lending duration averages per month.")
@AllArgsConstructor
public class GenreLendingsAvgPerMonthView {
    private Integer year;
    private Integer month;
    private List<GenreLendingsView> durationAverages;

}
