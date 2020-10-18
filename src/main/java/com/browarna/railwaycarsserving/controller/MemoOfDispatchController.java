package com.browarna.railwaycarsserving.controller;

import com.browarna.railwaycarsserving.dto.MemoIdListAndStatementIdDto;
import com.browarna.railwaycarsserving.dto.MemoOfDispatchDto;
import com.browarna.railwaycarsserving.service.MemoOfDispatchService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/memo/dispatch")
@AllArgsConstructor
public class MemoOfDispatchController {

    private final MemoOfDispatchService memoOfDispatchService;

    @GetMapping
    public ResponseEntity<List<MemoOfDispatchDto>> getAllMemoOfDispatchs() {
        return ResponseEntity.status(OK)
                .body(memoOfDispatchService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemoOfDispatchDto> getMemoOfDispatchById(@PathVariable Long id) {
        return ResponseEntity.status(OK)
                .body(memoOfDispatchService.getById(id));
    }

    @PostMapping
    public ResponseEntity<MemoOfDispatchDto> createMemoOfDispatch(@RequestBody MemoOfDispatchDto memoOfDispatchDto) {
    return ResponseEntity.status(OK)
                .body(memoOfDispatchService.create(memoOfDispatchDto));
    }

    @PutMapping
    public String updateMemoOfDispatch(@RequestBody MemoOfDispatchDto memoOfDispatchDto) {
        memoOfDispatchService.update(memoOfDispatchDto);
        return new JSONObject().put("message", "Изменения сохранены").toString();
    }

    @DeleteMapping("/{id}")
    public String deleteMemoOfDispatch(@PathVariable Long id) {
        memoOfDispatchService.delete(id);
        return new JSONObject().put("message", "Памятка уборки удалена").toString();
    }

    @GetMapping("/suitable/{statementId}")
    public ResponseEntity<List<MemoOfDispatchDto>> getSuitableMemoForStatement(@PathVariable Long statementId) {
        return ResponseEntity.status(OK).body(memoOfDispatchService.getSuitableMemoForControllerStatement(statementId));
    }

    @GetMapping("/add-controller-statement")
    public String addControllerStatementToMemoOfDispatch(
            @Param("memoIdToAdd") Long memoIdToAdd, @Param("statementId") Long statementId) {
        memoOfDispatchService.addControllerStatementToMemoOfDispatch(memoIdToAdd, statementId);
        return new JSONObject().put("message", "В памятку уборки добавлена ведомость").toString();
    }

    @PostMapping("/add-controller-statement-list")
    public String addControllerStatementToMemoOfDispatchList(
            @RequestBody MemoIdListAndStatementIdDto body) {
        List<MemoOfDispatchDto> memoOfDispatchDtoList = new ArrayList<>();
        for (Long memoId : body.getMemoIds()) {
            memoOfDispatchService.addControllerStatementToMemoOfDispatch(memoId, body.getStatementId());
        }
        return new JSONObject().put("message", "Во все памятки добавлена ведомость").toString();
    }

    @GetMapping("/remove-controller-statement")
    public String removeControllerStatementFromMemo(@Param("memoId") Long memoId) {
        memoOfDispatchService.removeControllerStatementFromMemo(memoId);
        return new JSONObject().put("message", "Ведомость из памятки удалена").toString();
    }

    @PostMapping("/remove-controller-statement-list")
    public String removeControllerStatementFromMemoList(@RequestBody Long[] memoIds) {
        for (Long memoId : memoIds) {
            memoOfDispatchService.removeControllerStatementFromMemo(memoId);
        }
        return new JSONObject().put("message", "Ведомость удалена из всех памяток").toString();
    }
}
