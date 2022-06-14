package cinema.seats;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StateOfCinema {
    @JsonProperty("current_income")
    private int income = 0;
    @JsonProperty("number_of_available_seats")
    private int availableSeats = 81;
    @JsonProperty("number_of_purchased_tickets")
    private int purchasedTicket = 0;

    public boolean buyTicket(int income) {
        if (availableSeats == 0) {
            return false;
        }
        this.income += income;
        availableSeats--;
        purchasedTicket++;
        return true;
    }

    public boolean returnTicket(int income) {
        if (availableSeats == 81) {
            return false;
        }
        this.income -= income;
        availableSeats++;
        purchasedTicket--;
        return true;
    }
}
