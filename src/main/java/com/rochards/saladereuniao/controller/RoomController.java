package com.rochards.saladereuniao.controller;

import com.rochards.saladereuniao.exception.ResourceNotFoundException;
import com.rochards.saladereuniao.model.Room;
import com.rochards.saladereuniao.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/rooms")
@CrossOrigin(origins = "http://localhost:4200") // para permitir o front end consumir a API
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RoomController {

    private final RoomRepository roomRepository;

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok(roomRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable long id) {
        Optional<Room> optRoom = roomRepository.findById(id);
        return optRoom.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Room> createRoom(@Valid @RequestBody Room room) {
        return ResponseEntity.ok(roomRepository.save(room));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable long id, @Valid @RequestBody Room room)
            throws ResourceNotFoundException {
        if (roomRepository.existsById(id)) {
            room.setId(id);
            return ResponseEntity.ok(roomRepository.save(room));
        }
        throw new ResourceNotFoundException(String.format("Not found resource with id '%d'.", id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable long id) throws ResourceNotFoundException {
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        throw new ResourceNotFoundException(String.format("Not found resource with id '%d'.", id));
    }
}
