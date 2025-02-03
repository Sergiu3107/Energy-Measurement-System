package ro.tuc.ds2024.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "Device")
@Table(name = "device")
@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "device_id", nullable = false)
    @Type(type = "uuid-binary")
    @NonNull
    private UUID id;

    @Column(name = "user_id", nullable = false)
    @NonNull
    private UUID userId;

    @Column(name = "maxim_cost", nullable = false)
    @NonNull
    private Double maximCost;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Consumption> consumptionList = new ArrayList<>();

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", measurementCount=" + (consumptionList != null ? consumptionList.size() : 0) +
                '}';
    }
}
