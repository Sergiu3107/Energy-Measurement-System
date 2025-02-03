package ro.tuc.ds2024.entities;

import lombok.NonNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "Consumption")
@Table(name = "consumption")
@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Consumption {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "day", nullable = false)
    @NonNull
    private String day;

    @Column(name = "hour", nullable = false)
    @NonNull
    private int hour;

    @Column(name = "hourly_consumption", nullable = false)
    @NonNull
    private Double hourlyConsumption;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id", nullable = false)
    @NonNull
    private Device device;

    public void setDevice(Device device) {
        this.device = device;
        if (!device.getConsumptionList().contains(this)) {
            device.getConsumptionList().add(this);
        }
    }

    @Override
    public String toString() {
        return "Consumption{" +
                "id=" + id +
                ", day=" + day +
                ", hour=" + hour +
                ", hourlyConsumption=" + hourlyConsumption +
                '}';
    }
}
