package com.netcraker.controllers;

import com.netcraker.model.User;
import com.netcraker.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLDataException;

@RestController
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.DELETE})
@RequiredArgsConstructor
public class AdminModeratorController {
    private final UserService userService;

    @PostMapping("admins/create")
    public ResponseEntity<?> createAdminModerator(@RequestBody @Validated User user,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("User must have only valid properties");
        }
        userService.createAdminModerator(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("admins/update")
    public ResponseEntity<?> updateAdminModerator(@RequestBody User user) {
        userService.updateAdminModerator(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("admins/{email}")
    public ResponseEntity<?> getAdminModeratorProfile(@PathVariable String email) {
        final User userFromDb = userService.findByEmail(email);
        if (userFromDb == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with such id doesn't exist");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userFromDb);
    }
    @DeleteMapping("admins/delete/{email}")
    public ResponseEntity<?> deleteAdminModerator(@PathVariable String email) throws SQLDataException {
        final User userFromDb = userService.findByEmail(email);
        if (userFromDb == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with such id doesn't exist");
        }
        userService.deleteAdminModerator(email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
