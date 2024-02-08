package com.leocalheiros.paymentsystem.controller;

import com.leocalheiros.paymentsystem.dto.PixChargeRequest;
import com.leocalheiros.paymentsystem.service.PixService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pix")
public class PixController {

    public PixController(PixService pixService){
        this.pixService = pixService;
    }

    private final PixService pixService;

    @GetMapping
    @Operation(summary = "Create EVP PIX key in EFI bank",
            description = "Create EVP PIX key in EFI bank",
            tags = {"Pix"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    }),
            @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content(mediaType = "application/json")),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content(mediaType = "application/json")),
            @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity pixCreateEVP(){
        JSONObject response = this.pixService.pixCreateEVP();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response.toString());
    }

    @PostMapping
    @Operation(summary = "Create PIX billing with QR Code and copy and paste code",
            description = "Create PIX billing with QR Code and copy and paste code",
            tags = {"Pix"}, responses = {
            @ApiResponse(description = "Success", responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    }),
            @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content(mediaType = "application/json")),
            @ApiResponse(description = "Unprocessable Entity", responseCode = "422", content = @Content(mediaType = "application/json")),
            @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity pixCreateCharge(@RequestBody PixChargeRequest pixChargeRequest){
        JSONObject response = this.pixService.pixCreateCharge(pixChargeRequest);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response.toString());
    }
}
