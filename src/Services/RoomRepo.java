package Services;

import Entity.Room;
import Entity.RoomType;
import Generic.IService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RoomRepo implements IService<Room> {
    public static List<Room> rooms;
    public RoomRepo() {;}

    @Override
    public Room add() {
        return null;
    }

    @Override
    public Room update(Room room) {
        return null;
    }

    @Override
    public void delete(Room room) {

    }

    @Override
    public Optional<Room> findById(String id) {
        return rooms.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();
    }
    public static List<Room> findRoomByType(RoomType type) {
        return rooms.stream()
                .filter(r->r.getRoomType().equals(type))
                .collect(Collectors.toList());
    }

}
