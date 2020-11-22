package com.browarna.railwaycarsserving.controller;

import com.browarna.railwaycarsserving.dto.MemoOfDeliveryDto;
import com.browarna.railwaycarsserving.service.MemoOfDeliveryService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/memo/delivery")
@AllArgsConstructor
public class MemoOfDeliveryController {

    private final MemoOfDeliveryService memoOfDeliveryService;

    @GetMapping("/{afterDate}/{beforeDate}")
    public ResponseEntity<List<MemoOfDeliveryDto>> getAllMemoOfDeliveries(
            @PathVariable Date afterDate, @PathVariable Date beforeDate) {
        return ResponseEntity.status(OK)
                .body(memoOfDeliveryService.getAll(afterDate, beforeDate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemoOfDeliveryDto> getMemoOfDeliveryById(@PathVariable Long id) {
        return ResponseEntity.status(OK)
                .body(memoOfDeliveryService.getById(id));
    }

    @PostMapping
    public ResponseEntity<MemoOfDeliveryDto> createMemoOfDelivery(@RequestBody MemoOfDeliveryDto memoOfDeliveryDto) {
    return ResponseEntity.status(OK)
                .body(memoOfDeliveryService.create(memoOfDeliveryDto));
    }

    @PutMapping
    public ResponseEntity<MemoOfDeliveryDto> updateMemoOfDelivery(@RequestBody MemoOfDeliveryDto memoOfDeliveryDto) {
        return ResponseEntity.status(OK)
                .body(memoOfDeliveryService.update(memoOfDeliveryDto));
    }

    @DeleteMapping("/{id}")
    public String deleteMemoOfDelivery(@PathVariable Long id) {
        memoOfDeliveryService.deleteMemoOfDelivery(id);
        return new JSONObject().put("message", "Памятка подачи удалена").toString();
    }
}
