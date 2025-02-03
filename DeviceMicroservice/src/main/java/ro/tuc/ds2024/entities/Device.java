package ro.tuc.ds2024.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity(name = "Device")
@Table
@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "description", nullable = false)
    @NonNull
    private String description;

    @Column(name = "address", nullable = false)
    @NonNull
    private String address;

    @Column(name = "cost_per_hout", nullable = false)
    @NonNull
    private Double costPerHour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
