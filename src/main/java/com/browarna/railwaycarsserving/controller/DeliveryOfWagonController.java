package com.browarna.railwaycarsserving.controller;

import com.browarna.railwaycarsserving.dto.DeliveryIdListAndMemoIdDto;
import com.browarna.railwaycarsserving.dto.DeliveryOfWagonDto;
import com.browarna.railwaycarsserving.service.DeliveryOfWagonService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/delivery")
@AllArgsConstructor
public class DeliveryOfWagonController {

    private final DeliveryOfWagonService deliveryOfWagonService;

    @GetMapping
    public ResponseEntity<List<DeliveryOfWagonDto>> getAllDeliveryOfWagons() {
        return ResponseEntity.status(OK)
                .body(deliveryOfWagonService.getAllDeliveryOfWagons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryOfWagonDto> getDeliveryOfWagonById(@PathVariable Long id) {
        return ResponseEntity.status(OK)
                .body(deliveryOfWagonService.getDeliveryOfWagonById(id));
    }

    @GetMapping("/memo/{id}")
    public ResponseEntity<List<DeliveryOfWagonDto>> getDeliveryByMemoId(@PathVariable Long id) {
        return ResponseEntity.status(OK)
                .body(deliveryOfWagonService.getDeliveryByMemoId(id));
    }

    @GetMapping("/suitable/{memoId}")
    public ResponseEntity<List<DeliveryOfWagonDto>> getSuitableDelivery(@PathVariable Long memoId) {
        return ResponseEntity.status(OK)
                .body(deliveryOfWagonService.getSuitableDelivery(memoId));
    }

    @GetMapping("/add-memo")
    public String addMemoToDelivery(@Param("deliveryIdToAdd") Long deliveryIdToAdd, @Param("memoId") Long memoId) {
        deliveryOfWagonService.addMemoToDelivery(deliveryIdToAdd, memoId);
        return new JSONObject().put("message", "В общую подачу добавлена памятка").toString();
    }

    @PostMapping("/add-memo-list")
    public String addMemoToDeliveryList(@RequestBody DeliveryIdListAndMemoIdDto body) {
        for (Long deliveryId : body.getDeliveryIds()) {
            deliveryOfWagonService.addMemoToDelivery(deliveryId, body.getMemoId());
        }
        return new JSONObject().put("message", "В памятку добавлены все подходящие подачи").toString();
    }

    @GetMapping("/remove-memo")
    public String removeMemoToDelivery(@Param("deliveryId") Long deliveryId) {
        deliveryOfWagonService.removeMemoToDelivery(deliveryId);
        return new JSONObject().put("message", "Памятка из общей памятки удалена").toString();
    }

    @PostMapping
    public String createDeliveryOfWagon(@RequestBody DeliveryOfWagonDto deliveryOfWagonDto) {
        deliveryOfWagonService.createDeliveryOfWagon(deliveryOfWagonDto);
        return new JSONObject().put("message", "Общая подача создана").toString();
    }

    @PutMapping
    public String updateDeliveryOfWagon(@RequestBody DeliveryOfWagonDto deliveryOfWagonDto) {
        deliveryOfWagonService.updateDeliveryOfWagon(deliveryOfWagonDto);
        return new JSONObject().put("message", "Изменения сохранены").toString();
    }

    @DeleteMapping("/{id}")
    public String deleteDeliveryOfWagon(@PathVariable Long id) {
        deliveryOfWagonService.deleteDeliveryOfWagon(id);
        return new JSONObject().put("message", "Общая подача удалена").toString();
    }
}
