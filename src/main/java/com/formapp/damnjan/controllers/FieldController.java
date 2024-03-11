package com.formapp.damnjan.controllers;

import com.formapp.damnjan.models.request.FieldRequestDto;
import com.formapp.damnjan.models.response.TextResponseMessage;
import com.formapp.damnjan.services.FieldService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/field")
public class FieldController {

    private final FieldService fieldService;

    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @PostMapping("/create")
    public ResponseEntity<TextResponseMessage> createForm(@RequestBody FieldRequestDto fieldRequestDto) {
        fieldService.createField(fieldRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TextResponseMessage("Field created",
                HttpStatus.CREATED.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TextResponseMessage> deleteFieldById(@PathVariable Integer id) {
        fieldService.deleteField(id);
        return ResponseEntity.status(HttpStatus.OK).body(new TextResponseMessage("Field deleted",
                HttpStatus.OK.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TextResponseMessage> updateFormById(@PathVariable Integer id,
                                                              @RequestBody FieldRequestDto fieldRequestDto) {
        fieldService.updateField(id, fieldRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(new TextResponseMessage("Field updated",
                HttpStatus.OK.value()));
    }

    @GetMapping("")
    public ResponseEntity getFields(@RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "5") Integer size) {
        return ResponseEntity.ok(fieldService.getFields(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity getFieldById(@PathVariable Integer id) {
        fieldService.getFieldById(id);
        return ResponseEntity.ok(fieldService.getFieldById(id));
    }
}