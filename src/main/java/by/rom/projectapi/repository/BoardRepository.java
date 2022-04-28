package by.rom.projectapi.repository;

import by.rom.projectapi.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByName(String name);

    @Query("select p from Board p where lower(p.name) like lower(concat('%', :projectName,'%'))")
    List<Board> findByNameContainingIgnoreCase(@Param("projectName") String name);
}
