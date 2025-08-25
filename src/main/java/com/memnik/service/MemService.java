package com.memnik.service;

import com.memnik.dao.MemEntity;
import com.memnik.dao.TagEntity;
import com.memnik.service.base.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

import static com.memnik.common.CommonUtils.getFileFromAddress;

@Slf4j
@Service
public class MemService extends BaseService<MemEntity> {
    public boolean deleteMem(Long memId) {
        Optional<MemEntity> optionalMem = repository.findById(memId);
        if(optionalMem.isEmpty()) {
            log.error("Can't find mem with id %d".formatted(memId));
            return false;
        }
        MemEntity mem = optionalMem.get();

        // Разрываем связи с TagEntity
        for (TagEntity tag : new ArrayList<>(mem.getTags())) {
            tag.removeMem(mem);
        }

        // Удаляем MemEntity
        File file = getFileFromAddress(mem.getInformation());
        if(file != null) {
            file.delete();
        }
        repository.delete(mem);
        log.info("Mem %d has been deleted".formatted(memId));

        return true;
    }
}
