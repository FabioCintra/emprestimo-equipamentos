package io.github.fabiocintra.loan;

import io.github.fabiocintra.loan.dto.LoanRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService service;
    private final LoanMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createLoan(@RequestBody @Valid LoanRequest request){
        LoanModel loan = mapper.toModel(request);
        service.createLoan(loan);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void returnLoan(@PathVariable String id){
        UUID loanId = UUID.fromString(id);
        service.returnLoan(loanId);
    }

}
