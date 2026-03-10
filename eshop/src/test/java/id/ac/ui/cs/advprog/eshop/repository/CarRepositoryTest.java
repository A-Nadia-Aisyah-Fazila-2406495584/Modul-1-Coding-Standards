package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {

    private CarRepositoryImpl carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepositoryImpl();
    }

    @Test
    void testCreateWithNullId() {
        Car car = new Car();
        car.setCarName("Toyota");
        Car result = carRepository.create(car);
        assertNotNull(result.getCarId()); // Memastikan UUID di-generate
    }

    @Test
    void testCreateWithExistingId() {
        Car car = new Car();
        car.setCarId("fixed-id");
        Car result = carRepository.create(car);
        assertEquals("fixed-id", result.getCarId()); // Memastikan if(carId == null) dilewati
    }

    @Test
    void testFindAll() {
        Car car = new Car();
        carRepository.create(car);
        Iterator<Car> iterator = carRepository.findAll();
        assertTrue(iterator.hasNext());
    }

    @Test
    void testFindByIdSuccess() {
        Car car = new Car();
        car.setCarId("123");
        carRepository.create(car);

        Car found = carRepository.findById("123");
        assertNotNull(found);
        assertEquals("123", found.getCarId());
    }

    @Test
    void testFindByIdNotFound() {
        Car car = new Car();
        car.setCarId("123");
        carRepository.create(car);

        Car found = carRepository.findById("999"); // ID tidak ada di list
        assertNull(found);
    }

    @Test
    void testUpdateSuccess() {
        Car car = new Car();
        car.setCarId("123");
        car.setCarName("Lama");
        carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setCarName("Baru");
        updatedCar.setCarColor("Hijau");
        updatedCar.setCarQuantity(10);

        Car result = carRepository.update("123", updatedCar);

        assertNotNull(result);
        assertEquals("Baru", result.getCarName());
        assertEquals("Hijau", result.getCarColor());
        assertEquals(10, result.getCarQuantity());
    }

    @Test
    void testUpdateNotFound() {
        Car car = new Car();
        car.setCarId("123");
        carRepository.create(car);

        Car updatedCar = new Car();
        Car result = carRepository.update("999", updatedCar); // ID salah
        assertNull(result);
    }

    @Test
    void testDelete() {
        Car car = new Car();
        car.setCarId("123");
        carRepository.create(car);

        carRepository.delete("123");

        Car found = carRepository.findById("123");
        assertNull(found);
    }
}