package com.medullus.fabricrestapi.resources.apis.document;

import com.google.gson.Gson;
import com.medullus.fabricrestapi.domains.dto.ResponseHeader;
import com.medullus.fabricrestapi.domains.dto.document.DocumentRequest;
import com.medullus.fabricrestapi.domains.dto.document.DocumentResponse;
import com.medullus.fabricrestapi.domains.dto.document.DocumentServiceBulkPojo;
import com.medullus.fabricrestapi.domains.dto.document.DocumentServicePojo;
import com.medullus.fabricrestapi.services.DocumentService;
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

//TODO
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

//
@Api(tags = "Document")
@RestController
@RequestMapping(path = "/documents")
public class DocumentController {
    private static final Logger logger = Logger.getLogger(DocumentController.class);
//
    private DocumentService documentService;
    private DocumentMapper documentMapper;
//
    @Autowired
    public DocumentController(DocumentService documentService, DocumentMapper documentMapper) {
        this.documentService = documentService;
        this.documentMapper = documentMapper;
    }
//
////    @RequestMapping(value = "/{documentPK}", method = RequestMethod.PUT)
////    @ResponseBody
////    @ResponseStatus(HttpStatus.OK)
////    @ApiOperation(value = "update Document to ledger")
////    @ApiResponses({
////            @ApiResponse(code = 200, message = "Document updated"),
////            @ApiResponse(code = 400, message = "Bad Request"),
////            @ApiResponse(code = 500, message = "Internal Server Error")
////    })
////    public DocumentResponse updateDocument(@PathVariable("documentPK") String documentPK,
////                                           @RequestHeader(value = "caller") String caller,
////                                           @RequestHeader(value = "org") String org,
////                                           @RequestBody DocumentRequest documentRequest) {
////        DocumentServicePojo servicePojo = documentMapper.mapResourceToServicePojo(documentRequest);
////        logger.debug("Update Document");
////
////        return documentMapper.mapServiceObjToResponse(null, null, documentService.updateDocument(servicePojo, documentPK, caller));
////    }
//
    @RequestMapping(value = "/{documentPK}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get Generic Document from ledger")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public DocumentResponse getDoc(@PathVariable("documentPK") String documentPK,
                                            @RequestHeader(value = "caller") String caller) {
        logger.debug("retrieving :" + documentPK);
        return documentMapper.mapServiceObjToResponse(documentService.getDocument(documentPK, caller, null), "Found " + documentPK, null);
    }
//

    @RequestMapping(value = "/{documentPK}", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Add any generic 'non-nested' JSON to ledger.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Document added"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public DocumentResponse addDocument(@PathVariable("documentPK") String documentPK,
                                        @RequestBody DocumentRequest documentRequest) throws ExecutionException, InterruptedException {
        DocumentServiceBulkPojo documentServiceBulkPojo = documentMapper.mapResourceToServicePojo(documentRequest);
        logger.debug("adding document");
        return documentMapper.mapServiceObjToResponse(null,"added all", documentService.addDocument(documentServiceBulkPojo, documentPK, documentRequest.getRequestHeader().getCaller()));
    }
//
//    @RequestMapping(value = "/{documentPK}", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.OK)
//    @ApiOperation(value = "Add any generic 'non-nested' JSON to ledger.")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "Document added"),
//            @ApiResponse(code = 400, message = "Bad Request"),
//            @ApiResponse(code = 500, message = "Internal Server Error")
//    })
//    public DeferredResult<ResponseEntity<?>> addDocument(@PathVariable("documentPK") String documentPK,
//                                                         @RequestBody DocumentRequest documentRequest) {
//        DocumentServicePojo documentServicePojo = documentMapper.mapResourceToServicePojo(documentRequest);
//        logger.debug("adding document");
//
//        DeferredResult<ResponseEntity<?>> result = new DeferredResult<>();
//
//        new Thread(() ->{
//            result.setResult(ResponseEntity.ok(documentMapper.mapServiceObjToResponse(null, null, documentService.addDocument(documentServicePojo, documentPK, documentRequest.getRequestHeader().getCaller()))));
//        }, "MyThread-" + counter.incrementAndGet()).start();
//
//        return result;
//    }

//    @RequestMapping(value = "/{documentPK}", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.OK)
//    @ApiOperation(value = "Add any generic 'non-nested' JSON to ledger.")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "Document added"),
//            @ApiResponse(code = 400, message = "Bad Request"),
//            @ApiResponse(code = 500, message = "Internal Server Error")
//    })
//    public Callable<DocumentResponse> addDocument(@PathVariable("documentPK") String documentPK,
//                                                  @RequestBody DocumentRequest documentRequest) {
//        DocumentServicePojo documentServicePojo = documentMapper.mapResourceToServicePojo(documentRequest);
//        logger.debug("adding document");
////        return documentMapper.mapServiceObjToResponse(null, null, documentService.addDocument(documentServicePojo, documentPK, documentRequest.getRequestHeader().getCaller()));
//
//        return () -> documentMapper.mapServiceObjToResponse(null, null, documentService.addDocument(documentServicePojo, documentPK, documentRequest.getRequestHeader().getCaller()));
//    }



    private final AtomicInteger counter = new AtomicInteger(0);

}
