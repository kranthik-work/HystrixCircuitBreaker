package com.kkalletla.restconsumer;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {

    @Autowired
    //@LoadBalanced
    private RestTemplate restTemplate;

    Logger logger = Logger.getLogger(CustomerService.class);

    @HystrixCommand(fallbackMethod = "defaultGetCustomers")
    public String getCustomerDetails(String firstName,String lastName){
        logger.info("From getCustomers()");
        logger.info("Request: "+"http://localhost:3333/getCustomers?name="+firstName+"&lastName="+lastName);
        return restTemplate.getForObject("http://localhost:3333/getCustomers?firstName="+firstName+"&lastName="+lastName,String.class,firstName);
        //return restTemplate.getForObject("http://localhost:3333/getCustomers?name="+firstName+"&lastName="+lastName,String.class,firstName);
    }

    private String defaultGetCustomers(String firstName,String lastName){
        logger.info("This id from Default Customers main function");
        return "Hello User!";
    }

    @HystrixCommand(fallbackMethod = "defaultDeleteCustomers")
    public String deleteCustomer(String firstName, String lastName){
        logger.info("From deleteCustomers()");
        return restTemplate.getForObject("http://localhost:3333/deleteCustomers?firstName="+firstName+"&lastName="+lastName,String.class,firstName);
        //return restTemplate.getForObject("http://localhost:3333/deleteCustomers?name="+firstName+"&lastName="+lastName,String.class,firstName);
    }

    private String defaultDeleteCustomers(String firstName,String lastName){
        logger.info("This id from Default Customers main function");
        return "Unable to delete User!";
    }

    @HystrixCommand(fallbackMethod = "defaultDeleteCustomers")
    public String insertCustomer(String firstName, String lastName){
        logger.info("From deleteCustomers()");
        return restTemplate.getForObject("http://localhost:3333/insertCustomers?firstName="+firstName+"&lastName="+lastName,String.class,firstName);
        //return restTemplate.getForObject("http://localhost:3333/deleteCustomers?name="+firstName+"&lastName="+lastName,String.class,firstName);
    }

    private String defaultInsertCustomers(String firstName,String lastName){
        logger.info("This id from Default Customers main function");
        return "Unable to delete User!";
    }
}

//http://localhost:9091/hystrix/monitor?stream=http%3A%2F%2F192.168.1.53%3A9091%2Fhystrix.stream