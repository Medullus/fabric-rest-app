package com.medullus.fabricrestapi.resources.apis.document;

import com.google.gson.Gson;
import com.medullus.fabricrestapi.domains.dto.ResponseHeader;
import com.medullus.fabricrestapi.domains.dto.document.DocumentRequest;
import com.medullus.fabricrestapi.domains.dto.document.DocumentResponse;
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
import org.springframework.web.bind.annotation.*;

//TODO
import java.util.Map;
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
    public DocumentResponse getEntityMaster(@PathVariable("documentPK") String documentPK,
                                            @RequestHeader(value = "caller") String caller) {
        logger.debug("retrieving :" + documentPK);
        return documentMapper.mapServiceObjToResponse(documentService.getDocument(documentPK, caller, null), "Found " + documentPK, null);
    }
//
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
                                        @RequestBody DocumentRequest documentRequest) {
        DocumentServicePojo documentServicePojo = documentMapper.mapResourceToServicePojo(documentRequest);
        logger.debug("adding document");
        return documentMapper.mapServiceObjToResponse(null, null, documentService.addDocument(documentServicePojo, documentPK, documentRequest.getRequestHeader().getCaller()));
    }
//
//
//    @RequestMapping(value = "test/{docPk}", method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.OK)
//    @ApiOperation(value = "added Document to ledger")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "Document added"),
//            @ApiResponse(code = 400, message = "Bad Request"),
//            @ApiResponse(code = 500, message = "Internal Server Error")
//    })
//    public DocumentResponse testAddDoc(@PathVariable("docPk")String docPK,
//                                    @RequestBody DocumentRequest documentRequest){
//        System.out.println("pass this to cc for key to store doc "+ docPK);
//
//        System.out.println(documentRequest.getRequestHeader().getCaller());
//        System.out.println(documentRequest.getRequestHeader().getOrg());
//
//
//        for(Map.Entry<String, Object> entry : documentRequest.getDocuments().entrySet()){
//            //map example to get k,v
//            System.out.printf("key %s : value %s", entry.getKey(), entry.getValue());
//        }
//
//        //map to json string example
//        Gson objGson = new Gson();
//        String anyJsonStr = objGson.toJson(documentRequest.getDocuments());
//
//        System.out.println(anyJsonStr);
//
//        DocumentResponse resp = new DocumentResponse();
//        ResponseHeader responseHeader = new ResponseHeader();
//        responseHeader.setTxID("txIDReturned by Service");
//
//        resp.setResponseHeader(responseHeader);
//
//        return resp;
//
//    }
//

}
