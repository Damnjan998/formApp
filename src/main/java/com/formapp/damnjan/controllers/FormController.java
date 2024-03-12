package com.formapp.damnjan.controllers;

import com.formapp.damnjan.models.request.FillFormRequestDto;
import com.formapp.damnjan.models.request.FormRequestDto;
import com.formapp.damnjan.models.response.TextResponseMessage;
import com.formapp.damnjan.services.FormService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/form")
public class FormController {

    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    @PostMapping("/create")
    public ResponseEntity<TextResponseMessage> createForm(@RequestBody FormRequestDto formRequestDto) {
        formService.createForm(formRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TextResponseMessage("Form created",
                HttpStatus.CREATED.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TextResponseMessage> deleteFormById(@PathVariable Integer id) {
        formService.deleteForm(id);
        return ResponseEntity.status(HttpStatus.OK).body(new TextResponseMessage("Form deleted",
                HttpStatus.OK.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TextResponseMessage> updateFormById(@PathVariable Integer id,
                                                              @RequestBody FormRequestDto formRequestDto) {
        formService.updateForm(id, formRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(new TextResponseMessage("Form updated",
                HttpStatus.OK.value()));
    }

    @GetMapping("")
    public ResponseEntity getForms(@RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "5") Integer size) {
        return ResponseEntity.ok(formService.getForms(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity getFormById(@PathVariable Integer id) {
        return ResponseEntity.ok(formService.getFormById(id));
    }

    @PostMapping("/fill")
    public ResponseEntity<TextResponseMessage> fillForm(@RequestBody FillFormRequestDto fillFormRequestDto) {

        formService.fillForm(fillFormRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TextResponseMessage("Filled form created",
                HttpStatus.CREATED.value()));
    }

    @DeleteMapping("/fill/{id}")
    public ResponseEntity<TextResponseMessage> deleteFilledForm(@PathVariable Integer id) {

        formService.deleteFilledForm(id);
        return ResponseEntity.status(HttpStatus.OK).body(new TextResponseMessage("Filled form deleted",
                HttpStatus.OK.value()));
    }

    @GetMapping("/fill")
    public ResponseEntity getFilledForms(@RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "5") Integer size) {
        return ResponseEntity.ok(formService.getFilledForms(page, size));
    }

    @GetMapping("/fill/{id}")
    public ResponseEntity getFilledFormById(@PathVariable Integer id) {
        return ResponseEntity.ok(formService.getFilledFormById(id));
    }
}
