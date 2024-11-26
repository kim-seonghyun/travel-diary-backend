package com.ssafy.trip.traveldiary.controller;

import com.ssafy.trip.traveldiary.dto.request.TravelDiaryRegisterRequest;
import com.ssafy.trip.traveldiary.dto.request.TravelDiaryUpdateRequest;
import com.ssafy.trip.traveldiary.dto.response.TravelDiaryDetailResponse;
import com.ssafy.trip.traveldiary.dto.response.TravelDiaryListResponse;
import com.ssafy.trip.traveldiary.service.TravelDiaryService;
import com.ssafy.trip.utils.ImageUtils;
import com.ssafy.trip.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/travel-diary")
public class TravelDiaryController {
    private final TravelDiaryService service;
    private final JwtUtil jwtUtil;

    @Autowired
    public TravelDiaryController(TravelDiaryService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "여행 일지 등록", description = "새로운 여행 일지를 등록합니다.")
    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestPart("payload") TravelDiaryRegisterRequest request, @RequestPart("imageName") MultipartFile image,
            @RequestPart("selectedPosts") List<Long> selectedPosts) throws Exception {
        String imageName = ImageUtils.upload(image);
        request.setImageName(imageName);
        request.setSelectedPosts(selectedPosts);
        service.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Operation(summary = "여행 일지 목록 조회", description = "모든 여행 일지 목록을 조회합니다.")
    @GetMapping("/list")
    public ResponseEntity<List<TravelDiaryListResponse>> list(@RequestHeader("Authorization") String accessToken) {
        accessToken = accessToken.substring(7);
        Claims claim = jwtUtil.parseToken(accessToken);
        Long userId = claim.get("userId", Long.class);
        List<TravelDiaryListResponse> response = service.selectAll(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "여행 일지 상세 조회", description = "특정 여행 일지의 상세 정보를 조회합니다.")
    @GetMapping("/detail/{id}")
    public ResponseEntity<TravelDiaryDetailResponse> detail(
            @RequestHeader("Authorization") String accessToken, @PathVariable Long id) {
        accessToken = accessToken.substring(7);
        Claims claim = jwtUtil.parseToken(accessToken);
        Long userId = claim.get("userId", Long.class);
        TravelDiaryDetailResponse response = service.selectById(id, userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "여행 일지 수정", description = "기존의 여행 일지를 수정합니다.")
    @PutMapping("/update")
    public ResponseEntity<Void> update(
            @Parameter(description = "여행 일지 수정 요청 객체", required = true) @RequestBody TravelDiaryUpdateRequest request) {
        service.update(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "여행 일지 삭제", description = "특정 여행 일지를 삭제합니다.")
    @DeleteMapping("/delete/{travelDiaryId}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "삭제할 여행 일지의 ID", required = true) @PathVariable long travelDiaryId) {
        service.delete(travelDiaryId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
