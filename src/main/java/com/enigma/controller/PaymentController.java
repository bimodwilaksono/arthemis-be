package com.enigma.controller;

import com.enigma.model.Payment;
import com.enigma.model.request.MidtransRequest;
import com.enigma.model.request.PaymentRequest;
import com.enigma.model.response.CommonResponse;
import com.enigma.model.response.SuccessResponse;
import com.enigma.service.PaymentService;
import com.midtrans.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;

    private RestTemplate restTemplate;

    private Config config;

    private final String url = "https://app.sandbox.midtrans.com/snap/v1/transactions";

    @Autowired
    public PaymentController(PaymentService paymentService, RestTemplate restTemplate, Config config) {
        this.paymentService = paymentService;
        this.restTemplate = restTemplate;
        this.config = config;
    }

    @PostMapping("/checkout")
    public ResponseEntity checkout(@RequestBody MidtransRequest midtransRequest) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("U0ItTWlkLXNlcnZlci1IME1qRU8xa0J5T2stOWxFbnBqUkwtWFQ6");
        HttpEntity<MidtransRequest> httpEntity = new HttpEntity<>( midtransRequest,headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        System.out.println(response);
        

//        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Success get token transaction", response));

        return response;

    }


    @GetMapping
    public ResponseEntity getAllpayment(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(defaultValue = "id") String sort
    ) {
        Page<Payment> payments = paymentService.findAll(page, size, direction, sort);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Success Get All payment", payments));
    }

    @GetMapping("/{id}")
    public ResponseEntity getByid(@PathVariable("id") String id) {
        Payment payment = paymentService.findById(id);
        CommonResponse commonResponse = new SuccessResponse<>("Success Finding payment with id: " + id, payment);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

//    @PostMapping
//    public ResponseEntity createPayment(@RequestBody Payment payment){
//        Payment createPayment = paymentService.save(payment);
//        CommonResponse commonResponse = new SuccessResponse<>("Success Creating new payment", createPayment);
//        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
//    }

    @PutMapping("/{id}")
    public ResponseEntity updatePayment(@PathVariable("id") String id, @RequestBody PaymentRequest payment) {
        Payment update = paymentService.update(id, payment);
        CommonResponse commonResponse = new SuccessResponse<>("Success updating payment", update);
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePayment(@PathVariable String id) {
        paymentService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Success deleting payment", null));
    }
}
