package cinema.seats;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class Seat {
    private int row;
    private int column;
    private int price;
    @Setter
    @JsonIgnore
    private boolean taken = false;

    public Seat(int row, int column, int price) {
        this.row = row;
        this.column = column;
        this.price = price;
    }

    public static boolean isOutOfBounds(int row, int column) {
        return row <= 9 && column <= 9 && column >= 1 && row >= 1;
    }
}
