package com.task.httpserver.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@RestController
public class HexController {

    private static final String ALGORITHM = "SHA1PRNG";

    @RequestMapping(value = "/generate", method = {RequestMethod.GET})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Hex String was generated successfully"),
            @ApiResponse(code = 500, message = "Server error") })
    @ApiOperation(value = "Generate hexadecimal 32 chars string", httpMethod = "GET")
    public ResponseEntity<String> generateHexString() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance(ALGORITHM);
        byte[] arrayBytes = new byte[16];
        sr.nextBytes(arrayBytes);
        /* Preventing stripping leading zero */
        if (arrayBytes[0] >= 0 && arrayBytes[0] < 0x10) {
            arrayBytes[0] += 0x10;
        }
        BigInteger bigInteger = new BigInteger(1, arrayBytes);
        return new ResponseEntity(bigInteger.toString(16), HttpStatus.OK);
    }
}
