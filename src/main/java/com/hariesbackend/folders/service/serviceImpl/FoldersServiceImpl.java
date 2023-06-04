package com.hariesbackend.folders.service.serviceImpl;

import com.hariesbackend.folders.dto.FoldersDTO;
import com.hariesbackend.folders.model.FoldersEntity;
import com.hariesbackend.folders.repository.FoldersRepository;
import com.hariesbackend.folders.service.FoldersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.NullableUtils;
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
    public FoldersDTO getRootFolder () {
        FoldersDTO foldersDTO = new FoldersDTO();
        BeanUtils.copyProperties(foldersRepository.findByDepth(-1).get(0), foldersDTO);
        return foldersDTO;
    }

    // 부모 폴더 및 자식 폴더 조회
    @Override
    public List<FoldersDTO> getChildFolders(String parentId) {

        FoldersDTO folder = this.getFolder(parentId);
        System.out.println("parent값 확인"+ folder);
        List<FoldersDTO> foldersDTO = new ArrayList<>();

        List<FoldersEntity> foldersEntityList = foldersRepository.findByParentIdAndDepth(parentId, folder.getDepth());
        System.out.println("값 확인"+ foldersEntityList);
        foldersEntityList.stream().forEach(el -> {
            FoldersDTO folderDTO = new FoldersDTO();
            BeanUtils.copyProperties(el, folderDTO);
            foldersDTO.add(folderDTO);
        });

        return foldersDTO;
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

        // 부모 폴더가 있는지 확인
        FoldersEntity parentFolder = foldersRepository.findById(foldersDTO.getParentId()).get();

        if (parentFolder != null) {
            // 자식 추가
            foldersRepository.insert(foldersEntity);

            // 부모 노드에 자식 노드 id 추가
            FoldersEntity childFolder = foldersRepository.findByUniqueKey(foldersDTO.getUniqueKey());
            List<String> childIdList = parentFolder.getChildrenId();
            childIdList.add(childFolder.getId());
            parentFolder.setChildrenId(childIdList);

            foldersRepository.save(parentFolder);
        }
    }

    @Override
    public FoldersDTO getFolder(String id) {
        FoldersDTO folder = new FoldersDTO();
        FoldersEntity folderE = new FoldersEntity();
        BeanUtils.copyProperties(foldersRepository.findById(id).get(), folderE);
        System.out.println("폴더 값 확인"+ foldersRepository.findById(id).get());
        System.out.println("폴더 값 확인2"+ folderE);
        return folder;
    }

    @Override
    public FoldersEntity getFolderByUniqueKey(String uniqueKey) {
        return foldersRepository.findByUniqueKey(uniqueKey);
    }


    @Override
    public void modifyFolders(String id, FoldersDTO foldersDTO) {
         FoldersEntity foldersEntity = foldersRepository.findById(id).get();

//        if(foldersDTO.getUniqueKey() !== ) {
//            foldersEntity.setUniqueKey(foldersDTO.getUniqueKey());
//        }
    }
}
