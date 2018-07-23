package com.medullus.fabricrestapi.resources.apis.invoices;


import com.medullus.fabricrestapi.domains.dto.invoice.InvoiceRequest;
import com.medullus.fabricrestapi.domains.dto.invoice.InvoiceResponse;
import com.medullus.fabricrestapi.domains.dto.invoice.InvoiceServicePojo;
import com.medullus.fabricrestapi.services.InvoiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.atomic.AtomicInteger;

@Api(tags = "Invoice")
@RestController
@RequestMapping(path = "/invoices")
public class InvoiceController {
    private static final Logger logger = Logger.getLogger(InvoiceController.class);
    private final AtomicInteger counter = new AtomicInteger(0);
    private InvoiceService invoiceService;
    private InvoiceMapper invoiceMapper;

    @Autowired
    public InvoiceController(InvoiceService invoiceService, InvoiceMapper invoiceMapper){
        this.invoiceService = invoiceService;
        this.invoiceMapper = invoiceMapper;
    }


    @RequestMapping(value = "", method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "update Invoice to ledger")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Invoice updated" ),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error" )
    })
    public InvoiceResponse updateInvoice(@RequestBody InvoiceRequest invoiceRequest){
        InvoiceServicePojo servicePojo = invoiceMapper.mapResourceToServicePojo(invoiceRequest);
        logger.debug("Update Invoice");

        return invoiceMapper.mapServiceObjToResponse(null, null, invoiceService.updateInvoice(servicePojo));
    }

    @RequestMapping(value = "/{refId}/{poNumber}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get Invoice from ledger")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK" ),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error" )
    })
    public InvoiceResponse getInvoices(@PathVariable("refId") String refId,
                                       @PathVariable("poNumber") String poNumber,
                                       @RequestHeader(value = "caller")String caller){
        logger.debug("retrieving :"+refId+poNumber);
        return invoiceMapper.mapServiceObjToResponse(invoiceService.getInvoice(refId,poNumber , caller, null), "Found "+refId+poNumber, null);
    }


    @RequestMapping(value = "", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Add invoice to ledger.Caller must be registered from /users api. RefId, poNumber, and caller is used to create composite key. To get invoice, use refId and poNumber")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK" ),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error" )
    })
    public InvoiceResponse addInvoice(@RequestBody InvoiceRequest invoiceRequest){

        InvoiceServicePojo invoiceServicePojo = invoiceMapper.mapResourceToServicePojo(invoiceRequest);
        logger.debug("adding invoice");
        return invoiceMapper.mapServiceObjToResponse(null,null,invoiceService.addInvoice(invoiceServicePojo));
    }

//    @RequestMapping(value = "", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.OK)
//    @ApiOperation(value = "Add invoice to ledger.Caller must be registered from /users api. RefId, poNumber, and caller is used to create composite key. To get invoice, use refId and poNumber")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "OK" ),
//            @ApiResponse(code = 400, message = "Bad Request"),
//            @ApiResponse(code = 500, message = "Internal Server Error" )
//    })
//    public DeferredResult<ResponseEntity<InvoiceResponse>> addInvoice(@RequestBody InvoiceRequest invoiceRequest){
//
////        InvoiceResponse
//        InvoiceServicePojo invoiceServicePojo = invoiceMapper.mapResourceToServicePojo(invoiceRequest);
//        logger.debug("adding invoice");
////        return invoiceMapper.mapServiceObjToResponse(null,null,invoiceService.addInvoice(invoiceServicePojo));
//
//        DeferredResult<ResponseEntity<InvoiceResponse>> result = new DeferredResult<>();
//
//        new Thread(() -> {
//            result.setResult(ResponseEntity.ok(invoiceMapper.mapServiceObjToResponse(null,null,invoiceService.addInvoice(invoiceServicePojo))));
//        }, "MyThread-" + counter.incrementAndGet()).start();
//
//        return result;
//    }
}
