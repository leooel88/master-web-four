package com.quest.etna.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.quest.etna.model.Address;
import com.quest.etna.model.JwtUserDetails;
import com.quest.etna.model.User;
import com.quest.etna.model.User.UserRole;
import com.quest.etna.repositories.AddressRepository;
import com.quest.etna.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableJpaRepositories("com.quest.etna.repositories")
public class AddressController {

    // Repositories
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
    
    // FIND ALL
    @RequestMapping(value = "/address", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> readAll() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId;
        UserRole userRole;
        if (principal instanceof JwtUserDetails) {
            userId = ((JwtUserDetails)principal).getId();
            userRole = ((JwtUserDetails)principal).getRole();
        } else {
            username = principal.toString();
            userId = userRepositoryAddress.findFirstByUsernameIgnoreCase(username).getId();
            userRole = userRepositoryAddress.findFirstByUsernameIgnoreCase(username).getRole();
        }
        Iterable<Address> addresses;
        if (userRole.equals(UserRole.ROLE_ADMIN)) {
            addresses = addressRepository.findAll();
        } else {
            addresses = addressRepository.findByUser(userId);
        }
        ArrayList<HashMap<String, String>> jsonResponse = Address.buildMultipleJson(addresses);
        return new ResponseEntity<Object>(jsonResponse, HttpStatus.OK);
    }

    // FIND BY ID
    @RequestMapping(value = "/address/{addressId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> readById(@PathVariable int addressId) {
        if (addressId < 0) {
            HashMap<String, String> error = new HashMap<String, String>();
            error.put("Error", "Invalid id passed (id < 0) !");
            return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
        }

        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId;
        UserRole userRole;
        if (principal instanceof JwtUserDetails) {
            userId = ((JwtUserDetails)principal).getId();
            userRole = ((JwtUserDetails)principal).getRole();
        } else {
            username = principal.toString();
            userId = userRepositoryAddress.findFirstByUsernameIgnoreCase(username).getId();
            userRole = userRepositoryAddress.findFirstByUsernameIgnoreCase(username).getRole();
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
            if (address.getUser().getId() != userId && userRole != UserRole.ROLE_ADMIN) {
                HashMap<String, String> error = new HashMap<String, String>();
                error.put("Error", "This address doesn't belong to you, you cannot access it !");
                return new ResponseEntity<Object>(error, HttpStatus.METHOD_NOT_ALLOWED);
            }
            HashMap<String, String> jsonResponse = address.buildJson();
            return new ResponseEntity<Object>(jsonResponse, HttpStatus.OK);
        } else {
            HashMap<String, String> error = new HashMap<String, String>();
            error.put("Error", "No address found for id : " + addressId);
            return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
        }
    }

    // CREATE
    @RequestMapping(value = "/address", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> create(@RequestBody Map<String, String> body){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof JwtUserDetails) {
            username = ((JwtUserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        String street = body.get("street");
        String postalCode = body.get("postalCode");
        String city = body.get("city");
        String country = body.get("country");

        User user = userRepositoryAddress.findFirstByUsernameIgnoreCase(username);
        
        if (street == null || street == "" || postalCode == null || postalCode == "" || city == null || city == "" || country == null || country == "") {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", "One parameter is missing !");
            return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
        }
        try {
            Address savingResponse = addressRepository.save(new Address(street, postalCode, city, country, user));
            if (savingResponse == null) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("Error", "Address couldn't be created");
                return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
            }

            // Map<String, String> map = new HashMap<String, String>();
            // map.put("id", Integer.toString(savingResponse.getId()));
            // map.put("street", savingResponse.getStreet());
            // map.put("postalCode", savingResponse.getPostalCode());
            // map.put("city", savingResponse.getCity());
            // map.put("country", savingResponse.getCountry());
            return new ResponseEntity<Object>(savingResponse.buildJson(), HttpStatus.CREATED);
        } catch(Exception e) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", e.toString());
            return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
        }
    }

    // UPDATE
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

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof JwtUserDetails) {
            username = ((JwtUserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        if (address.getUser().getId() != userRepositoryAddress.findFirstByUsernameIgnoreCase(username).getId() && userRepositoryAddress.findFirstByUsernameIgnoreCase(username).getRole().toString() != "ROLE_ADMIN") {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", "This address doesn't belong to you, you cannot modify it !");
            return new ResponseEntity<Object>(map, HttpStatus.FORBIDDEN);
        }
        
        Optional<User> userOpt = userRepositoryAddress.findById(address.getUser().getId());
        User user = null;
        try  {
            user = userOpt.get();
        } catch (Exception e) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", "This user doesn't exist !");
            return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
        }

        String street = body.get("street");
        String postalCode = body.get("postalCode");
        String city = body.get("city");
        String country = body.get("country");

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

        address.setStreet(street);
        address.setPostalCode(postalCode);
        address.setCity(city);
        address.setCountry(country);
        address.setUser(user);

        try {
            Address savingResponse = addressRepository.save(address);
            if (savingResponse == null) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("Error", "Address couldn't be saved");
                return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
            }

            // Map<String, String> map = new HashMap<String, String>();
            // map.put("id", Integer.toString(savingResponse.getId()));
            // map.put("street", savingResponse.getStreet());
            // map.put("postalCode", savingResponse.getPostalCode());
            // map.put("city", savingResponse.getCity());
            // map.put("country", savingResponse.getCountry());
            return new ResponseEntity<Object>(savingResponse.buildJson(), HttpStatus.CREATED);
        } catch(Exception e) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Error", e.toString());
            return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
        }
    }

    // DELETE
    @RequestMapping(value = "/address/{addressId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable int addressId){
        Optional<Address> addressOpt = addressRepository.findById(addressId);
        Address address = null;

        try {
            address = addressOpt.get();
        } catch (Exception e) {
            HashMap<String, Boolean> error = new HashMap<String, Boolean>();
            error.put("Success", false);
            return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof JwtUserDetails) {
            username = ((JwtUserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        boolean valid = address.getUser().getId() != userRepositoryAddress.findFirstByUsernameIgnoreCase(username).getId();
        boolean role = userRepositoryAddress.findFirstByUsernameIgnoreCase(username).getRole().toString() != "ROLE_ADMIN";
        if (valid && role) {
            Map<String, Boolean> map = new HashMap<String, Boolean>();
            map.put("Success", false);
            return new ResponseEntity<Object>(map, HttpStatus.FORBIDDEN);
        }

        try {
            addressRepository.deleteById(addressId);
        } catch (Exception e) {
            HashMap<String, Boolean> error = new HashMap<String, Boolean>();
            error.put("Success", false);
            return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
        }

        HashMap<String, Boolean> error = new HashMap<String, Boolean>();
        error.put("Success", true);
        return new ResponseEntity<Object>(error, HttpStatus.OK);
    }
}
