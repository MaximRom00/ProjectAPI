package by.rom.projectapi.repository;

import by.rom.projectapi.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
