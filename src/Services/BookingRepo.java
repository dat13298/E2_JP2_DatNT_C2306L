package Services;

import Entity.Booking;
import Entity.Customer;
import Entity.Room;
import Entity.RoomType;
import Generic.IService;
import Global.Format;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static Services.RoomRepo.findRoomByType;


public class BookingRepo implements IService<Booking> {
    public static List<Booking> allBookings;
    public static List<Customer> allCustomers;
    public static List<Room> allRooms;
    public static RoomRepo roomRepo;
    public static CustomerRepo customerRepo;

    public BookingRepo() {;}


    @Override
    public Booking add() {
        Booking newBooking = input();
        allBookings.add(newBooking);
        return newBooking;
    }

    @Override
    public Booking update(Booking booking) {
        return null;
    }

    @Override
    public void delete(Booking booking) {

    }

    @Override
    public Optional<Booking> findById(String id) {
        return null;
    }

    public Booking input() {
        int id = 0;
        boolean flag = false;
        String roomId = "", customerId = "", startDate = "", endDate = "", selected;
        Room selectedRoom = null;
        Customer selectedCustomer = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        do {
            try {
                id = allBookings.size()+1;
                do {
                    System.out.println("---Select room type");
                    for (int i = 0; i < RoomType.values().length; i++) {
                        System.out.println((i + 1) + ". " + RoomType.values()[i].getType());
                    }
                    System.out.print("Enter your choice: ");
                    selected = br.readLine();
                    switch (selected) {
                        case "1":
                            List<Room> singleEmpty = getEmptyRoom(RoomType.S);
                            if(singleEmpty.isEmpty()){
                                System.out.println("There are no more rooms available during this time");
                                break;
                            } else singleEmpty.forEach(System.out::println);
                            flag = true;
                            break;
                        case "2":
                            List<Room> doubleEmpty = getEmptyRoom(RoomType.D);
                            if(doubleEmpty.isEmpty()){
                                System.out.println("There are no more rooms available during this time");
                                break;
                            } else doubleEmpty.forEach(System.out::println);
                            flag = true;
                            break;
                        case "3":
                            List<Room> queenEmpty = getEmptyRoom(RoomType.QN);
                            if(queenEmpty.isEmpty()){
                                System.out.println("There are no more rooms available during this time");
                                break;
                            } else queenEmpty.forEach(System.out::println);
                            flag = true;
                            break;
                        case "4":
                            List<Room> quadEmpty = getEmptyRoom(RoomType.QD);
                            if(quadEmpty.isEmpty()){
                                System.out.println("There are no more rooms available during this time");
                                break;
                            } else quadEmpty.forEach(System.out::println);
                            flag = true;
                            break;
                        case "5":
                            List<Room> tripleEmpty = getEmptyRoom(RoomType.T);
                            if(tripleEmpty.isEmpty()){
                                System.out.println("There are no more rooms available during this time");
                                break;
                            } else tripleEmpty.forEach(System.out::println);
                            flag = true;
                            break;
                        default:
                            System.out.println("Invalid choice");
                    }
                } while (!flag);
                flag = false;
                System.out.print("Enter Room ID: ");
                roomId = br.readLine();

                if (!checkRoomExists(roomId)) throw new Exception("Room does not exist");
                selectedRoom = roomRepo.findById(roomId).get();
                System.out.print("Enter Customer ID: ");
                customerId = br.readLine();
                if (!checkCustomerExists(customerId)) throw new Exception("Customer does not exist");
                selectedCustomer = customerRepo.findById(customerId).get();
                System.out.print("Enter Start Date(yyyy-MM-dd HH:mm): ");
                startDate = br.readLine();
                System.out.print("Enter End Date(yyyy-MM-dd HH:mm): ");
                endDate = br.readLine();
                flag = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (!flag);
        return new Booking(id
                ,selectedRoom
                ,selectedCustomer
                ,Format.formatDate(startDate)
                ,Format.formatDate(endDate));

    }

//    GET EMPTY ROOM

    private List<Room> getEmptyRoom(RoomType type) {
        List<Room> roomResults = findRoomByType(type);
        Map<String, List<Booking>> bookingByRoomId = allBookings.stream()
                .filter(s->s.getCheck_out_datetime().isAfter(LocalDateTime.now()))
                .collect(Collectors.groupingBy(b->b.getRoom().getId()));
        return roomResults.stream()
                .filter(r-> {
                    List<Booking> bookings = bookingByRoomId.get(r.getId());
                    return bookings == null || bookings.isEmpty();
                }).collect(Collectors.toList());
    }

//    FIND BOOKING BY...

    public List<Map.Entry<String, List<Booking>>> findByCustomerName(String customerName) {
        return allBookings.stream()
                .collect(Collectors.groupingBy(b->b.getCustomer().getCus_name()))
                .entrySet().stream().filter(mk-> mk.getKey().contains(customerName)).toList();
    }
    public List<Map.Entry<String, List<Booking>>> findByCustomerPhone(String phone) {
        return allBookings.stream()
                .collect(Collectors.groupingBy(b->b.getCustomer().getCus_phone()))
                .entrySet().stream().filter(mk-> mk.getKey().contains(phone)).toList();
    }
    public List<Map.Entry<String, List<Booking>>> findByRoomId(String roomId) {
        return allBookings.stream()
                .collect(Collectors.groupingBy(b->b.getRoom().getId()))
                .entrySet().stream().filter(mk-> mk.getKey().equals(roomId)).toList();
    }

//    TOTAL REVENUE BY ROOM TYPE

    public List<Map.Entry<String, DoubleSummaryStatistics>> getRoomWithTotalRevenue() {
        return allBookings.stream()
                .collect(Collectors.groupingBy(b->b.getRoom().getRoomType().getType()
                        ,Collectors.summarizingDouble(Booking::getPrice)))
                .entrySet().stream().toList();
    }

//    DISPLAY ROOM HAS MAX TOTAL REVENUE BY YEAR

    public Optional<Map.Entry<String, DoubleSummaryStatistics>> getRoomHasLargestRevenue(int year) {
        return allBookings.stream()
                .filter(booking -> booking.getCheck_in_datetime().getYear() == year)
                .collect(Collectors.groupingBy(b->b.getRoom().getRoomType().getType()
                        ,Collectors.summarizingDouble(Booking::getPrice)))
                .entrySet().stream()
                .collect(Collectors.maxBy(Comparator.comparingDouble(e->e.getValue().getSum())));
    }

//    CHECK EXIST WHEN INPUT

    public boolean checkRoomExists(String roomId) {
        return allBookings.stream().anyMatch(b -> Objects.equals(b.getRoom().getId(), roomId));
    }

    public boolean checkCustomerExists(String customerId) {
        return allBookings.stream().anyMatch(b -> Objects.equals(b.getCustomer().getId(), customerId));
    }
}
