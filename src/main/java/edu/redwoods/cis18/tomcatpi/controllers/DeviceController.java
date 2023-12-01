package edu.redwoods.cis18.tomcatpi.controllers;

import edu.redwoods.cis18.tomcatpi.models.Device;
// Spring imports
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;


@CrossOrigin(origins = "*") // Allow requests from any origin
@RestController
@RequestMapping("/device")
public class DeviceController implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

    private Device d;
    @GetMapping("/index")
    public ResponseEntity<String> getIndexPage() {
        try {
            Resource resource = new ClassPathResource("static/index.html");
            if (resource.exists()) {
                byte[] htmlBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
                String htmlContent = new String(htmlBytes);
                return ResponseEntity.ok(htmlContent);
            } else {
                return new ResponseEntity<>("Index.html not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/set/color", produces = "application/json")
    public ResponseEntity<Device> setColor(@RequestParam int r, @RequestParam int g, @RequestParam int b) {
        logger.debug("DeviceController setColor called.");
        d.setColor(r,g,b);
        return ResponseEntity.ok(d);
    }

    @GetMapping(value = "/rainbow", produces = "application/json")
    public ResponseEntity<Device> rainbow() {
        try {
            d.rainbowColors();
        } catch(Throwable t) {
            d.setState(t.getMessage());
        }
        return ResponseEntity.ok(d);
    }
    @GetMapping(value = "/chaseRainbow", produces = "application/json")
    public ResponseEntity<Device> ledAnimate() {
        try {
            d.ledAnimate();
        } catch(Throwable t) {
            d.setState(t.getMessage());
        }
        return ResponseEntity.ok(d);
    }
    @GetMapping(value = "/seizureMode", produces = "application/json")
    public ResponseEntity<Device> seizureMode() {
        try {
            d.ledAnimate();
            d.rainbowColors();
            d.animations();
            d.christmasColors();
        } catch(Throwable t) {
            d.setState(t.getMessage());
        }
        return ResponseEntity.ok(d);
    }
    @GetMapping(value = "/christmas", produces = "application/json")
    public ResponseEntity<Device> christmasColors() {
        try {
            d.christmasColors();
        } catch(Throwable t) {
            d.setState(t.getMessage());
        }
        return ResponseEntity.ok(d);
    }


    @GetMapping(value = "/animations", produces = "application/json")
    public ResponseEntity<Device> animations() {
        try {
            d.animations();
        } catch(Throwable t) {
            d.setState(t.getMessage());
        }
        return ResponseEntity.ok(d);
    }

    @GetMapping(value = "/alloff", produces = "application/json")
    public ResponseEntity<Device> allOff() {
        try {
            d.allOff();
        } catch(Throwable t) {
            d.setState(t.getMessage());
        }
        return ResponseEntity.ok(d);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.debug("Creating device since the application is ready.");
        d = new Device("PiZero", 18, 255, 60);
    }
}
