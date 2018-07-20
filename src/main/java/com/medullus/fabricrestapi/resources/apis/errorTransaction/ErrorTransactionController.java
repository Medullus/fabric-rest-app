package com.medullus.fabricrestapi.resources.apis.errorTransaction;

import com.medullus.fabricrestapi.domains.dto.errorTransaction.ErrorTransactionResponse;
import com.medullus.fabricrestapi.services.ErrorTransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Error Transactions")
@RestController
@RequestMapping(path = "/mismatches", produces = MediaType.APPLICATION_JSON_VALUE)
public class ErrorTransactionController {

    private static final Logger logger = Logger.getLogger(ErrorTransactionController.class);

    private ErrorTransactionService errorTransactionService;
    private ErrorTransactionMapper errorTransactionMapper;

    @Autowired
    public ErrorTransactionController(ErrorTransactionService errorTransactionService, ErrorTransactionMapper errorTransactionMapper){
        this.errorTransactionMapper = errorTransactionMapper;
        this.errorTransactionService = errorTransactionService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get mismatches from ledger. unmatched PO and Invoices must be submited for list to build")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public ErrorTransactionResponse getUnmatched(@RequestHeader(value = "caller") String caller) {
        logger.debug("retrieving error transactions");

        return errorTransactionMapper.mapServiceObjToResponse(errorTransactionService.getErrorTransaction(caller, null), "Unmatched Doc List", null);
    }


    @RequestMapping(value = "/delete/{caller}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete error transaction list")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public void deleteUnmatch(@PathVariable("caller")String caller){
        errorTransactionService.deleteErrorTransaction(caller);
    }
}
