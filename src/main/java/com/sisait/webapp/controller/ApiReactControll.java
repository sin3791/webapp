package com.sisait.webapp.controller;

import com.sisait.webapp.dto.EmailDTO;
import com.sisait.webapp.service.ApiReactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*") // 모든 도메인에서의 요청을 허용하려면 이렇게 수정하시는 것이 편합니다.
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class ApiReactControll {

    private final ApiReactService service;

    // 네이버로 이메일 보내기
    @PostMapping("/send_email")
    public ResponseEntity<String> naverEmailSend(@RequestBody EmailDTO dto) {
        try {
            service.sendEmailMessage(dto);
            return ResponseEntity.ok().body("Ok");

        } catch (Exception e) {
            log.error("Email send failed: ", e);
            return ResponseEntity.badRequest().body("Fail");
        }
    }

    //이번시간
    @GetMapping("crawlingMelonTop100")
    public List<Map<String, String>> melonChart() {        //곡전체 정보를 담을 컬랙션 변수 선언
        List<Map<String, String>> melonTopDBList = new ArrayList<Map<String, String>>();
        try{
            // 크롤링 페이지 url
            String url = "https://www.melon.com/chart/index.htm";
            // goekd url에서 Document
            Connection connect = Jsoup.connect(url);
            Document document = connect.get();
//            System.out.println(doc);

            //select() : Document에서 tr태그를 class가 lst 50, lst 100인 element구하기
            // select("tr"): tr태그선택
            //select("tr.lst50") : tr태그에 class가 lst50인 element50 등을 선택
            //select("tr\lst50") : tr태그에 class가 lst50인 element50 등을 선택

            Elements elements = document.select("tr.lst50,tr.lst100"); //<tr></tr><tr></tr>.....

            for(Element element : elements){
                //순위
                String rank = element.select("span.rank").text();
                //포스트 이미지
                String postImage = element.select("a.image_typeAll img").attr("src");
                //노래제목
                String songName = element.select("div.rank01 > span > a").text();
                //가수명
                String artistName = element.select("div.rank02>a").text();
                //앨범명
                String albumName = element.select("div.rank03>a").text();
                //좋아요
                String albumUrl = element.select("div.wrap>a.image_typeAll").attr("href");
                //앨범코드
                String albumNo = albumUrl.substring(albumUrl.indexOf("=") + 1);

                String songUrl = element.select("div.wrap>a.type03").attr("href");
                String songId = songUrl.substring(songUrl.indexOf("=")+1);
//                System.out.println(rank +"/ " + songName+ "/  " +artistName+"/  " + albumName+"/  "+ songUrl+"/  "+ songId);
                Map<String, String> map = new HashMap<String, String>();
                map.put("rank", rank);
                map.put("postImage", postImage);
                map.put("songName", songName);
                map.put("artistName", artistName);
                map.put("albumName", albumName);
                map.put("albumUrl", albumUrl);
                map.put("albumNo", albumNo);
                map.put("songId", songId);

                melonTopDBList.add(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return melonTopDBList;
    }

    @GetMapping("/albumDetail")
    public Map<String, Object> albumDetail(@RequestParam String albumNo) {

        Map<String, Object> albumData = new HashMap<>();

        try {
            String url = "https://www.melon.com/album/detail.htm?albumId=" + albumNo;

            Document document = Jsoup.connect(url).get();

            // 앨범명
            String albumName = document.select("div.song_name").text();

            // 가수명
            String artistName = document.select("div.artist").text();

            // 앨범 이미지
            String albumImage = document.select("div.thumb img").attr("src");

            albumData.put("albumName", albumName);
            albumData.put("artistName", artistName);
            albumData.put("albumImage", albumImage);


            String releaseDate = document.select("div.meta dl.list dd").get(0).text();
            String genre = document.select("div.meta dl.list dd").get(1).text();
            String publisher = document.select("div.meta dl.list dd").get(2).text();
            String agency = document.select("div.meta dl.list dd").get(3).text();


            // 발매정보 가져오기
            Elements dd = document.select("div.meta dl.list dd");


            albumData.put("releaseDate", dd.get(0).text());
            albumData.put("genre", dd.get(1).text());
            albumData.put("publisher", dd.get(2).text());
            albumData.put("agency", dd.get(3).text());

            albumData.put("agency", dd.get(3).text());

// ===== 여기부터 추가 =====

// 수록곡 목록 가져오기

            List<Map<String, String>> songList = new ArrayList<>();

// 수록곡 행 가져오기
            Elements songs = document.select(".service_list_song table tbody tr");

            if (songs.isEmpty()) {
                songs = document.select("table tbody tr");
            }

            System.out.println("찾은 행 개수: " + songs.size());

            int trackNo = 1;

            for (Element song : songs) {

                // 곡명
                Element titleElement = song.selectFirst(
                        "div.rank01 a, div.ellipsis a"
                );

                if (titleElement == null) {
                    continue;
                }

                String songName = titleElement.text();

                // 아티스트
                Element artistElement = song.selectFirst(
                        "div.rank02 a, .checkEllipsis a"
                );

                String songArtist = artistElement != null
                        ? artistElement.text() : artistName;

                Map<String, String> songMap = new HashMap<>();

                songMap.put("trackNo", String.valueOf(trackNo++));
                songMap.put("songName", songName);
                songMap.put("artistName", songArtist);

                songList.add(songMap);
            }

            albumData.put("songList", songList);

            System.out.println("수록곡 목록: " + songList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return albumData;

    }
    @GetMapping("/currentParking")
    public List<JsonNode> currentParking(double lat, double lng){
        return service.getParkingList(lat,lng);


    }
}