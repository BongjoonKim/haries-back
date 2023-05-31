package com.hariesbackend.folders.service.serviceImpl;

import com.hariesbackend.folders.dto.FoldersDTO;
import com.hariesbackend.folders.model.FoldersEntity;
import com.hariesbackend.folders.repository.FoldersRepository;
import com.hariesbackend.folders.service.FoldersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class FoldersServiceImpl implements FoldersService {
    @Autowired
    FoldersRepository foldersRepository;

    // Root 폴더 조회
    @Override
    public FoldersEntity getRootFolder () {
        return foldersRepository.findByDepth(-1).get(0);
    }

    // 부모 폴더 및 자식 폴더 조회
    @Override
    public List<FoldersEntity> getExpandedFolders(String parentId, int depth) {
        return foldersRepository.findByParentIdAndDepth(parentId, depth);
    }


    // 폴더 생성
    @Override
    public void createFolders(FoldersDTO foldersDTO) {
        FoldersEntity foldersEntity = new FoldersEntity();
        LocalDateTime now = LocalDateTime.now();

        foldersEntity.setUniqueKey(foldersDTO.getUniqueKey());
        foldersEntity.setLabel(foldersDTO.getLabel());
        foldersEntity.setDepth(foldersDTO.getDepth());
        foldersEntity.setPath(foldersDTO.getPath());
        foldersEntity.setParentId(foldersDTO.getParentId());
        foldersEntity.setChildrenId(foldersDTO.getChildrenId());
        foldersEntity.setType(foldersDTO.getType());
        foldersEntity.setShow(foldersDTO.isShow());
        foldersEntity.setExpand(foldersDTO.isExpand());
        foldersEntity.setCreated(now);
        foldersEntity.setModified(null);
        foldersEntity.setCreator("haries");
        foldersEntity.setModifier("haries");


//        foldersRepository.save(foldersEntity);
        foldersRepository.insert(foldersEntity);
    }

    @Override
    public FoldersEntity getFolder(String id) {
        return foldersRepository.findById(id).get();
    }

    @Override
    public void modifyFolders(String id, FoldersDTO foldersDTO) {
         FoldersEntity foldersEntity = foldersRepository.findById(id).get();

//        if(foldersDTO.getUniqueKey() !== ) {
//            foldersEntity.setUniqueKey(foldersDTO.getUniqueKey());
//        }
    }
}
