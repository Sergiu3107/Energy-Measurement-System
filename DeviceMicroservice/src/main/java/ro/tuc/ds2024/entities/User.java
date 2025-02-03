package ro.tuc.ds2024.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import ro.tuc.ds2024.entities.Device;

@Entity(name = "User")
@Table(name = "d_user")
@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id", nullable = false)
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    @NonNull
    private UUID id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @NonNull
    private List<Device> deviceList;

}
