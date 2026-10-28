package com.sisait.webapp.service;

import com.sisait.webapp.dto.EmailDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApiReactService {

    @Autowired
    private JavaMailSender mailSender;

    // 컨트롤러에서 호출하는 메서드 이름(sendMailMessage)과 통일했습니다.
    public void sendEmailMessage(EmailDTO dto) throws MessagingException {

        // 1. MimeMessage 객체 생성
        MimeMessage message = mailSender.createMimeMessage();

        // 2. MimeMessageHelper를 이용해 이메일 세부 설정 (멀티파트 true, 인코딩 UTF-8)
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        // 3. 보낸사람, 받는사람, 제목 설정
        helper.setFrom("sin3791@naver.com");
        helper.setTo(dto.getTo());
        helper.setSubject(dto.getSubject());

        // 4. HTML 본문 작성
        String text = "<h1>이메일 보내기 연습중</h1>";
        text += "<div style='background-color:pink; width:500px; text-align:center; font-size:2em'>";
        text += dto.getText();
        text += "</div>";

        // 5. 본문 내용 적용 (두 번째 인자에 true를 주어야 HTML 태그가 깨지지 않고 적용됩니다)
        helper.setText(text, true);

        // 6. 메일 전송
        mailSender.send(message);
    }

    // 공영주차장 정보 요청하기
    //환경설정 파일인 application.properties의 인증키를 변수에 대입하기
    @Value("${publicata.parking.key}")
    private String serviceKey;

    //RestClient :외부 서버의 Rest api 호출할떄 사용하는 Http 클라이언트 객체
    private final RestClient restClient = RestClient.create();
    public List<JsonNode> getParkingList(double lat, double lng) {

        String url = "https://api.data.go.kr/openapi/tn_pubr_prkplce_info_api";

        String decodingKey = URLDecoder.decode(
                serviceKey, StandardCharsets.UTF_8
        );

        JsonNode response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("api.data.go.kr")
                        .path("/openapi/tn_pubr_prkplce_info_api")
                        .queryParam("serviceKey", decodingKey)
                        .queryParam("pageNo", 1)
                        .queryParam("numOfRows", 100)
                        .queryParam("type", "json")
                        .build())
                .retrieve()
                .body(JsonNode.class);

        System.out.println(response);

        List<JsonNode> parkingList = new ArrayList<>();

        if (response == null) {
            return parkingList;
        }

        JsonNode parkingItem = response
                .path("body")
                .path("items")
                .path("item");
        System.out.println("API 전체 응답: " + response);
        System.out.println("추출한 데이터 개수: " + parkingItem.size());
        for (JsonNode node : parkingItem) {

            String latitude = node.path("latitude").asString("");
            String longitude = node.path("longitude").asString("");

            if ("공영".equals(node.path("prkplceSe").asString())
                    && !latitude.isBlank()
                    && !longitude.isBlank()) {

                parkingList.add(node);
            }
        }

        return parkingList;
        }
    }