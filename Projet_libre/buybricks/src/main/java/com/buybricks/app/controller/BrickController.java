package com.buybricks.app.controller;

import java.util.Map;

import com.buybricks.app.service.BrickService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableJpaRepositories("com.buybricks.repository")
public class BrickController {
    
    @Autowired
    private BrickService brickService;
    
    // FIND ALL
    @RequestMapping(value = "/brick", method = RequestMethod.GET)
    public ResponseEntity<?> readAll() {
        return brickService.getBrickAll();
    }
    
    // FIND BY NAME
    @RequestMapping(value = "/brick/{brick_name}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> readByName(@PathVariable String brick_name) {
        return brickService.getBricksByName(brick_name);
    }
    
    // CREATE
    @RequestMapping(value = "/brick", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody Map<String, String> body) {
        String brickName = body.get("brick_name");
        long brickDimH = Long.parseLong(body.get("brick_dim_h"));
        long brickDimL = Long.parseLong(body.get("brick_dim_l"));
        long brickDimW = Long.parseLong(body.get("brick_dim_w"));
        long brickPrice = Long.parseLong(body.get("brick_price"));
        long brickQuantity = Long.parseLong(body.get("brick_quantity"));
        String brickImageUrl = body.get("brick_image_url");

        return brickService.createBrick(brickName, brickDimH, brickDimL, brickDimW, brickPrice, brickQuantity, brickImageUrl);
    }

    // UPDATE
    @RequestMapping(value = "/brick/{brick_id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable String brick_id, @RequestBody Map<String, Long> body) {
        int brickId = Integer.parseInt(brick_id);
        long brickPrice = -1;
        if (body.get("brick_price") != null) {
            brickPrice = body.get("brick_price");
        }
        long brickQuantity = -1;
        if (body.get("brick_quantity") != null) {
            brickQuantity = body.get("brick_quantity");
        }

        return brickService.updateBrick(brickId, brickPrice, brickQuantity);
    }

    // DELETE  ALL
    @RequestMapping(value = "/brick", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> deleteAll() {
        return brickService.deleteBrickAll();
    }

    // DELETE 
    @RequestMapping(value = "/brick/{brick_id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable String brick_id) {
        int brickId = Integer.parseInt(brick_id);

        return brickService.deleteBrick(brickId);
    }
    
}
