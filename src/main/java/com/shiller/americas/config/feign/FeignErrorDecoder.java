package com.shiller.americas.config.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class FeignErrorDecoder extends ErrorDecoder.Default {

    private ErrorDecoder defaultDecoder = new ErrorDecoder.Default();

    /**
     * To get cause of error.
     *
     * @param methodKey method tha is invoke.
     * @param response response relationship.
     */
    @Override
    public Exception decode(String methodKey, Response response) {
      Exception defaultException = defaultDecoder.decode(methodKey, response);
      System.out.println("Exception catched: "+defaultException.getMessage());


      log.debug(" FeignClient Exception Response " + response.body());

      switch (response.status()) {

        case 400:

          return new IllegalArgumentException(response.body().toString());

        case 404:
          System.out.println("Entra al  404");
          return new  ResponseStatusException(HttpStatus.NOT_FOUND, response.body().toString());

        case 429:
          System.out.println("Retry after: "+response.headers().get("Retry-After"));
          return defaultException;
        default:
          return new Exception(response.body().toString());
      }  }

  }
