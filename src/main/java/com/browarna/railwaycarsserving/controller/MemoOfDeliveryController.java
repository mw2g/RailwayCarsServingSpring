package com.browarna.railwaycarsserving.controller;

import com.browarna.railwaycarsserving.dto.MemoOfDeliveryDto;
import com.browarna.railwaycarsserving.service.MemoOfDeliveryService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/memo/delivery")
@AllArgsConstructor
public class MemoOfDeliveryController {

    private final MemoOfDeliveryService memoOfDeliveryService;

    @GetMapping
    public ResponseEntity<List<MemoOfDeliveryDto>> getAllMemoOfDeliverys() {
        return ResponseEntity.status(OK)
                .body(memoOfDeliveryService.getAllMemoOfDeliveries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemoOfDeliveryDto> getMemoOfDeliveryById(@PathVariable Long id) {
        return ResponseEntity.status(OK)
                .body(memoOfDeliveryService.getMemoOfDeliveryById(id));
    }

    @PostMapping
    public ResponseEntity<MemoOfDeliveryDto> createMemoOfDelivery(@RequestBody MemoOfDeliveryDto memoOfDeliveryDto) {
    return ResponseEntity.status(OK)
                .body(memoOfDeliveryService.createMemoOfDelivery(memoOfDeliveryDto));
    }

    @PutMapping
    public ResponseEntity<MemoOfDeliveryDto> updateMemoOfDelivery(@RequestBody MemoOfDeliveryDto memoOfDeliveryDto) {
        return ResponseEntity.status(OK)
                .body(memoOfDeliveryService.updateMemoOfDelivery(memoOfDeliveryDto));
    }

    @DeleteMapping("/{id}")
    public String deleteMemoOfDelivery(@PathVariable Long id) {
        memoOfDeliveryService.deleteMemoOfDelivery(id);
        return new JSONObject().put("message", "Памятка подачи удалена").toString();
    }
}
