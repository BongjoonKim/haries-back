package com.hariesbackend.folders.service.serviceImpl;

import com.hariesbackend.folders.dto.FoldersDTO;
import com.hariesbackend.folders.model.FoldersEntity;
import com.hariesbackend.folders.repository.FoldersRepository;
import com.hariesbackend.folders.service.FoldersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class FoldersServiceImpl implements FoldersService {
    @Autowired
    FoldersRepository foldersRepository;

    private
    @Override
    public List<FoldersDTO> getAllExpandedFolders() {
        List<FoldersEntity> foldersList = foldersRepository.findByDepth(0);



    }

    @Override
    public void createFolders(FoldersDTO foldersDTO) {
        LocalDateTime now = LocalDateTime.now();
        FoldersEntity foldersEntity = new FoldersEntity();

        foldersEntity.setUniqueKey(foldersDTO.getUniqueKey());
        foldersEntity.setLabel(foldersDTO.getLabel());
        foldersEntity.setDepth(foldersDTO.getDepth());
        foldersEntity.setParentId(foldersDTO.getParentId());
        foldersEntity.setType(foldersDTO.getType());
        foldersEntity.setShow(foldersDTO.isShow());
        foldersEntity.setExpand(foldersDTO.isExpand());
        foldersEntity.setCreated(now);
        foldersEntity.setCreater("haries");
        foldersRepository.save(foldersEntity);
    }

    @Override
    public void modifyFolders(String id, FoldersDTO foldersDTO) {
         FoldersEntity foldersEntity = foldersRepository.findById(id).get();

//        if(foldersDTO.getUniqueKey() !== ) {
//            foldersEntity.setUniqueKey(foldersDTO.getUniqueKey());
//        }
    }
}
