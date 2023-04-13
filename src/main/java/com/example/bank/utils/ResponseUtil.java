package com.example.bank.utils;

public class ResponseUtil {

    /**
     * Represent a resource list
     * @param data the resource list to be responded
     * @param total the total record of resources
     * @param success the status of the response (true/false)
     * @param message the message of response
     * @return an object of ResponseDTO
     */
    public static ResponseDTO list(Object data, long total, boolean success, String message) {
        ResponseDTO response = new ResponseDTO();
        response.setData(data);
        response.setSuccess(success);
        response.setMessage(message);
        response.setTotal(total);
        return response;
    }

    /**
     * Represent a single resource
     * @param data the resource object to be responded
     * @param success the status of the response (true/false)
     * @param message the message of response
     * @return an object of ResponseDTO
     */
    public static ResponseDTO object(Object data, boolean success, String message) {
        ResponseDTO response = new ResponseDTO();
        response.setData(data);
        response.setSuccess(success);
        response.setMessage(message);
        response.setTotal(data != null ? 1 : 0);
        return response;
    }

}
