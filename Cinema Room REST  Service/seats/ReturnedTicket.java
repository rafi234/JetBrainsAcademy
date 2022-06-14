package cinema.seats;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReturnedTicket {
    @JsonProperty("returned_ticket")
    private Seat seat;
}
