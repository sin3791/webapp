package com.sisait.webapp.controller;


import com.sisait.webapp.domain.DataEntity;
import com.sisait.webapp.domain.FileEntity;
import com.sisait.webapp.service.DataService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class DataController {
    private final DataService service;
    @PostMapping("/dataWrite")
    // insert가 여러번 발생하므로 중간에 에러가 발생하면 이전 Insert한 결과 취소하기 위한 설정
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public String dataWrite(DataEntity entity, HttpSession session){
        String path = session.getServletContext().getRealPath("/uploads");
        System.out.println("path--->" + path);

        List<FileEntity> fileList = null;
        try {

            File pathFile = new File(path);
            if( pathFile.exists()){
                pathFile.mkdirs(); // 폴더 생성됨
            }
            DataEntity resultEntity = service.dataInsert(entity);
            fileList = fileuploadProcess(resultEntity.getId(), entity.getFiles(), path);

            System.out.println("size -->" +fileList.size());

            int resultCount = service.fileListInsert(fileList);
            return "Ok";


        }catch (Exception e){
            e.printStackTrace();
            //이미 업로드된 파일 삭제
            if(fileList != null){
                for(FileEntity fe : fileList){
                    File f = new File(path, fe.getFilename()+"."+fe.getExtname());
                    f.delete();

                }
            }
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "failed";
        }

    }

    public List<FileEntity> fileuploadProcess(int id, List<MultipartFile> fileList, String path){
        List<FileEntity> uploadFileList = new ArrayList<FileEntity>();

        if(fileList != null){
            for(MultipartFile mf : fileList){
//                System.out.println(1);
                if(mf != null){
                    String orgFilename = mf.getOriginalFilename();
                    File file = new File(path, orgFilename);

                    int point = orgFilename.lastIndexOf(".");
                    String filename = orgFilename.substring(0, point);
                    String extname = orgFilename.substring(point+1);
                    if (file.exists()){
                        for (int i = 1; ; i++){
                            String newFilename = filename+"(" + i +")."+ extname;
                            file = new File(path, newFilename);
                            if (!file.exists()){
                                orgFilename =newFilename;
                                break;

                            }
                        }
                    }
                    try{
                        mf.transferTo(file);
                        FileEntity fEntity = new FileEntity();
                        System.out.println(1111);
                        DataEntity data = new DataEntity();
                        data.setId(id);
                        System.out.println(2222);
                        fEntity.setDataEntity(data);


                        int p = orgFilename.lastIndexOf(".");
                        fEntity.setFilename(orgFilename.substring(0,p));
                        fEntity.setExtname(extname);
                        fEntity.setSize((int) file.length());

                        uploadFileList.add(fEntity);
                        System.out.println(3333);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        }
        return uploadFileList;

    }
}


