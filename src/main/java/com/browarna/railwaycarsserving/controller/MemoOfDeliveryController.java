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
                .body(memoOfDeliveryService.getAllMemoOfDeliverys());
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

        //        return new JSONObject().put("message", "Пользователь создан")
//                                .put("memoId", memoId).toString();
    }

    @PutMapping
    public String updateMemoOfDelivery(@RequestBody MemoOfDeliveryDto memoOfDeliveryDto) {
        memoOfDeliveryService.updateMemoOfDelivery(memoOfDeliveryDto);
        return new JSONObject().put("message", "Изменения сохранены").toString();
    }

    @DeleteMapping("/{id}")
    public String deleteMemoOfDelivery(@PathVariable Long id) {
        memoOfDeliveryService.deleteMemoOfDelivery(id);
        return new JSONObject().put("message", "Пользователь удален").toString();
    }
}
