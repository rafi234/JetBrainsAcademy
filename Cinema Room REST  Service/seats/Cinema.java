package cinema.seats;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

@Getter
public class Cinema {
    private final int total_rows = 9;
    private final int total_columns = 9;
    private Seat[] available_seats;
    @JsonIgnore
    private final int super_secret = 123;
    @JsonIgnore
    private StateOfCinema stateOfCinema;

    public Cinema() {
        this.available_seats = new Seat[total_columns * total_rows];
        for (int i = 0, k = 0; i < total_rows; ++i) {
            for (int j = 0; j < total_columns; ++j, ++k) {
                available_seats[k] = new Seat(
                        i + 1,
                        j + 1,
                        i + 1 <= 4 ? 10 : 8);
            }
        }
        stateOfCinema = new StateOfCinema();
    }
}