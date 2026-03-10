package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CarTest {
    private Car car;

    @BeforeEach
    void setUp() {
        this.car = new Car();
    }

    @Test
    void testGetAndSetCarId() {
        String id = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        this.car.setCarId(id);
        assertEquals(id, this.car.getCarId());
    }

    @Test
    void testGetAndSetCarName() {
        String name = "Tesla Model 3";
        this.car.setCarName(name);
        assertEquals(name, this.car.getCarName());
    }

    @Test
    void testGetAndSetCarColor() {
        String color = "Midnight Silver";
        this.car.setCarColor(color);
        assertEquals(color, this.car.getCarColor());
    }

    @Test
    void testGetAndSetCarQuantity() {
        int quantity = 10;
        this.car.setCarQuantity(quantity);
        assertEquals(quantity, this.car.getCarQuantity());
    }

    @Test
    void testInitialValues() {
        assertNull(this.car.getCarId());
        assertNull(this.car.getCarName());
        assertNull(this.car.getCarColor());
        assertEquals(0, this.car.getCarQuantity());
    }
}