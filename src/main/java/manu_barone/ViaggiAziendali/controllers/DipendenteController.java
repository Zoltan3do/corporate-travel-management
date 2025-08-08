package manu_barone.ViaggiAziendali.controllers;

import manu_barone.ViaggiAziendali.entities.Dipendente;
import manu_barone.ViaggiAziendali.entities.Viaggio;
import manu_barone.ViaggiAziendali.exceptions.BadRequestException;
import manu_barone.ViaggiAziendali.payloads.DipendenteDTO;
import manu_barone.ViaggiAziendali.services.DipendenteSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {

    @Autowired
    private DipendenteSer dipendenteSer;

    @GetMapping
    public Page<Dipendente> findAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "username") String sortBy) {
        return this.dipendenteSer.findAll(page, size, sortBy);
    }

    @GetMapping("/{dipendenteId}")
    public Dipendente findById(@PathVariable String dipendenteId){
        return this.dipendenteSer.findById(dipendenteId);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente save(@RequestBody @Validated DipendenteDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return this.dipendenteSer.save(body);
    }


    @PatchMapping("/{dipendenteId}/avatar")
    public String addAvatar(@PathVariable("dipendenteId") String dipendenteId, @RequestParam("avatar") MultipartFile file){
        return this.dipendenteSer.aggiungiAvatar(dipendenteId, file);
    }



}
