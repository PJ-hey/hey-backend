package hey.io.heybackend.domain.file.service;

import hey.io.heybackend.domain.file.dto.FileDTO;
import hey.io.heybackend.domain.file.entity.File;
import hey.io.heybackend.domain.file.enums.EntityType;
import hey.io.heybackend.domain.file.enums.FileCategory;
import hey.io.heybackend.domain.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public Map<Long, List<FileDTO>> getFileDtosByEntityType(List<Long> entityIds, EntityType entityType,  FileCategory fileCategory) {
        if (entityIds.isEmpty()) return Collections.emptyMap();

        return fileRepository.findFilesByEntityAndIds(entityIds, entityType, fileCategory)
                .stream()
                .collect(Collectors.groupingBy(File::getEntityId, Collectors.mapping(FileDTO::of, Collectors.toList())));
    }

    public List<FileDTO> getFileDtosByEntity(Long entityId, EntityType entityType, FileCategory fileCategory) {
        return fileRepository.findFilesByEntityAndId(entityId, entityType, fileCategory)
                .stream()
                .map(FileDTO::of)
                .collect(Collectors.toList());
    }
}