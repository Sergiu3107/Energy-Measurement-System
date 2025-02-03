package ro.tuc.ds2024.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.tuc.ds2024.dtos.DeviceDTO;
import ro.tuc.ds2024.entities.Device;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {

    @Query(value = "SELECT d FROM Device d WHERE d.user.id = :userId")
    List<Device> getByUser(@Param("userId") UUID userId);

    @Query(value = "SELECT d FROM Device d WHERE d.description = :description")
    Optional<Device> getByDescription(@Param("description") String description);
}
