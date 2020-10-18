package com.browarna.railwaycarsserving.controller;

import com.browarna.railwaycarsserving.model.WagonGroup;
import com.browarna.railwaycarsserving.service.WagonGroupService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/wagon-group")
@AllArgsConstructor
public class WagonGroupController {

    private final WagonGroupService wagonGroupService;

    @GetMapping
    public ResponseEntity<List<WagonGroup>> getAll() {
        return ResponseEntity.status(OK)
                .body(wagonGroupService.getAll());
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<WagonGroup> getById(@PathVariable Long groupId) {
        return ResponseEntity.status(OK)
                .body(wagonGroupService.getById(groupId));
    }

    @PostMapping
    public ResponseEntity<WagonGroup> create(@RequestBody WagonGroup wagonGroup) {
        return ResponseEntity.status(OK)
                .body(wagonGroupService.create(wagonGroup));
    }

    @PutMapping
    public ResponseEntity update(@RequestBody WagonGroup wagonGroup) {
        wagonGroupService.update(wagonGroup);
//        return new JSONObject().put("message", "Изменения сохранены").toString();
        return ResponseEntity.status(OK).build();
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity delete(@PathVariable Long groupId) {
        wagonGroupService.delete(groupId);
        return ResponseEntity.status(OK).build();
//        return new JSONObject().put("message", "Вид тарифа удален").toString();
    }
}
