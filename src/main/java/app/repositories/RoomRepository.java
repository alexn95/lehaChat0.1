package app.repositories;


import app.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findByRoomName(String roomName);

}