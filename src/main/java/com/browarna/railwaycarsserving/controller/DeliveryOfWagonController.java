package com.browarna.railwaycarsserving.controller;

import com.browarna.railwaycarsserving.dto.BaseRateAndPenaltyResponse;
import com.browarna.railwaycarsserving.dto.DeliveryIdListAndMemoIdDto;
import com.browarna.railwaycarsserving.dto.DeliveryOfWagonDto;
import com.browarna.railwaycarsserving.dto.StatementWithRateRequest;
import com.browarna.railwaycarsserving.model.Owner;
import com.browarna.railwaycarsserving.service.DeliveryOfWagonService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.LOCKED;

@RestController
@RequestMapping("/api/delivery")
@AllArgsConstructor
public class DeliveryOfWagonController {

    private final DeliveryOfWagonService deliveryOfWagonService;

    @GetMapping("/{afterDate}/{beforeDate}")
    public ResponseEntity<List<DeliveryOfWagonDto>> getAllDeliveryOfWagons(
            @PathVariable Date afterDate, @PathVariable Date beforeDate) {
        return ResponseEntity.status(OK).body(deliveryOfWagonService.getAllDeliveryOfWagons(afterDate, beforeDate));
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
        HttpStatus status = OK;
        DeliveryOfWagonDto delivery = deliveryOfWagonService.updateDeliveryOfWagon(deliveryOfWagonDto);
        if (delivery == null){
            status = LOCKED;
        }
        return ResponseEntity.status(status).body(delivery);
    }

    @DeleteMapping("/{id}")
    public String deleteDeliveryOfWagon(@PathVariable Long id) {
        deliveryOfWagonService.deleteDeliveryOfWagon(id);
        return new JSONObject().put("message", "Общая подача удалена").toString();
    }

    @GetMapping("/owner")
    public ResponseEntity<List<Owner>> getAllOwners() {
        return ResponseEntity.status(OK).body(deliveryOfWagonService.getAllOwners());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryOfWagonDto> getDeliveryOfWagonById(@PathVariable Long id) {
        return ResponseEntity.status(OK).body(deliveryOfWagonService.getDeliveryOfWagonById(id));
    }
//
//    @GetMapping("/memo/{id}")
//    public ResponseEntity<List<DeliveryOfWagonDto>> getDeliveryByMemoId(@PathVariable Long id) {
//        return ResponseEntity.status(OK).body(deliveryOfWagonService.getDeliveryByMemoId(id));
//    }

    @GetMapping("/autocomplete/delivery/{wagonNumber}")
    public ResponseEntity<DeliveryOfWagonDto> getDeliveryForAutocomplete(@PathVariable String wagonNumber) {
        return ResponseEntity.status(OK).body(deliveryOfWagonService.getDeliveryForAutocomplete(wagonNumber));
    }

    @GetMapping("/suitable/delivery/{memoId}")
    public ResponseEntity<List<DeliveryOfWagonDto>> getSuitableDeliveryForMemoOfDelivery(@PathVariable Long memoId) {
        return ResponseEntity.status(OK).body(deliveryOfWagonService.getSuitableDeliveryForMemoOfDelivery(memoId));
    }

    @GetMapping("/suitable/dispatch/{memoId}")
    public ResponseEntity<List<DeliveryOfWagonDto>> getSuitableDeliveryForMemoOfDispatch(@PathVariable Long memoId) {
        return ResponseEntity.status(OK).body(deliveryOfWagonService.getSuitableDeliveryForMemoOfDispatch(memoId));
    }

    @GetMapping("/add-memo-of-delivery")
    public ResponseEntity addMemoOfDeliveryToDelivery(
            @Param("deliveryIdToAdd") Long deliveryIdToAdd, @Param("memoId") Long memoId) {
        HttpStatus status = deliveryOfWagonService.addMemoOfDeliveryToDelivery(deliveryIdToAdd, memoId) ? OK : NOT_FOUND;
        return ResponseEntity.status(status).build();
    }

    @GetMapping("/add-memo-of-dispatch")
    public String addMemoOfDispatchToDelivery(
            @Param("deliveryIdToAdd") Long deliveryIdToAdd, @Param("memoId") Long memoId) {
        deliveryOfWagonService.addMemoOfDispatchToDelivery(deliveryIdToAdd, memoId);
        return new JSONObject().put("message", "В общую подачу добавлена памятка уборки").toString();
    }

    @PostMapping("/base-rate-and-penalty")
    public ResponseEntity<BaseRateAndPenaltyResponse> getBaseRateAndPenaltyById(@RequestBody StatementWithRateRequest request) {
        return ResponseEntity.status(OK).body(deliveryOfWagonService.getBaseRateAndPenaltyById(request));
    }

    @PostMapping("/add-memo-of-delivery-list")
    public ResponseEntity addMemoToDeliveryList(@RequestBody DeliveryIdListAndMemoIdDto body) {
        HttpStatus status = OK;
        for (Long deliveryId : body.getDeliveryIds()) {
            status = deliveryOfWagonService.addMemoOfDeliveryToDelivery(deliveryId, body.getMemoId()) ? status : NOT_FOUND;
        }
        return ResponseEntity.status(status).build();
    }

    @PostMapping("/add-memo-of-dispatch-list")
    public ResponseEntity addMemoOfDispatchToDeliveryList(@RequestBody DeliveryIdListAndMemoIdDto body) {
        HttpStatus status = OK;
        for (Long deliveryId : body.getDeliveryIds()) {
            status = deliveryOfWagonService.addMemoOfDispatchToDelivery(deliveryId, body.getMemoId()) ? status : NOT_FOUND;
        }
        return ResponseEntity.status(status).build();
    }

    @GetMapping("/remove-memo-of-delivery")
    public ResponseEntity removeMemoOfDeliveryFromDelivery(@Param("deliveryId") Long deliveryId) {
        HttpStatus status = deliveryOfWagonService.removeMemoOfDeliveryFromDelivery(deliveryId) ? OK : NOT_FOUND;
        return ResponseEntity.status(status).build();
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
}
