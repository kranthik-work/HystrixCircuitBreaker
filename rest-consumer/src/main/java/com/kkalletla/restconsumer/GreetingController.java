package com.kkalletla.restconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.PathParam;

@RestController
@EnableHystrixDashboard
public class GreetingController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping("/getCustomers")
    public String getCustomers(@PathParam("firstName") String firstName,@PathParam("lastName") String lastName) {
        return customerService.getCustomerDetails(firstName,lastName);
    }

    @RequestMapping("/deleteCustomers")
    public String deleteCustomers(@PathParam("firstName") String firstName,@PathParam("lastName") String lastName) {
        return customerService.deleteCustomer(firstName,lastName);
    }

    @RequestMapping("/insertCustomers")
    public String insertCustomers(@PathParam("firstName") String firstName,@PathParam("lastName") String lastName) {
        return customerService.insertCustomer(firstName,lastName);
    }
}
