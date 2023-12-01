package edu.redwoods.cis18.tomcatpi.models;

import com.diozero.ws281xj.LedDriverInterface;
import com.diozero.ws281xj.PixelAnimations;
import com.diozero.ws281xj.PixelColour;
import com.diozero.ws281xj.rpiws281x.WS281x;
import com.fasterxml.jackson.annotation.JsonProperty;
// Logging imports
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Device {
    private boolean animationRunning = false;
    private static final Logger logger = LoggerFactory.getLogger(Device.class);
    @JsonProperty("deviceName")
    private String deviceName;
    @JsonProperty("gpioNum")
    private int gpioNum;
    @JsonProperty("brightness")
    private int brightness;
    @JsonProperty("pixels")
    private int pixels;

    // @TODO: Make this state part of an ENUM of possible states rather than a string.
    @JsonProperty("state")
    private String state;

    //@TODO Consider adding a property or method that defines/returns the current status of each pixel
    //    (i.e. an array of pixels and Hex color);

    private final LedDriverInterface ledDriver;

    public Device(String deviceName, int gpioNum, int brightness, int pixels) {
        this.deviceName = deviceName;
        this.gpioNum = gpioNum;
        this.brightness = brightness;
        this.pixels = pixels;
        ledDriver = getLedDriver();
    }

    public LedDriverInterface getLedDriver() {
        if(ledDriver == null) {
            try {
                return new WS281x(gpioNum, brightness, pixels);
            } catch(java.lang.RuntimeException e) {
                // Catch the error where we don't have a native library for our arch
                this.state = e.getMessage();
                logger.error(e.getMessage());
            }
        }
        return ledDriver;
    }
    public void ledAnimate(){
        theatreChaseRainbow(ledDriver, 40);
    }

    public void theatreChaseRainbow(LedDriverInterface var0, int var1) {
        animationRunning = true;
        for(int var2 = 0; var2 < 256; ++var2) {
            if (animationRunning) {
                for (int var3 = 0; var3 < 3; ++var3) {
                    if (animationRunning) {
                        int var4;
                        for (var4 = 0; var4 < var0.getNumPixels(); var4 += 3) {
                            var0.setPixelColour(var4 + var3, PixelColour.wheel((var4 + var2) % 255));
                        }

                        var0.render();
                        PixelAnimations.delay(var1);

                        for (var4 = 0; var4 < var0.getNumPixels(); var4 += 3) {
                            var0.setPixelColour(var4 + var3, 0);
                        }
                    }
                }
            }
        }
        ledDriver.allOff();
    }


    public void setColor(int r, int g, int b) {
        if(ledDriver != null) {
            if (isValidRGB(r,g,b)) {
            for (int pixel = 0; pixel < ledDriver.getNumPixels(); pixel++) {
                ledDriver.setPixelColourRGB(pixel, r, g, b);
            }
            ledDriver.render();
            } else {
                System.out.println("Error values must be within 1, 255");
            }
        }
    }
public boolean isValidRGB(int colorVal1, int colorVal2, int colorVal3){
      return colorVal1 >= 0 && colorVal1 <= 255 && colorVal2 >= 0 && colorVal2 <= 255 && colorVal3 >= 0 && colorVal3 <= 255;
}

    public void christmasColors() throws Throwable {
        animationRunning = true;
        if (ledDriver != null) {
            for (int j = 0; j < 16; j++) { // Change 16 to the desired number of loops
                if (animationRunning) {
                    // Loop from the beginning to the end
                    for (int i = 0; i < ledDriver.getNumPixels(); i++) {
                        if (animationRunning) {
                            switch (i % 3) {
                                case 0:
                                    ledDriver.setPixelColour(i, PixelColour.GREEN);
                                    break;
                                case 1:
                                    ledDriver.setPixelColourRGB(i, 100, 100, 100);
                                    break;
                                case 2:
                                    ledDriver.setPixelColour(i, PixelColour.RED);
                                    break;
                            }
                        }
                        ledDriver.render();
                        PixelAnimations.delay(3);
                    }

                    // Loop from the end to the beginning
                    for (int i = ledDriver.getNumPixels() - 1; i >= 0; i--) {
                        if (animationRunning) {
                            switch (i % 3) {
                                case 2:
                                    ledDriver.setPixelColour(i, PixelColour.GREEN);
                                    break;
                                case 0:
                                    ledDriver.setPixelColourRGB(i, 100, 100, 100);
                                    break;
                                case 1:
                                    ledDriver.setPixelColour(i, PixelColour.RED);
                                    break;
                            }
                        }
                        ledDriver.render();
                        PixelAnimations.delay(3);
                    }

                }
            }
            ledDriver.allOff();
        }
    }


    public void rainbowColors() throws Throwable {
        animationRunning = true;
        if(ledDriver != null) {
            // No point in trying to manipulate the physical device if it was never loaded.
            int[] colors = PixelColour.RAINBOW;
            for (int i = 0; i < 250; i++) {
                if (animationRunning) {
                    for (int pixel = 0; pixel < ledDriver.getNumPixels(); pixel++) {
                        if (animationRunning) {
                            ledDriver.setPixelColour(pixel, colors[(i + pixel) % colors.length]);
                        }
                    }
                    ledDriver.render();
                    PixelAnimations.delay(50);
                }
            }
        }
    }

    public void animations() throws Throwable {
        if(ledDriver != null) { // No point in trying to manipulate the physical device if it was never loaded.
            PixelAnimations.demo(ledDriver);
        }
    }
    private void stopAnimation() {
        animationRunning = false;
    }
    public void allOff() throws Throwable {
        if(ledDriver != null) {
            if (animationRunning){
                stopAnimation();
            }
            Thread.sleep(100);
            ledDriver.allOff();
        }
    }
    public void candyCane(){
        animationRunning = true;
        for (int i = 0; i < ledDriver.getNumPixels(); i++){
            switch (i % 5){
                case 0:
                    ledDriver.setPixelColourRGB(i, 255, 0, 0);
                    break;
                case 1:
                    ledDriver.setPixelColourRGB(i, 255, 0, 0);
                    break;
                case 2:
                    ledDriver.setPixelColourRGB(i, 255, 0, 0);
                    break;
                case 3:
                    ledDriver.setPixelColourRGB(i, 255, 255, 255);
                    break;
                case 4:
                    ledDriver.setPixelColourRGB(i, 255, 255, 255);
                    break;
            }
            ledDriver.render();
            PixelAnimations.delay(5);
        }
        while(animationRunning) {
            for (int i = 0; i < ledDriver.getNumPixels(); i++) {
                int initColorR = ledDriver.getRedComponent(i);
                int initColorG = ledDriver.getGreenComponent(i);
                int initColorB = ledDriver.getBlueComponent(i);
                ledDriver.setPixelColourRGB(i, 255, 255, 0);
                ledDriver.render();
                PixelAnimations.delay(100);
                ledDriver.setPixelColourRGB(i, initColorR, initColorG, initColorB);
            }
        }
        ledDriver.allOff();
    }

    @Override
    public String toString() {
        return String.format("""
                Device: {
                  Name: %s
                  GPIO: %d
                  Brightness: %d
                  Pixels: %d
                  Status: success }""", deviceName, gpioNum, brightness, pixels);
    }

    public String getState() {
        // @TODO: Change when I change state
        return state;
    }

    public void setState(String state) {
        // @TODO: Change when I change state
        this.state = state;
    }
}
