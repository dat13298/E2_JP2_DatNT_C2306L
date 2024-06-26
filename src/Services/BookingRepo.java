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
        Map<Room, List<Booking>> bookingByRoomId = allBookings.stream()
                .filter(bm->bm.getCheck_out_datetime().isAfter(LocalDateTime.now()))
                .collect(Collectors.groupingBy(Booking::getRoom));
        return roomResults.stream()
                .filter(bookingByRoomId::containsKey).collect(Collectors.toList());
    }

//    FIND BOOKING BY...

    public List<Map.Entry<Customer, List<Booking>>> findByCustomerName(String customerName) {
        String[] keywords = customerName.split(" ");
        return allBookings.stream()
                .collect(Collectors.groupingBy(Booking::getCustomer))
                .entrySet().stream()
                .filter(km-> {
                    for (String keyword : keywords) {
                        if(km.getKey().getCus_name().contains(customerName)) return true;
                    }
                    return false;

                }).toList();
    }
    public List<Map.Entry<Customer, List<Booking>>> findByCustomerPhone(String phone) {
        String[] keywords = phone.split(" ");
        return allBookings.stream()
                .collect(Collectors.groupingBy(Booking::getCustomer))
                .entrySet().stream().filter(mk-> {
                    for (String keyword : keywords) {
                        if (mk.getKey().getCus_phone().contains(phone)) return true;
                    }
                    return false;
                }).toList();
    }
    public List<Map.Entry<Room, List<Booking>>> findByRoomId(String roomId) {
        return allBookings.stream()
                .collect(Collectors.groupingBy(Booking::getRoom))
                .entrySet().stream().filter(mk-> mk.getKey().getId().equals(roomId)).toList();
    }

//    TOTAL REVENUE BY ROOM TYPE

    public List<Map.Entry<String, Double>> getRoomTypeWithTotalRevenue() {
        return allBookings.stream()
                .collect(Collectors.groupingBy(b->b.getRoom().getRoomType().getType()
                        ,Collectors.summarizingDouble(Booking::getPrice)))
                        .entrySet().stream()
                        .map(e->Map.entry(e.getKey(), e.getValue().getSum()))
                        .collect(Collectors.toList());
            }

//    DISPLAY TYPE ROOM HAS MAX TOTAL REVENUE BY YEAR

    public Optional<Map.Entry<String, Double>> getRoomTypeHasLargestRevenue(int year) {
                return allBookings.stream()
                .filter(booking -> booking.getCheck_in_datetime().getYear() == year)
                .collect(Collectors.groupingBy(b->b.getRoom().getRoomType().getType()
                        ,Collectors.summarizingDouble(Booking::getPrice)))
                        .entrySet().stream()
                        .map(e->Map.entry(e.getKey(), e.getValue().getSum()))
                        .collect(Collectors.maxBy(Comparator.comparingDouble(Map.Entry::getValue)));
    }

//    CHECK EXIST WHEN INPUT

    public boolean checkRoomExists(String roomId) {
        return allBookings.stream().anyMatch(b -> Objects.equals(b.getRoom().getId(), roomId));
    }

    public boolean checkCustomerExists(String customerId) {
        return allBookings.stream().anyMatch(b -> Objects.equals(b.getCustomer().getId(), customerId));
    }
}
