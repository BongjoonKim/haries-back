package com.hariesbackend.folders.service.serviceImpl;

import com.hariesbackend.folders.dto.FoldersDTO;
import com.hariesbackend.folders.model.FoldersEntity;
import com.hariesbackend.folders.repository.FoldersRepository;
import com.hariesbackend.folders.service.FoldersService;
import com.hariesbackend.login.model.Users;
import com.hariesbackend.login.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.NullableUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class FoldersServiceImpl implements FoldersService {
    @Autowired
    FoldersRepository foldersRepository;

    @Autowired
    UsersRepository usersRepository;

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
        List<FoldersDTO> foldersDTO = new ArrayList<>();
        List<FoldersEntity> foldersEntityList = foldersRepository.findByParentIdAndDepth(parentId, folder.getDepth() + 1);
        if (foldersEntityList.size() > 0) {
            foldersEntityList.stream().forEach(el -> {
                FoldersDTO folderDTO = new FoldersDTO();
                BeanUtils.copyProperties(el, folderDTO);
                folderDTO.setChildren(getChildFolders(el.getId())); // 재귀 함수로 만들기
                foldersDTO.add(folderDTO);
            });
        }
        return foldersDTO;
    }


    // 폴더 생성
    @Override
    public void createFolders(FoldersDTO foldersDTO) throws Exception {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() != null) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                System.out.println("userDetails = " + userDetails);
                String username = userDetails.getUsername();

                Users users = usersRepository.findByUserId(username);

                if (!ObjectUtils.isEmpty(users)) {
                    FoldersEntity foldersEntity = new FoldersEntity();
                    LocalDateTime now = LocalDateTime.now();

                    foldersEntity.setUniqueKey(foldersDTO.getUniqueKey());
                    foldersEntity.setLabel(foldersDTO.getLabel());
                    //        foldersEntity.setDepth(foldersDTO.getDepth());
                    //        foldersEntity.setPath(foldersDTO.getPath());
                    //        foldersEntity.setParentId(foldersDTO.getParentId());
                    foldersEntity.setChildrenId(foldersDTO.getChildrenId());
                    foldersEntity.setType(foldersDTO.getType());
                    foldersEntity.setShow(foldersDTO.isShow());
                    foldersEntity.setExpand(foldersDTO.isExpand());
                    foldersEntity.setCreated(now);
                    foldersEntity.setModified(now);
                    foldersEntity.setCreator(username);
                    foldersEntity.setModifier(username);

                    // 부모 폴더가 있는지 확인
                    FoldersEntity parentFolder = foldersRepository.findById(foldersDTO.getParentId()).get();

                    if (parentFolder != null) {
                        foldersEntity.setDepth(parentFolder.getDepth() + 1);
                        foldersEntity.setPath(parentFolder.getPath() + "/" + foldersEntity.getLabel());
                        foldersEntity.setParentId(foldersDTO.getParentId());
                        // 자식 추가
                        foldersRepository.save(foldersEntity);

                        // 부모 노드에 자식 노드 id 추가
                        FoldersEntity childFolder = foldersRepository.findByUniqueKey(foldersDTO.getUniqueKey());
                        List<String> childIdList = parentFolder.getChildrenId();
                        if (!ObjectUtils.isEmpty(childIdList)) {
                            childIdList.add(childFolder.getId());
                            parentFolder.setChildrenId(childIdList);
                        } else {
                            List<String> newChildId = Collections.singletonList(childFolder.getId());
                            parentFolder.setChildrenId(newChildId);
                        }
                        foldersRepository.save(parentFolder);

                    }
                } else {
                    throw new Exception("로그인이 필요합니다");
                }
            }
        } catch (Exception e) {
             throw e;
        }
    }

    @Override
    public FoldersDTO getFolder(String id) {
        FoldersDTO folder = new FoldersDTO();
        FoldersEntity folderE = new FoldersEntity();
        BeanUtils.copyProperties(foldersRepository.findById(id).get(), folder);
        return folder;
    }

    @Override
    public FoldersEntity getFolderByUniqueKey(String uniqueKey) {
        return foldersRepository.findByUniqueKey(uniqueKey);
    }


    @Override
    public FoldersDTO modifyFolders(FoldersDTO foldersDTO) {
         FoldersEntity foldersEntity = foldersRepository.findById(foldersDTO.getId()).get();
         if (!Objects.isNull(foldersEntity)) {
             LocalDateTime now = LocalDateTime.now();
             foldersEntity.setLabel(foldersDTO.getLabel());
             foldersEntity.setModified(now);
             FoldersEntity newFolders = foldersRepository.save(foldersEntity);
             BeanUtils.copyProperties(newFolders, foldersDTO);
             return foldersDTO;
         } else {
             return null;
         }
    }

    @Override
    public void deleteFolder(String id) {
        foldersRepository.deleteById(id);
    }
}
