package ro.tuc.ds2024.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.tuc.ds2024.entities.Consumption;
import ro.tuc.ds2024.entities.Device;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConsumptionRepository extends JpaRepository<Consumption, UUID> {

    @Query("SELECT c FROM Consumption c WHERE c.device.id = :deviceId AND c.day = :day")
    List<Consumption> findByDeviceIdAndDate(@Param("deviceId") UUID deviceId, @Param("day") String day);

}
