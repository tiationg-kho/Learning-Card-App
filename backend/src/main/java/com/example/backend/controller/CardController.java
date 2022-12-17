package com.example.backend.controller;

import com.example.backend.dto.CardRequest;
import com.example.backend.model.Card;
import com.example.backend.service.CardService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping("/{studentId}/{cardsetId}")
    public ResponseEntity<Card> create(@PathVariable Integer studentId, @PathVariable Integer cardsetId, @RequestBody @Valid CardRequest cardRequest) {
        Card card = cardService.create(studentId, cardsetId, cardRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(card);
    }

    @GetMapping("/{studentId}/{cardsetId}")
    public ResponseEntity<List<Card>> readAll(@PathVariable Integer studentId, @PathVariable Integer cardsetId) {
        List<Card> cards = cardService.readAll(studentId, cardsetId);
        return ResponseEntity.status(HttpStatus.OK).body(cards);
    }

    @DeleteMapping("/{studentId}/{cardsetId}")
    public ResponseEntity<String> delete(@PathVariable Integer studentId, @PathVariable Integer cardsetId, @RequestParam Integer cardId) {
        cardService.delete(studentId, cardsetId, cardId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{studentId}/{cardsetId}")
    public ResponseEntity<Card> update(@PathVariable Integer studentId, @PathVariable Integer cardsetId, @RequestParam Integer cardId, @RequestBody @Valid CardRequest cardRequest) {
        Card card = cardService.update(studentId, cardsetId, cardId, cardRequest);
        return ResponseEntity.status(HttpStatus.OK).body(card);
    }

    @GetMapping("/{studentId}/{cardsetId}/export.csv")
    public ResponseEntity<byte[]> export(@PathVariable Integer studentId, @PathVariable Integer cardsetId) throws IOException, SQLException {
        ByteArrayOutputStream stream = cardService.export(studentId, cardsetId);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType("application/csv")).header(HttpHeaders.CONTENT_DISPOSITION, "attachment").body(stream.toByteArray());
    }
}
