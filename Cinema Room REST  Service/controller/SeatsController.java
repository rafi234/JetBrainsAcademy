package cinema.controller;

import cinema.exceptions.ResponseHandler;
import cinema.seats.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class SeatsController {
    private final Cinema cinema = new Cinema();
    private final Map<UUID, Seat> purchaseHistory = new ConcurrentHashMap<>();

    @GetMapping("/seats")
    public Cinema getCinema() {
        return cinema;
    }

    @PostMapping("/purchase")
    public Object purchase(@RequestBody Seat s) {
        if (!Seat.isOutOfBounds(s.getRow(), s.getColumn())) {
            return ResponseHandler.generateResponse(
                    "The number of a row or a column is out of bounds!",
                    HttpStatus.BAD_REQUEST
            );
        }

        Seat seat = cinema.getAvailable_seats()[9 * (s.getRow() - 1) + s.getColumn() - 1];
        Purchase purchase = new Purchase(seat);

        if (seat.isTaken()) {
           return ResponseHandler.generateResponse(
                   "The ticket has been already purchased!",
                   HttpStatus.BAD_REQUEST
           );
        }
        purchaseHistory.put(purchase.getToken(), seat);
        seat.setTaken(true);
        cinema.getStateOfCinema().buyTicket(seat.getPrice());
        return purchase;
    }

    @PostMapping("/return")
    public Object getTicket(@RequestBody Token token) {
        if (purchaseHistory.containsKey(token.getToken())) {
            Seat seat = purchaseHistory.get(token.getToken());
            purchaseHistory.remove(token.getToken());
            cinema.getStateOfCinema().returnTicket(seat.getPrice());
            return new ReturnedTicket(seat);
        } else {
            return ResponseHandler.generateResponse(
                    "Wrong token!",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping("/stats")
    public Object getStats(@RequestParam(required = false) String password) {
        if (password == null || !password.equals("super_secret")) {
            return ResponseHandler.generateResponse(
                    "The password is wrong!",
                    HttpStatus.UNAUTHORIZED
            );
        }
        return cinema.getStateOfCinema();
    }
}
