package com.quest.etna.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.quest.etna.model.Address;
import com.quest.etna.model.User;
import com.quest.etna.repositories.AddressRepository;
import com.quest.etna.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableJpaRepositories("com.quest.etna.repositories")
public class AddressController {

    private static AddressRepository addressRepository;
    private static UserRepository userRepositoryAddress;

    @Autowired
    private AddressRepository addressRepo;
    
    @Autowired
    private UserRepository userRepo;
    
    @PostConstruct
    private void init() {
        addressRepository = this.addressRepo;
        userRepositoryAddress = this.userRepo;
    }
    
    @RequestMapping(value = "/address", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> readAll() {
        Iterable<Address> addresses = addressRepository.findAll();
        ArrayList<HashMap<String, String>> jsonResponse = Address.buildMultipleJson(addresses);
        return new ResponseEntity<Object>(jsonResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/address/{addressId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> readById(@PathVariable int addressId) {
        if (addressId < 0) {
            HashMap<String, String> error = new HashMap<String, String>();
            error.put("Error", "Invalid id passed (id < 0) !");
            return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
        }

        Optional<Address> addressOpt = addressRepository.findById(addressId);
        Address address = null;

        try {
            address = addressOpt.get();
        } catch (Exception e) {
            HashMap<String, String> error = new HashMap<String, String>();
            error.put("Error", "No address found for id : " + addressId);
            return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
        }
        if (address != null) {
            HashMap<String, String> jsonResponse = address.buildJson();
            return new ResponseEntity<Object>(jsonResponse, HttpStatus.OK);
        } else {
            HashMap<String, String> error = new HashMap<String, String>();
            error.put("Error", "No address found for id : " + addressId);
            return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/address", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> create(@RequestBody Map<String, String> body){
        String street = body.get("street");
        String postalCode = body.get("postalCode");
        String city = body.get("city");
        String country = body.get("country");
        Integer user_id = -1;
        if (body.get("user") != null) {
            user_id = Integer.parseInt(body.get("user"));
        }

        Optional<User> userOpt = userRepositoryAddress.findById(user_id);
        User user = null;
        try  {
            user = userOpt.get();
        } catch (Exception e) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", "This user dosn't exist !");
            return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
        }
        
        if (street == null || street == "" || postalCode == null || postalCode == "" || city == null || city == "" || country == null || country == "") {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", "One parameter is missing !");
            return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
        }
        else if (user_id != null && user == null) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", "No user with the id : " + user_id + " was found !");
            return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
        }
        try {
            Address savingResponse = addressRepository.save(new Address(street, postalCode, city, country, user));
            if (savingResponse == null) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("Error", "Address couldn't be created");
                return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
            }

            Map<String, String> map = new HashMap<String, String>();
            map.put("street", savingResponse.getStreet());
            map.put("postalCode", savingResponse.getPostalCode());
            map.put("city", savingResponse.getCity());
            map.put("country", savingResponse.getCountry());
            return new ResponseEntity<Object>(map, HttpStatus.CREATED);
        } catch(Exception e) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", e.toString());
            return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/address/{addressId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> update(@RequestBody Map<String, String> body, @PathVariable int addressId){
        if (addressId < 0) {
            HashMap<String, String> error = new HashMap<String, String>();
            error.put("Error", "Invalid id passed (id < 0) !");
            return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
        }

        Optional<Address> addressOpt = addressRepository.findById(addressId);
        Address address = null;

        try {
            address = addressOpt.get();
        } catch (Exception e) {
            HashMap<String, String> error = new HashMap<String, String>();
            error.put("Error", "No address found for id : " + addressId);
            return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
        }

        String street = body.get("street");
        String postalCode = body.get("postalCode");
        String city = body.get("city");
        String country = body.get("country");
        Integer user_id = -1;
        if (body.get("user") != null) {
            user_id = Integer.parseInt(body.get("user"));
        }

        if (street == null) {
            street = address.getStreet();
        }
        if (postalCode == null) {
            postalCode = address.getPostalCode();
        }
        if (city == null) {
            city = address.getCity();
        }
        if (country == null) {
            country = address.getCountry();
        }
        if (user_id == -1) {
            user_id = address.getUser().getId();
        }

        Optional<User> userOpt = userRepositoryAddress.findById(user_id);
        User user = null;
        try  {
            user = userOpt.get();
        } catch (Exception e) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", "This user dosn't exist !");
            return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
        }

        address.setStreet(street);
        address.setPostalCode(postalCode);
        address.setCity(city);
        address.setCountry(country);
        address.setUser(user);

        try {
            Address savingResponse = addressRepository.save(address);
            if (savingResponse == null) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("Error", "Address couldn't be created");
                return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
            }

            Map<String, String> map = new HashMap<String, String>();
            map.put("street", savingResponse.getStreet());
            map.put("postalCode", savingResponse.getPostalCode());
            map.put("city", savingResponse.getCity());
            map.put("country", savingResponse.getCountry());
            return new ResponseEntity<Object>(map, HttpStatus.CREATED);
        } catch(Exception e) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", e.toString());
            return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/address/{addressId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable int addressId){
        // if (addressId < 0) {
        //     HashMap<String, String> error = new HashMap<String, String>();
        //     error.put("Error", "Invalid id passed (id < 0) !");
        //     return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
        // }

        // Optional<Address> addressOpt = addressRepository.findById(addressId);
        // Address address = null;

        // try {
        //     address = addressOpt.get();
        // } catch (Exception e) {
        //     HashMap<String, String> error = new HashMap<String, String>();
        //     error.put("Success", "FALSE");
        //     return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
        // }

        try {
            addressRepository.deleteById(addressId);
        } catch (Exception e) {
            HashMap<String, String> error = new HashMap<String, String>();
            error.put("Success", "FALSE");
            return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
        }

        HashMap<String, String> error = new HashMap<String, String>();
        error.put("Success", "TRUE");
        return new ResponseEntity<Object>(error, HttpStatus.OK);

    }
}
