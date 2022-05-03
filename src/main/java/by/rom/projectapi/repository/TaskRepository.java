package by.rom.projectapi.repository;

import by.rom.projectapi.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("select t from Task t where lower(t.name) like lower(concat('%', :taskName,'%'))")
    List<Task> findByNameContainingIgnoreCase(@Param("taskName") String name);

    Optional<Task> findByName(String name);
}
