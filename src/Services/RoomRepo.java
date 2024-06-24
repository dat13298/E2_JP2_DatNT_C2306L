package Services;

import Entity.Room;
import Entity.RoomType;
import Generic.IService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public Room findById(String id) {
        return rooms.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    public static List<Room> findRoomByType(String type) {
        Map<RoomType, List<Room>> map = rooms.stream()
                .collect(Collectors.groupingBy(Room::getRoomType));
        return map.get(RoomType.valueOf(type));
    }

}
