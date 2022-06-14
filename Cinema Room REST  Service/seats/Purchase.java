package cinema.seats;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Purchase {
    private final UUID token;
    private final Seat ticket;
    public Purchase(Seat ticket) {
        this.ticket = ticket;
        this.token = UUID.randomUUID();
    }
}
