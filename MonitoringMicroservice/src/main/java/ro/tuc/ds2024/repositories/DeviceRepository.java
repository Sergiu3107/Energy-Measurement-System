package ro.tuc.ds2024.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.tuc.ds2024.entities.Device;

import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {
}
