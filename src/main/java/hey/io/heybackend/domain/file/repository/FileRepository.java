package hey.io.heybackend.domain.file.repository;

import hey.io.heybackend.domain.file.entity.File;
import hey.io.heybackend.domain.file.enums.EntityType;
import hey.io.heybackend.domain.file.enums.FileCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findByEntityTypeAndEntityId(EntityType entityType, Long entityId);

    List<File> findByEntityTypeAndEntityIdInAndFileCategory(EntityType entityType, List<Long> entityIds, FileCategory fileCategory);

}
