package com.browarna.railwaycarsserving.controller;

import com.browarna.railwaycarsserving.dto.DeliveryIdListAndMemoIdDto;
import com.browarna.railwaycarsserving.dto.DeliveryOfWagonDto;
import com.browarna.railwaycarsserving.model.Owner;
import com.browarna.railwaycarsserving.service.DeliveryOfWagonService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.LOCKED;

@RestController
@RequestMapping("/api/delivery")
@AllArgsConstructor
public class DeliveryOfWagonController {

    private final DeliveryOfWagonService deliveryOfWagonService;

    @GetMapping
    public ResponseEntity<List<DeliveryOfWagonDto>> getAllDeliveryOfWagons() {
        return ResponseEntity.status(OK).body(deliveryOfWagonService.getAllDeliveryOfWagons());
    }

    @GetMapping("/owner")
    public ResponseEntity<List<Owner>> getAllOwners() {
        return ResponseEntity.status(OK).body(deliveryOfWagonService.getAllOwners());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryOfWagonDto> getDeliveryOfWagonById(@PathVariable Long id) {
        return ResponseEntity.status(OK).body(deliveryOfWagonService.getDeliveryOfWagonById(id));
    }

    @GetMapping("/memo/{id}")
    public ResponseEntity<List<DeliveryOfWagonDto>> getDeliveryByMemoId(@PathVariable Long id) {
        return ResponseEntity.status(OK).body(deliveryOfWagonService.getDeliveryByMemoId(id));
    }

    @GetMapping("/suitable/delivery/{memoId}")
    public ResponseEntity<List<DeliveryOfWagonDto>> getSuitableDeliveryForMemoOfDelivery(@PathVariable Long memoId) {
        return ResponseEntity.status(OK).body(deliveryOfWagonService.getSuitableDeliveryForMemoOfDelivery(memoId));
    }

    @GetMapping("/autocomplete/delivery/{wagonNumber}")
    public ResponseEntity<DeliveryOfWagonDto> getDeliveryForAutocomplete(@PathVariable String wagonNumber) {
        return ResponseEntity.status(OK).body(deliveryOfWagonService.getDeliveryForAutocomplete(wagonNumber));
    }

    @GetMapping("/suitable/dispatch/{memoId}")
    public ResponseEntity<List<DeliveryOfWagonDto>> getSuitableDeliveryForMemoOfDispatch(@PathVariable Long memoId) {
        return ResponseEntity.status(OK).body(deliveryOfWagonService.getSuitableDeliveryForMemoOfDispatch(memoId));
    }

    @GetMapping("/add-memo-of-delivery")
    public ResponseEntity<DeliveryOfWagonDto> addMemoOfDeliveryToDelivery(
            @Param("deliveryIdToAdd") Long deliveryIdToAdd, @Param("memoId") Long memoId) {
//        deliveryOfWagonService.addMemoOfDeliveryToDelivery(deliveryIdToAdd, memoId);
//        return new JSONObject().put("message", "В общую подачу добавлена памятка подачи").toString();
        return ResponseEntity.status(OK)
                .body(deliveryOfWagonService.addMemoOfDeliveryToDelivery(deliveryIdToAdd, memoId));
    }

    @GetMapping("/add-memo-of-dispatch")
    public String addMemoOfDispatchToDelivery(
            @Param("deliveryIdToAdd") Long deliveryIdToAdd, @Param("memoId") Long memoId) {
        deliveryOfWagonService.addMemoOfDispatchToDelivery(deliveryIdToAdd, memoId);
        return new JSONObject().put("message", "В общую подачу добавлена памятка уборки").toString();
    }

    @PostMapping("/add-memo-of-delivery-list")
    public ResponseEntity<List<DeliveryOfWagonDto>> addMemoToDeliveryList(
            @RequestBody DeliveryIdListAndMemoIdDto body) {
        List<DeliveryOfWagonDto> deliveryOfWagonDtoList = new ArrayList<>();
        for (Long deliveryId : body.getDeliveryIds()) {
            deliveryOfWagonDtoList.add(deliveryOfWagonService
                    .addMemoOfDeliveryToDelivery(deliveryId, body.getMemoId()));
        }
        return ResponseEntity.status(OK).body(deliveryOfWagonDtoList);
    }

    @PostMapping("/add-memo-of-dispatch-list")
    public String addMemoOfDispatchToDeliveryList(@RequestBody DeliveryIdListAndMemoIdDto body) {
        for (Long deliveryId : body.getDeliveryIds()) {
            deliveryOfWagonService.addMemoOfDispatchToDelivery(deliveryId, body.getMemoId());
        }
        return new JSONObject().put("message", "В памятку уборки добавлены все подходящие подачи").toString();
    }

    @GetMapping("/remove-memo-of-delivery")
    public String removeMemoOfDeliveryFromDelivery(@Param("deliveryId") Long deliveryId) {
        deliveryOfWagonService.removeMemoOfDeliveryFromDelivery(deliveryId);
        return new JSONObject().put("message", "Памятка из общей памятки удалена").toString();
    }

    @PostMapping("/remove-memo-of-delivery-list")
    public String removeMemoOfDeliveryFromDeliveryList(@RequestBody Long[] deliveryIds) {
        for (Long deliveryId : deliveryIds) {
            deliveryOfWagonService.removeMemoOfDeliveryFromDelivery(deliveryId);
        }
        return new JSONObject().put("message", "Памятка из удалена из всех вагонов").toString();
    }

    @PostMapping("/remove-memo-of-dispatch-list")
    public String removeMemoOfDispatchFromDeliveryList(@RequestBody Long[] deliveryIds) {
        for (Long deliveryId : deliveryIds) {
            deliveryOfWagonService.removeMemoOfDispatchFromDelivery(deliveryId);
        }
        return new JSONObject().put("message", "Памятка из удалена из всех вагонов").toString();
    }

    @GetMapping("/remove-memo-of-dispatch")
    public String removeMemoOfDispatchFromDelivery(@Param("deliveryId") Long deliveryId) {
        deliveryOfWagonService.removeMemoOfDispatchFromDelivery(deliveryId);
        return new JSONObject().put("message", "Памятка из общей памятки удалена").toString();
    }

    @PostMapping
    public ResponseEntity<DeliveryOfWagonDto> createDeliveryOfWagon(
            @RequestBody DeliveryOfWagonDto deliveryOfWagonDto) {
        DeliveryOfWagonDto delivery = deliveryOfWagonService.createDeliveryOfWagon(deliveryOfWagonDto);
        HttpStatus status = OK;
        if (delivery.getDeliveryId() == null) {
            status = LOCKED;
        }
        return ResponseEntity.status(status).body(delivery);
    }

    @PutMapping
    public ResponseEntity<DeliveryOfWagonDto> updateDeliveryOfWagon(
            @RequestBody DeliveryOfWagonDto deliveryOfWagonDto) {
        return ResponseEntity.status(OK)
                .body(deliveryOfWagonService.updateDeliveryOfWagon(deliveryOfWagonDto));
    }

    @DeleteMapping("/{id}")
    public String deleteDeliveryOfWagon(@PathVariable Long id) {
        deliveryOfWagonService.deleteDeliveryOfWagon(id);
        return new JSONObject().put("message", "Общая подача удалена").toString();
    }
}
