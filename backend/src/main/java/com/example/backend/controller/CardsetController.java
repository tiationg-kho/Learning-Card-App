package com.example.backend.controller;

import com.example.backend.constant.CardsetCategory;
import com.example.backend.constant.CardsetStatus;
import com.example.backend.dto.CardsetRequest;
import com.example.backend.model.Cardset;
import com.example.backend.service.CardsetService;
import com.example.backend.util.Page;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
public class CardsetController {

    @Autowired
    private CardsetService cardsetService;

    @PostMapping("/{studentId}")
    public ResponseEntity<Cardset> create(@PathVariable Integer studentId, @RequestBody @Valid CardsetRequest cardsetRequest) {
        Cardset cardset = cardsetService.create(studentId, cardsetRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cardset);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Page<Cardset>> readAll(@PathVariable Integer studentId, @RequestParam(required = false) CardsetCategory cardsetCategory, @RequestParam(required = false) CardsetStatus cardsetStatus, @RequestParam(required = false) String keyword, @RequestParam(defaultValue = "6") Integer limit, @RequestParam(defaultValue = "0") Integer offset) {
        Page<Cardset> page = cardsetService.readAll(studentId, cardsetCategory, cardsetStatus, keyword, limit, offset);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<String> delete(@PathVariable Integer studentId, @RequestParam Integer cardsetId) {
        cardsetService.delete(studentId, cardsetId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
