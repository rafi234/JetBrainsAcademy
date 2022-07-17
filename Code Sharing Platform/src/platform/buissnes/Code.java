package platform.buissnes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import platform.myUtils.MyUtils;

import javax.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "codes")
@Getter
@Setter
@NoArgsConstructor
public class Code {

    @Id
    @Column(name = "code_id")
    @JsonIgnore
    private UUID id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    @Column(name = "time")
    @JsonIgnore
    private long time;

    @JsonProperty("time")
    private long timeLeft;

    @Column(name = "views")
    private int views;

    @JsonIgnore
    private boolean viewsDependent;

    @JsonIgnore
    private boolean timeDependent;

    public Code(UUID id, String code, long time, int views, boolean viewsDependent, boolean timeDependent) {
        this.id = id;
        this.code = code;
        this.date = LocalDateTime.now();
        this.time = time;
        this.views = views;
        this.viewsDependent = viewsDependent;
        this.timeDependent = timeDependent;
        setTimeLeft();
    }

    public void setTimeLeft() {
        this.timeLeft = timeDependent ? this.time - countSecondsLeft() : 0;
    }

    @JsonIgnore
    public String getFormattedDate() {
        return MyUtils.getFormattedDate(MyUtils.DATE_FORMATTER, date);
    }

    @JsonIgnore
    public boolean isCodeTimeExpired() {
        if (!timeDependent)
            return false;
        setTimeLeft();
        return this.timeLeft <= 0;
    }

    public boolean hasAnyRestriction() {
        return timeDependent || viewsDependent;
    }

    private long countSecondsLeft() {
        long duration = Duration.between(date, LocalDateTime.now()).toSeconds();
        return Math.abs(duration);
    }
}
