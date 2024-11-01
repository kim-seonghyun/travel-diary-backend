package com.ssafy.trip.traveldiary.controller;

import com.ssafy.trip.traveldiary.dto.request.TravelDiaryRegistRequest;
import com.ssafy.trip.traveldiary.dto.request.TravelDiaryUpdateRequest;
import com.ssafy.trip.traveldiary.dto.response.TravelDiaryDetailResponse;
import com.ssafy.trip.traveldiary.dto.response.TravelDiaryListResponse;
import com.ssafy.trip.traveldiary.service.TravelDiaryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/travel-diary")
public class TravelDiaryController {
    private final TravelDiaryService service;

    @Autowired
    public TravelDiaryController(TravelDiaryService service) {
        this.service = service;
    }

    @PostMapping("/regist")
    public ResponseEntity<Void> regist(@RequestBody TravelDiaryRegistRequest request){
        service.regist(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<TravelDiaryListResponse>> list(){
        List<TravelDiaryListResponse> response = service.selectAll();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/detail/{id}")
    public ResponseEntity<TravelDiaryDetailResponse> detail(@PathVariable Long id){
        TravelDiaryDetailResponse response = service.selectById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody TravelDiaryUpdateRequest request){
        service.update(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete/{travelDiaryId}")
    public ResponseEntity<Void> delete(@PathVariable long travelDiaryId){
        service.delete(travelDiaryId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
