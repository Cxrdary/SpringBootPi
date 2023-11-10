package edu.redwoods.cis18.tomcatpi.controllers;

import edu.redwoods.cis18.tomcatpi.models.DeviceColor;
import edu.redwoods.cis18.tomcatpi.models.Device;
// Spring imports
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*") // Allow requests from any origin
@RestController
@RequestMapping("/device")
public class DeviceController implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

    private Device d;

    @PostMapping(value = "/set/color", produces = "application/json")
    public ResponseEntity<Device> setColor(@RequestParam int r, @RequestParam int g, @RequestParam int b) {
        logger.debug("DeviceController setColor called.");
        d.setColor(r,g,b);
        return ResponseEntity.ok(d);
    }
// example http://yourserver/contextPath/setvars?deviceName=stringValue&gpioNum=18&brightness=255&pixels=99
    @PutMapping(value = "/setvars", produces = "application/json")
    public ResponseEntity<Device> setVars(
            @RequestParam String deviceName,
            @RequestParam int gpioNum,
            @RequestParam int brightness,
            @RequestParam int pixels ) {
        try {
            if (brightness < 0 || brightness > 255) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (pixels <= 0) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            d.setDeviceName(deviceName);
            d.setGpioNum(gpioNum);
            d.setBrightness(brightness);
            d.setPixels(pixels);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } return ResponseEntity.ok(d);
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
    @GetMapping(value = "/christmas", produces = "application/json")
    public ResponseEntity<Device> christmasColors() {
        try {
            d.christmasColors();
        } catch(Throwable t) {
            d.setState(t.getMessage());
        }
        return ResponseEntity.ok(d);
    }
    @GetMapping(value = "/christmastwinkle", produces = "application/json")
    public ResponseEntity<Device> christmasColorsTwinkle() {
        try {
            d.christmasColorsTwinkle();
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
        d = new Device("PiZero", 18, 255, 99);
    }
}
