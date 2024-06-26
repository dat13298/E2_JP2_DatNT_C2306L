import Entity.Booking;
import Entity.Customer;
import Entity.Room;
import Entity.RoomType;
import Services.BookingRepo;
import Services.CustomerRepo;
import Services.RoomRepo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



public class Main {
    public static void main(String[] args) {
        boolean flag = false;
        String selected, stringToFind;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        List<Room> rooms = new ArrayList<Room>();
        rooms.add(new Room("RS001", 8, RoomType.S));
        rooms.add(new Room("RD001", 12, RoomType.D));
        rooms.add(new Room("RQ002", 35, RoomType.QN));
        rooms.add(new Room("RT001", 12.5, RoomType.T));
        rooms.add(new Room("RQ001", 20.5, RoomType.QD));
        rooms.add(new Room("RT002", 12.5, RoomType.T));

        List<Customer> customers = new ArrayList<Customer>();
        customers.add(new Customer("001", "Mr.Linus Tovaldo", "84125325346457"));
        customers.add(new Customer("002", "Mr.Bill", "91124235346467"));
        customers.add(new Customer("003", "Mr.Turing", "911423534646"));
//2024-07-17 12:00/
        List<Booking> bookings = new ArrayList<Booking>();
        bookings.add(new Booking(1, rooms.get(0), customers.get(0), LocalDateTime.of(2023, 3, 15, 9, 30, 15), LocalDateTime.of(2023, 3, 16, 12, 30, 45)));
        bookings.add(new Booking(2, rooms.get(0), customers.get(1), LocalDateTime.of(2023, 6, 9, 19, 30, 25), LocalDateTime.of(2023, 6, 10, 11, 25, 15)));
        bookings.add(new Booking(3, rooms.get(1), customers.get(1), LocalDateTime.of(2023, 3, 11, 10, 10, 5), LocalDateTime.of(2023, 3, 13, 11, 5, 10)));
        bookings.add(new Booking(4, rooms.get(3), customers.get(2), LocalDateTime.of(2025, 11, 11, 11, 11, 15), LocalDateTime.of(2025, 11, 13, 11, 15, 15)));
        bookings.add(new Booking(5, rooms.get(3), customers.get(0), LocalDateTime.of(2023, 10, 25, 9, 20, 25), LocalDateTime.of(2023, 10, 26, 12, 25, 30)));
        bookings.add(new Booking(6, rooms.get(2), customers.get(0), LocalDateTime.of(2023, 8, 18, 12, 25, 35), LocalDateTime.of(2023, 8, 19, 11, 35, 20)));

        RoomRepo roomRepo = new RoomRepo();
        RoomRepo.rooms = rooms;

        CustomerRepo customerRepo = new CustomerRepo();
        CustomerRepo.customers = customers;

        BookingRepo bookingRepo = new BookingRepo();
        BookingRepo.allBookings = bookings;
        BookingRepo.allCustomers = customers;
        BookingRepo.allRooms = rooms;
        BookingRepo.roomRepo = roomRepo;
        BookingRepo.customerRepo = customerRepo;

        do {
            try {
                System.out.println("----Menu");
                System.out.println("1. Booking");
                System.out.println("2. Find Booking");
                System.out.println("3. Total Revenue by RoomType");
                System.out.println("4. Room has the largest revenue");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                selected = br.readLine();
                switch (selected) {
                    case "1":
                        System.out.println(bookingRepo.add());
                        System.out.println("====After adding a booking");
                        bookings.forEach(System.out::println);
                        break;
                    case "2":
                        System.out.println("====Find Booking");
                        System.out.print("Enter customer name | phone number | room id: ");
                        stringToFind = br.readLine();
                        if(!bookingRepo.findByCustomerName(stringToFind).isEmpty()) {
                            System.out.println(bookingRepo.findByCustomerName(stringToFind));
                            break;
                        }
                        if (!bookingRepo.findByRoomId(stringToFind).isEmpty()) {
                            System.out.println(bookingRepo.findByRoomId(stringToFind));
                            break;
                        }
                        if (!bookingRepo.findByCustomerPhone(stringToFind).isEmpty()) {
                            System.out.println(bookingRepo.findByCustomerPhone(stringToFind));
                            break;
                        }
                        System.out.println("No reservations found");
                        break;
                    case "3":
                        System.out.println("====Total Revenue by RoomType");
                        System.out.println(bookingRepo.getRoomTypeWithTotalRevenue());
                        break;
                    case "4":
                        System.out.println("====Max total revenue by RoomType");
                        System.out.println(bookingRepo.getRoomTypeHasLargestRevenue(2023));
                        break;
                    case "5":
                        System.out.println("Goodbye!");
                        flag = true;
                    default:
                        System.out.println("Invalid choice");
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } while (!flag);
    }
}