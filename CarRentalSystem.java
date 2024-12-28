package project;
import java.util.*;
	

	// Car class to represent a car in the system
	class Car {
	    private int carId;
	    private String make;
	    private String model;
	    private double pricePerDay;
	    private boolean available;

	    public Car(int carId, String make, String model, double pricePerDay, boolean available) {
	        this.carId = carId;
	        this.make = make;
	        this.model = model;
	        this.pricePerDay = pricePerDay;
	        this.available = available;
	    }

	    public int getCarId() {
	        return carId;
	    }

	    public String getDetails() {
	        return make + " " + model + " - $" + pricePerDay + "/day";
	    }

	    public boolean isAvailable() {
	        return available;
	    }

	    public void setAvailable(boolean available) {
	        this.available = available;
	    }

	    public double getPricePerDay() {
	        return pricePerDay;
	    }
	}

	// Booking class to represent a car booking
	class Booking {
	    private int bookingId;
	    private Car car;
	    private String customerName;
	    private int rentalDays;
	    private double totalPrice;

	    public Booking(int bookingId, Car car, String customerName, int rentalDays) {
	        this.bookingId = bookingId;
	        this.car = car;
	        this.customerName = customerName;
	        this.rentalDays = rentalDays;
	        this.totalPrice = rentalDays * car.getPricePerDay();
	    }
	    public Car getCar() {
	    	return car;
	    }
	    public int getBookingId1() {
	    	return bookingId;
	    }
	    public String getCustomerName() {
	    	return customerName;
	    }
	    public int getRentalDays() {
	    	return rentalDays;
	    }
	    public double getTotalPrice() {
	    	return totalPrice;
	    }

	    public String getDetails() {
	        return "Booking ID: " + bookingId + ", Car: " + car.getDetails() + ", Customer: " + customerName +
	                ", Days: " + rentalDays + ", Total Price: $" + totalPrice;
	    }

		public Object getBookingId() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	// Service class to manage cars
	class CarService {
	    private List<Car> cars = new ArrayList<>();

	    public CarService() {
	        cars.add(new Car(1, "Toyota", "Corolla", 50.0, true));
	        cars.add(new Car(2, "Honda", "Civic", 60.0, true));
	        cars.add(new Car(3, "Ford", "Focus", 55.0, true));
	    }

	    public List<Car> getAvailableCars() {
	        List<Car> availableCars = new ArrayList<>();
	        for (Car car : cars) {
	            if (car.isAvailable()) {
	                availableCars.add(car);
	            }
	        }
	        return availableCars;
	    }

	    public Car getCarById(int carId) {
	        for (Car car : cars) {
	            if (car.getCarId() == carId) {
	                return car;
	            }
	        }
	        return null;
	    }
	}

	// Service class to manage bookings
	class BookingService {
	    private List<Booking> bookings = new ArrayList<>();
	    private int nextBookingId = 1;

	    public Booking createBooking(Car car, String customerName, int rentalDays) {
	        Booking booking = new Booking(nextBookingId++, car, customerName, rentalDays);
	        bookings.add(booking);
	        car.setAvailable(false); // Mark the car as unavailable
	        return booking;
	    }

	    public List<Booking> getAllBookings() {
	        return bookings;
	    }
	}

	// Main class
	public class CarRentalSystem {
	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);
	        CarService carService = new CarService();
	        BookingService bookingService = new BookingService();

	        System.out.println("Welcome to the Car Rental System!");

	        while (true) {
	            System.out.println("\nMenu:");
	            System.out.println("1. View Available Cars");
	            System.out.println("2. Book a Car");
	            System.out.println("3. View All Bookings");
	            System.out.println("4. Exit");

	            System.out.print("Choose an option: ");
	            int choice = scanner.nextInt();
	            scanner.nextLine(); // Consume newline

	            switch (choice) {
	                case 1:
	                    List<Car> availableCars = carService.getAvailableCars();
	                    if (availableCars.isEmpty()) {
	                        System.out.println("No cars available at the moment.");
	                    } else {
	                        System.out.println("Available Cars:");
	                        for (Car car : availableCars) {
	                            System.out.println(car.getCarId() + ". " + car.getDetails());
	                        }
	                    }
	                    break;

	                case 2:
	                    System.out.println("Available Cars:");
	                    for (Car car : carService.getAvailableCars()) {
	                        System.out.println(car.getCarId() + ". " + car.getDetails());
	                    }

	                    System.out.print("Enter Car ID to book: ");
	                    int carId = scanner.nextInt();
	                    scanner.nextLine();
	                    Car car = carService.getCarById(carId);

	                    if (car == null || !car.isAvailable()) {
	                        System.out.println("Invalid car ID or car not available.");
	                    } else {
	                        System.out.print("Enter your name: ");
	                        String customerName = scanner.nextLine();

	                        System.out.print("Enter rental days: ");
	                        int rentalDays = scanner.nextInt();

	                        Booking booking = bookingService.createBooking(car, customerName, rentalDays);
	                        System.out.println("Booking successful! Details: " + booking.getDetails());
	                    }
	                    break;

	                case 3:
	                    List<Booking> bookings = bookingService.getAllBookings();
	                    if (bookings.isEmpty()) {
	                        System.out.println("No bookings found.");
	                    } else {
	                        System.out.println("All Bookings:");
	                        for (Booking booking : bookings) {
	                            System.out.println(booking.getDetails());
	                        }
	                    }
	                    break;

	                case 4:
	                    System.out.println("Thank you for using the Car Rental System!");
	                    scanner.close();
	                    return;

	                default:
	                    System.out.println("Invalid option. Please try again.");
	            }
	        }
	    }
	}

