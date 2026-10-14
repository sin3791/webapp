package com.sisait.webapp.controller;


import com.sisait.webapp.domain.DataEntity;
import com.sisait.webapp.domain.FileEntity;
import com.sisait.webapp.service.DataService;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    @GetMapping("/dataList")
    public List<DataEntity> dataList(){
        return service.dataListSelect();
    }
    @GetMapping("/dataDetail/{id}")
    public DataEntity dataDetail(@PathVariable("id") int id){
        System.out.println(id);
        return  service.dataDetailSelect(id);
    }
    @GetMapping("/file/download/{downFile}")
    public ResponseEntity<Resource> downloadFile(@PathVariable ("downFile") String downFile, HttpSession session) throws Exception{
        String folder = session.getServletContext().getRealPath("/uploads");

        Path path = Paths.get(folder, downFile);
        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+downFile+"\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
    @PostMapping("/dataEdit")
    @Transactional(rollbackFor = {RuntimeException.class, SQLException.class})
    public String dataEdit(DataEntity entity, HttpSession session){
        System.out.println("1-->"+entity.toString());

        DataEntity orgDataEntity = service.dataDetailSelect(entity.getId());
        System.out.println("2-->"+orgDataEntity.toString());

        String uploadPath = session.getServletContext().getRealPath("/uploads");
        List<FileEntity> newUploadFile = null;
        int uploadFileCnt =0;
        try{
            newUploadFile = fileuploadProcess(entity.getId(), entity.getFiles(), uploadPath);

            if(newUploadFile !=null && newUploadFile.size()>0){
                uploadFileCnt = service.fileListInsert(newUploadFile);
            }
            List<FileEntity> delFileList = service.fileDeleteList(entity.getDelFile());

            int delFileCount = service.fileDelete(entity.getDelFile());

            //원글 수정한 내용을 update하기
            int updateCount = service.dataUpdate(entity);

            if(delFileList != null){
                for (FileEntity f : delFileList){
                    File file = new File(uploadPath, f.getFilename()+ "."+f.getExtname());
                    file.delete();
                }
            }
            fileDelete(uploadPath, delFileList);
            return "Ok";
        }catch (Exception e){

//            if(newUploadFile != null && newUploadFile.size()>0){
//                for(FileEntity f :newUploadFile){
//                    File file = new File(uploadPath, f.getFilename()+"."+f.getExtname());
//                    file.delete();
//                }
//            }
                e.printStackTrace();
                fileDelete(uploadPath, newUploadFile);

                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "Fail ";

        }
    }

    public void fileDelete(String path, List<FileEntity> List){
        if(List != null && List.size()>0){
            for(FileEntity f :List){
                File file = new File(path, f.getFilename()+"."+f.getExtname());
                file.delete();
            }
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


