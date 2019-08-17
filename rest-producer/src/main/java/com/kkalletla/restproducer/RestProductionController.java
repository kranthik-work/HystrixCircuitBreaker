package com.kkalletla.restproducer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.PathParam;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RestProductionController {

    @Autowired
    //@LoadBalanced
    RestTemplate restTemplate;

    Logger logger = Logger.getLogger(RestProductionController.class);

    @RequestMapping("/getCustomers")
    public String getCustomers(@PathParam("firstName") String firstName,@PathParam("lastName") String lastName){
        //return "Hello "+name;
        logger.info("Request: "+"http://localhost:4444/customer/customers?firstName="+firstName+"&lastName="+lastName);
        return restTemplate.getForObject("http://localhost:4444/customer/customers?firstName="+firstName+"&lastName="+lastName,String.class,firstName);
    }

    @RequestMapping("/deleteCustomers")
    public String deleteCustomers(@PathParam("firstName") String firstName,@PathParam("lastName") String lastName){
        //return "Hello "+name;
        try{
            restTemplate.delete("http://localhost:4444/customer/customers?firstName="+firstName+"&lastName="+lastName);//,String.class,name);
        }catch (Exception e){

            return "False";
        }
        return "True";
    }

    @RequestMapping("/insertCustomers")
    public Customer insertCustomers(@PathParam("firstName") String firstName,@PathParam("lastName") String lastName){
        //return "Hello "+name;
        logger.info("Insert: "+firstName+" "+lastName);

        Customer customer;
        HttpEntity<Customer> request = new HttpEntity<>(new Customer(firstName,lastName));
        try{

           /* HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);*/
            Map<String,String> variables = new HashMap();
            variables.put("firstName",firstName);
            variables.put("lastName",lastName);

            //final HttpEntity<Map<String, String>> entity = new HttpEntity<>(variables , headers);
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            customer = restTemplate.postForObject("http://localhost:4444/customer/customers",request,Customer.class,variables);
            logger.info("Returning:");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return customer;
    }
}
